package API_IBGE;

public class Microrregiao {
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
	public Mesorregiao getMesorregiao() {
		return mesorregiao;
	}
	public void setMesorregiao(Mesorregiao mesorregiao) {
		this.mesorregiao = mesorregiao;
	}
	private String nome;
	private Mesorregiao mesorregiao;
}
