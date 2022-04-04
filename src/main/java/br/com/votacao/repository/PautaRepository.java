package br.com.votacao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.votacao.model.Pauta;

public interface PautaRepository extends PagingAndSortingRepository<Pauta, Object> {

	Page<Pauta> findByTitulo(String titulo, Pageable pageable);
	

}
