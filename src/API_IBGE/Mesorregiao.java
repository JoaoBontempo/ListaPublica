package API_IBGE;

public class Mesorregiao {
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
	public UF getUF() {
		return UF;
	}
	public void setUF(UF UF) {
		this.UF = UF;
	}
	private String nome;
	private UF UF;
}
