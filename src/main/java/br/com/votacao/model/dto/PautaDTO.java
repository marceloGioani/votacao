package br.com.votacao.model.dto;

import java.util.Date;

import org.springframework.data.domain.Page;

import br.com.votacao.model.Pauta;
import br.com.votacao.model.enums.TipoSituacaoPautaEnum;
import lombok.Data;

@Data
public class PautaDTO {
   private long id;
   private String titulo;
   private String descricao;
   private Date dataCriacao = new Date();
   private TipoSituacaoPautaEnum situacao;
   private long totalVostos;
   private long totalVostosSim;
   private long totalVostosNao;
   private long totalAbstencao;
  
   public PautaDTO() {
	   
   }
   
   public PautaDTO(Pauta pauta) {
	   
	   this.setId(pauta.getId());
	   this.setTitulo(pauta.getTitulo());
	   this.setDescricao(pauta.getDescricao());
	   this.setDataCriacao(pauta.getDataCriacao());
	   this.setSituacao(pauta.getSituacao());

   }

	public static Page<PautaDTO> convert(Page<Pauta> pautas) {
		return pautas.map(PautaDTO::new);
	}

}
