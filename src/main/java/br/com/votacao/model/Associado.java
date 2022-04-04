package br.com.votacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Associado {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @NotNull
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull
    @Column(nullable = false)
    private boolean ativo;
    
    public Associado atualiza(Associado associado) {
 		this.setCpf(associado.getCpf());
 		this.setNome(associado.getNome());
 		this.setAtivo(associado.isAtivo());
 		return this;
    }
    
}
