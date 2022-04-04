package br.com.votacao.model.dto;

import java.util.Date;

import org.springframework.data.domain.Page;

import br.com.votacao.model.Pauta;
import br.com.votacao.model.Votacao;
import br.com.votacao.model.enums.TipoSituacaoVotacaoEnum;
import lombok.Data;

@Data
public class VotacaoDto {

	   private long id;
	   private Pauta pauta;
	   private Date abertura;
	   private Date encerramento;
	   private TipoSituacaoVotacaoEnum situacao;
	   private long totalVostos;
	   private long totalVostosSim;
	   private long totalVostosNao;
	   
	   public VotacaoDto(Votacao votacao) {
		   this.setId(votacao.getId());
		   this.setPauta(votacao.getPauta());
		   this.setAbertura(votacao.getAbertura());
		   this.setEncerramento(votacao.getEncerramento());
		   this.setSituacao(votacao.getSituacao());
	   }


	public static Page<VotacaoDto> convert(Page<Votacao> votacoes) {
		return votacoes.map(VotacaoDto::new);
		
	}

	public VotacaoDto() {
		super();
	}
}
