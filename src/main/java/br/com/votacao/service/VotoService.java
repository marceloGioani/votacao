package br.com.votacao.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.votacao.model.Votacao;
import br.com.votacao.model.Voto;
import br.com.votacao.model.enums.TipoSituacaoVotacaoEnum;
import br.com.votacao.repository.VotacaoRepository;
import br.com.votacao.repository.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
    private VotacaoRepository votacaoRepository;
	
	@Autowired
    private VotacaoService votacaoService;
	
	@Autowired
	private VotoRepository repository;
	
    public void registrarVoto(Voto voto) throws Exception {
    	
    	Optional<Votacao> votacao = votacaoRepository.findId(voto.getVotacao().getId());
    	
    	Date agora = new Date();
    	
    	// verifica se é possível votar
    	StatusVotacao(votacao, agora);
    	
    	// Se abertura agendada, Iniciar com o primeiro voto
    	if (votacao.get().getAbertura().getTime() <= agora.getTime()) {
    		if (votacao.get().getSituacao().equals(TipoSituacaoVotacaoEnum.ABERTA_NAO_INICIADA)) {
    			votacao.get().setSituacao(TipoSituacaoVotacaoEnum.ABERTA);
    			votacaoService.abreVotacao(votacao.get());
    		}
    		
	    }
    		
    	voto.setDataHora(LocalDateTime.now());
    	
    	try {
    		repository.save(voto);
    	} catch (DataIntegrityViolationException e) {
    		throw new Exception("Associado já votou. Voto Não contabilizado");

		}
    	
    }

	private void StatusVotacao(Optional<Votacao> votacao, Date agora) throws Exception {
		
		if (!(votacao.get().getSituacao().equals(TipoSituacaoVotacaoEnum.ABERTA)
    		|| votacao.get().getSituacao().equals(TipoSituacaoVotacaoEnum.ABERTA_NAO_INICIADA))) {
    		throw new Exception("Votação já foi encerrada. Voto Não contabilizado");
    	}
    	
    	if (votacao.get().getAbertura().getTime() > agora.getTime()) {
    		throw new Exception("Votação ainda não iniciada. Voto Não contabilizado");
    	}
    	
    	if (votacao.get().getEncerramento().getTime() < agora.getTime()) {
    		
    		if (votacao.get().getSituacao().equals(TipoSituacaoVotacaoEnum.ABERTA)
    	    		|| votacao.get().getSituacao().equals(TipoSituacaoVotacaoEnum.ABERTA_NAO_INICIADA) ){
    			votacaoService.encerraVotacao(TipoSituacaoVotacaoEnum.ENCERRADA);
    		}	
    	
    		throw new Exception("Votação encerrada. Voto Não contabilizado");
    	}
	}
    
}
