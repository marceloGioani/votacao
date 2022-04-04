package br.com.votacao.model.enums;

public enum TipoSituacaoPautaEnum {

	NOVA(1, "Nova"), 
	EM_VOTACAO(2, "Em votação"), 
	EM_APURACAO(3, "Em apuração"),
	ACEITA(4, "Aceita"), 
	REJEITADA(5, "Rejeitada");
	
	private Integer codigo;
	private String nome;
	
	private TipoSituacaoPautaEnum(Integer codigo, String nome) {
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
