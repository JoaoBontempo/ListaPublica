package classes;

public class Telefone {
	private String numero;
	private Endereco endereco;
	private Parceiro dono;
	
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
}
