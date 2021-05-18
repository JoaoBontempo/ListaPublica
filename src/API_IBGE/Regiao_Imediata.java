package API_IBGE;

public class Regiao_Imediata {
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
	public Regiao_intermediaria getRegiao_intermediaria() {
		return regiao_intermediaria;
	}
	public void setRegiao_intermediaria(Regiao_intermediaria regiao_intermediaria) {
		this.regiao_intermediaria = regiao_intermediaria;
	}
	private String nome;
	private Regiao_intermediaria regiao_intermediaria;
}
