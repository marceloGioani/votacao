package br.com.votacao.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.votacao.model.Pauta;
import br.com.votacao.model.dto.PautaDTO;
import br.com.votacao.model.dto.VotacaoDto;
import br.com.votacao.repository.AssociadoRepository;
import br.com.votacao.repository.PautaRepository;

@Service
public class PautaService {
	
	@Autowired
    private PautaRepository repository;
	@Autowired
	private AssociadoRepository associadoRepository;
	@Autowired
	private VotacaoService votacaoService;
	
	public Optional<Pauta> findId(long id) {
		return repository.findById(id);
	}
	
    public PautaDTO findById(long id) throws Exception {
    	
    	Optional<Pauta> pauta = repository.findById(id);
    	
    	if (pauta.isPresent()) {
    		
    		VotacaoDto vtDao = votacaoService.statusVotacaoPauta(pauta.get().getId());
    		
    		PautaDTO pautaDto = new PautaDTO(pauta.get());
    		
    		pautaDto.setTotalVostos(vtDao.getTotalVostos());
    		pautaDto.setTotalVostosNao(vtDao.getTotalVostosNao());
    		pautaDto.setTotalVostosSim(vtDao.getTotalVostosSim());
    		pautaDto.setTotalAbstencao(associadoRepository.findTotalAssociadosAtivos());
    		
    		return pautaDto;
    	}
    	
    	return null;
    }

	public Page<Pauta> findAll(Pageable pageable) {
		
		return repository.findAll(pageable);
	}

	public List<PautaDTO> findByTitulo(String titulo) {
		return repository.findByTitulo(titulo);
	}

	public @Valid Pauta save(@Valid Pauta pauta) {
		return repository.save(pauta);
	}
    
}
