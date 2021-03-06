package classes;

public class TableViewUtil {
	
	public TableViewUtil(String nome, String numero, String cidade, String estado, String email, String descricao, String tipo)
	{ 
		this.email = email;
		this.nome = nome;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.descricao = descricao;
		this.tipo = tipo;
	}
	
	public TableViewUtil (String numero, String tipo)
	{
		this.tipo = tipo;
		this.numero = numero;
	}
	
	public TableViewUtil(TableViewUtil tbl) {
		this.email = tbl.email;
		this.nome = tbl.nome;
		this.numero = tbl.numero;
		this.cidade = tbl.cidade;
		this.estado = tbl.estado;
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
		this.tipo =  telefone.getTipo();
	}
	
	private String nome;
	private String numero;
	private String cidade;
	private String estado;
	private String descricao;
	private String email;
	private String tipo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNumero() {
		return Util.FormatarGetTelefone(numero, tipo);
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
