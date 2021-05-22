package classes;

public class Denuncia {
	
	public Denuncia(int id, String descricao, String tipo, String local, boolean status, Parceiro denunciado, Parceiro denunciador)
    {
        this.id = id;
        this.descricao = descricao;
        this.tipo = tipo;
        this.local = local;
        this.status = status;
        this.denunciado = denunciado;
        this.denunciador = denunciador;
    }

    public Denuncia()
    {

    }
	
	private int id;
	private String descricao;
	private String tipo;
	private String local;
	private boolean status;
	private Parceiro denunciado;
	private Parceiro denunciador;
	private Telefone telefone;
	
	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	private Endereco endereco;
	
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Parceiro getDenunciado() {
		return denunciado;
	}
	public void setDenunciado(Parceiro denunciado) {
		this.denunciado = denunciado;
	}
	public Parceiro getDenunciador() {
		return denunciador;
	}
	public void setDenunciador(Parceiro denunciador) {
		this.denunciador = denunciador;
	}
}
