package API_IBGE;

public class Regiao_intermediaria {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Uf getUF() {
		return UF;
	}
	public void setUF(Uf UF) {
		this.UF = UF;
	}
	private String nome;
	private Uf UF;
}
