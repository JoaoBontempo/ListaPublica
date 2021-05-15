package classes;

import java.util.ArrayList;

public class Parceiro {
	
	public Parceiro()
	{
		
	}
	
	public Parceiro(int id, Object tipo, String nome, String cpf, String cnpj, String email, String usuario)
	{
		this.setId(id);
		this.setCnpj(cnpj);
		this.setCpf(cpf);
		this.setEmail(email);
		this.setNome(nome);
		this.setTipo(tipo);
		this.setUsuario(usuario);
	}
	
	
	private int id;
	private String nome;
	private String cpf;
	private String cnpj;
	private boolean tipo;//false para Pessoa Física, true para Pessoa Jurídica
	private String email;
	private String usuario;
	private ArrayList<Telefone> telefones;
	private ArrayList<Endereco> enderecos;
	
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
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public boolean getTipo() {
		return tipo;
	}
	
	public void setTipo(Object tipo)
	{
		try
		{
			if (Integer.parseInt(tipo.toString()) == 0)
				this.tipo = false;
			else
				this.tipo = true;
		}
		catch (Exception e)
		{
			this.tipo = (boolean) tipo;
		}
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public ArrayList<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(ArrayList<Telefone> telefones) {
		this.telefones = telefones;
	}
	
	public ArrayList<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(ArrayList<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
}
