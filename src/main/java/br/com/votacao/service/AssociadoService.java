package br.com.votacao.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.votacao.model.Associado;
import br.com.votacao.repository.AssociadoRepository;
import br.com.votacao.util.Util;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssociadoService {
    
	private enum situacaoSpf {ABLE_TO_VOTE, UNABLE_TO_VOTE;}
	
	@Autowired
	private final AssociadoRepository repository;

	@PersistenceContext()
	protected EntityManager entityManager;
	
    public Associado save(Associado associado) throws Exception {

    	try {
        
    		String cpfLimpo = Util.removeCaracteresEspeciais(associado.getCpf());
        	permiteCadastro(cpfLimpo, associado);
        	associado.setCpf(cpfLimpo);
	        
	        return repository.save(associado);
	        
        } catch (Exception e) {
			throw new Exception(e.getMessage());
		}
        
    }
    
    private void permiteCadastro(String cpfLimpo, Associado associado) throws Exception {
    	
    	String situacao = "";
        
        try {
        	situacao = ClienteService.getMicroServico("https://user-info.herokuapp.com/users/" + associado.getCpf(), String.class);
        } catch (HttpClientErrorException e) {
			situacao = situacaoSpf.UNABLE_TO_VOTE.name();
		}
        
		if (!situacao.contains(situacaoSpf.UNABLE_TO_VOTE.name())) {
	        Optional<Associado> associadoAtual = repository.findByCpf(cpfLimpo);
	        
	        if(associadoAtual.isPresent())
	            throw new Exception("Este CPF já está cadastrado!");
	        
		} else {
			throw new Exception("Cpf Invalido!");
		}
    }
}
