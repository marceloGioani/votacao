package br.com.votacao.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table (name = "voto", uniqueConstraints = @UniqueConstraint(columnNames = {"idAssociado", "idVotacao"}, name = "UK_associado_votacao" ))
public class Voto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "idAssociado", unique = true, nullable = false, updatable = false)
    private Associado associado;

    @Column(nullable = false)
    private boolean voto = false;

    @Column
    private LocalDateTime dataHora = LocalDateTime.now();
    
    @OneToOne
    @JoinColumn(name = "idVotacao", unique = true, nullable = false, updatable = false)
    private Votacao votacao;

}
