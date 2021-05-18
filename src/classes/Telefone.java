package classes;

public class Telefone {
	private int id;
	private String numero;
	private Endereco endereco;
	private Parceiro dono;
	private String descricao;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Parceiro getDono() {
		return dono;
	}
	public void setDono(Parceiro dono) {
		this.dono = dono;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
