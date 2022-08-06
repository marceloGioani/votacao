package br.com.votacao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import br.com.votacao.model.enums.TipoSituacaoVotacaoEnum;
import lombok.Data;

@Entity(name = "Votacao")
@Data
@Table (name = "votacao", uniqueConstraints = @UniqueConstraint(columnNames = {"idPauta", "situacao"}, name = "UK_votacao_pauta" ))
public class Votacao {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "idPauta", unique = true, nullable = false, updatable = false)
   private Pauta pauta;
   
   @Column
   @Temporal(TemporalType.TIMESTAMP)
   private Date abertura;
   
   @Column
   @Temporal(TemporalType.TIMESTAMP)
   private Date encerramento;
   
   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private TipoSituacaoVotacaoEnum situacao;
   
   @OneToMany(mappedBy = "votacao")
   private List<Voto> votos;

   public void validaTempo()  throws Exception {
	    if (this.getEncerramento() == null) {
	    	this.getEncerramento().setTime(this.getAbertura().getTime() + 60000);
	    }
	    
    	if ((getEncerramento().getTime() - getAbertura().getTime()) < 60000)
    		throw new Exception("O tempo mínimo para sessão de votação é de 1 minuto!");
   }
   
}
