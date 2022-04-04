package br.com.votacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.votacao.model.Votacao;

public interface VotacaoRepository extends PagingAndSortingRepository<Votacao, Object>{

    @Query("SELECT votacao "
    		+ "FROM Votacao votacao "
    		+ "JOIN votacao.pauta pauta "
            + "WHERE pauta.id = :idPauta")
    public Optional<Votacao> findByPauta(@Param("idPauta") long idPauta);
    
    @Query("SELECT votacao "
    		+ "FROM Votacao votacao "
    		+ "JOIN votacao.pauta pauta "
            + "WHERE situacao in ('ABERTA_NAO_INICIADA', 'ABERTA')")
    public Optional<Votacao> findVotacaoAberta();
    
    @Query("SELECT votacao "
    		+ "FROM Votacao votacao "
            + "WHERE votacao.id = :id")
    public Optional<Votacao> findId(@Param("id") long id);
    
}
