package br.com.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.votacao.model.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long>{

}
