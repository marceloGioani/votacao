package br.com.votacao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.model.Pauta;
import br.com.votacao.model.dto.PautaDTO;
import br.com.votacao.model.dto.VotacaoDto;
import br.com.votacao.repository.AssociadoRepository;
import br.com.votacao.repository.PautaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PautaService {
	
	@Autowired
    private final PautaRepository repository;
	@Autowired
	private final AssociadoRepository associadoRepository;
	@Autowired
	private VotacaoService votacaoService;
	
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
    
}
