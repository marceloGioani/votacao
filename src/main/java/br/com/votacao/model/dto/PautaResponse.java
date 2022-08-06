package br.com.votacao.model.dto;

import java.util.Date;

import br.com.votacao.model.enums.TipoSituacaoPautaEnum;

public interface PautaResponse {
    long getId();
    String getTitulo();
	String getDescricao();
	Date getDataCriacao();
	TipoSituacaoPautaEnum getSituacao();
	long getTotalVostos();
	long getTotalVostosSim();
	long getTotalVostosNao();
	long getTotalAbstencao();
	
}
