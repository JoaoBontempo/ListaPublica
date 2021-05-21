package classes;

public class TableViewUtil {
	
	public TableViewUtil(String nome, String numero, String cidade, String estado, String email)
	{ 
		this.email = email;
		this.nome = nome;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
	}
	
	public TableViewUtil(Telefone telefone, Parceiro parceiro, Endereco endereco)
	{
		if (endereco != null)
		{
			this.cidade = endereco.getCidade();
			this.estado = endereco.getEstado();
		}
		else
		{
			this.cidade = "N/D";
			this.estado = "N/D";
		}
		this.descricao = telefone.getDescricao();
		this.email = parceiro.getEmail();
		this.nome = parceiro.getNome();
		this.numero = telefone.getNumero();
	}
	
	private String nome;
	private String numero;
	private String cidade;
	private String estado;
	private String descricao;
	private String email;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
