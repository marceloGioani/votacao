package br.com.votacao.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.votacao.model.Pauta;
import br.com.votacao.model.dto.PautaDTO;

public interface PautaRepository extends PagingAndSortingRepository<Pauta, Object> {

	List<PautaDTO> findByTitulo(String titulo);
	

}
