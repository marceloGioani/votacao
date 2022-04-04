package br.com.votacao.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.votacao.model.Associado;

public interface AssociadoRepository extends PagingAndSortingRepository<Associado, Object> {

    @Query("SELECT count(associados) "
    		+ "FROM Associado associados "
            + "WHERE associados.ativo is true")
    public long findTotalAssociadosAtivos();

	public Page<Associado> findByNome(String nome, Pageable pageable);
	
	public Optional<Associado> findByCpf(String cpf);
	
}
