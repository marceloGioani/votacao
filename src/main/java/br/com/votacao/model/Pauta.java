package br.com.votacao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.votacao.model.enums.TipoSituacaoPautaEnum;
import lombok.Data;

@Entity
@Data
public class Pauta {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @NotNull
   @Column(nullable = false, length = 100)
   private String titulo;
   
   @NotNull
   @Column(nullable = false, length = 300)
   private String descricao;
   
   @Temporal(TemporalType.DATE)
   private Date dataCriacao = new Date();
   
   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private TipoSituacaoPautaEnum situacao;

   public Pauta atualiza(Pauta pauta) {
		this.setSituacao(pauta.getSituacao());
		this.setDataCriacao(pauta.getDataCriacao());
		this.setDescricao(pauta.getDescricao());
		this.setTitulo(pauta.getTitulo());
		return this;
   }
  
}
