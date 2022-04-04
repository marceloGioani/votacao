package br.com.votacao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.error.BadRequestException;
import br.com.votacao.model.Pauta;
import br.com.votacao.model.Votacao;
import br.com.votacao.model.dto.VotacaoDto;
import br.com.votacao.model.enums.TipoSituacaoPautaEnum;
import br.com.votacao.model.enums.TipoSituacaoVotacaoEnum;
import br.com.votacao.repository.PautaRepository;
import br.com.votacao.repository.VotacaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VotacaoService {
    
	@Autowired
	private final VotacaoRepository repository;

	@Autowired
	private final PautaRepository pautaRepository;
	
	@PersistenceContext()
	protected EntityManager entityManager;
	
    public Votacao findByIdOrException(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Votacao não encontrada"));
    }
    
    public Votacao abreVotacao(Votacao votacao) throws Exception {
    	
    	Optional<Votacao> votoAberto = repository.findVotacaoAberta();
    	
    	if (votoAberto.isPresent()) {
    		throw new Exception("Esta votação está " + votoAberto.get().getSituacao().getNome() );
    	}
    	
		votacao.setSituacao(TipoSituacaoVotacaoEnum.ABERTA_NAO_INICIADA);
		
		try {
			votacao.validaTempo();
			atualizaPauta(votacao.getPauta(), TipoSituacaoPautaEnum.EM_VOTACAO);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());		
		}
		
		return this.repository.save(votacao);
    }
    
    public Votacao encerraVotacao(TipoSituacaoVotacaoEnum tipoEncerramento) throws Exception {
    	
    	Optional<Votacao> votoAberto = repository.findVotacaoAberta();
    	
    	if (votoAberto.isEmpty()) {
    		throw new Exception("Não existe votação aberta ");
    	}
    	
    	votoAberto.get().setSituacao(tipoEncerramento);
    	
    	if ((tipoEncerramento.equals(TipoSituacaoVotacaoEnum.ABERTA) 
    			|| tipoEncerramento.equals(TipoSituacaoVotacaoEnum.ABERTA_NAO_INICIADA)) ) {
    		
    		atualizaPauta(votoAberto.get().getPauta(), null);
    		
    		votoAberto.get().setSituacao(TipoSituacaoVotacaoEnum.ENCERRADA);
    	}
		
		return this.repository.save(votoAberto.get());
    }
    
    private void atualizaPauta(Pauta pauta, TipoSituacaoPautaEnum tipo) throws Exception {
    	
    	if (tipo == null) {
			VotacaoDto votacao = statusVotacaoPauta(pauta.getId());
			
			if (votacao.getTotalVostosSim() > votacao.getTotalVostosNao()) {
				pauta.setSituacao(TipoSituacaoPautaEnum.ACEITA);
			} else {
				pauta.setSituacao(TipoSituacaoPautaEnum.REJEITADA);
			}
			
    	} else {
    		pauta.setSituacao(tipo);
    	}
    	
		pautaRepository.save(pauta);
    }
    
    public VotacaoDto statusVotacaoPauta(long id) throws Exception  {
    	String where = "where pt.id = " + id;
 	    return consultaStatusVotacao(where);
    }

    public VotacaoDto statusVotacao(long id) throws Exception  {
    	String where = "where vtc.id = " + id;
    	return consultaStatusVotacao(where);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private VotacaoDto consultaStatusVotacao(String where)  throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select vtc.id, ")
    	.append("pt.id as idPauta, ")
    	.append("vtc.abertura as abertura, ")
    	.append("vtc.encerramento as encerramento, ")
    	.append("vtc.situacao as situacao, ")
    	.append("count(vt.id) as totalVostos, ")
    	.append("sum(case when vt.voto is true then 1 else 0 end) as totalVostosSim, ") 
    	.append("sum(case when vt.voto is true then 0 else 1 end) as totalVostosNao ")
    	.append("from votacao vtc ")
    	.append("join pauta pt on pt.id = vtc.id_pauta ")
    	.append("left join voto vt on vt.id_votacao = vtc.id ")
    	.append(where)
    	.append(" group by vtc.id, pt.id, vtc.abertura, vtc.encerramento, vtc.situacao");
    	
    	Query query = entityManager.createNativeQuery(sql.toString());
    	List resultList = query.getResultList();
    	
    	List<VotacaoDto> listaLancamento = new ArrayList<VotacaoDto>();
    	
    	try {
	    	resultList.forEach(params -> {
    			VotacaoDto dto  = convertDto((Object[]) params);
				listaLancamento.add(dto);
    			
	    	});
    	} catch (Exception e) {
    		throw  new Exception("Não foi possível carregar os dados da votação!");
    	}
    	
    	return listaLancamento.get(0);
    }
    
    private VotacaoDto convertDto(Object[] params) {
		VotacaoDto dto = new VotacaoDto();
		dto.setId(Long.parseLong(params[0].toString()));
		dto.setPauta(pautaRepository.findById(Long.parseLong(params[1].toString())).get());
		dto.setAbertura((Date)params[2]);
		dto.setEncerramento((Date)params[3]);
		dto.setSituacao(TipoSituacaoVotacaoEnum.valueOf(params[4].toString()));
		dto.setTotalVostos(Long.parseLong(params[5].toString()));
		dto.setTotalVostosSim(Long.parseLong(params[6].toString()));
		dto.setTotalVostosNao(Long.parseLong(params[7].toString()));
		
		return dto;
	}

	@SuppressWarnings("unchecked")
	public VotacaoDto findId(long id) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select vtc.id, ")
    	.append("vtc.abertura as abertura, ")
    	.append("vtc.encerramento as encerramento, ")
    	.append("vtc.situacao as situacao, ")
    	.append("from votacao vtc")
    	.append("where vtc.id = " + id);
    	
    	Query query = entityManager.createNativeQuery(sql.toString(), VotacaoDto.class);
    	List<VotacaoDto> resultList = query.getResultList();
    	if (resultList.isEmpty())
    		return null;
    	
    	return resultList.get(0);
    }
}
