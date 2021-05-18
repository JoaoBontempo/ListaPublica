package API_IBGE;

public class Municipio {
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
	private String nome;
	
	private Microrregiao microrregiao;
	public Microrregiao getMicrorregiao() {
		return microrregiao;
	}
	public void setMicrorregiao(Microrregiao microrregiao) {
		this.microrregiao = microrregiao;
	}
	private Regiao_Imediata regiao_imediata;
	public Regiao_Imediata getRegiao_imediata() {
		return regiao_imediata;
	}
	public void setRegiao_imediata(Regiao_Imediata regiao_imediata) {
		this.regiao_imediata = regiao_imediata;
	}
}
