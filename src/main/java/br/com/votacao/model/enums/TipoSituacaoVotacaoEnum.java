package br.com.votacao.model.enums;

public enum TipoSituacaoVotacaoEnum {

	ABERTA_NAO_INICIADA(1, "Aberta não iniciada"), 
	ABERTA(2, "Aberta"), 
	ENCERRADA(3, "Encerrada"),
	CANCELADA(4, "Cancelada"), 
	SUSPENSA(5, "Suspensa");
	
	private Integer codigo;
	private String nome;
	
	private TipoSituacaoVotacaoEnum(Integer codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
