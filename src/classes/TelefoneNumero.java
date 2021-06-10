package classes;

public class TelefoneNumero {
	private String numero;
	private String tipo;

	public String getNumero() {
		return numero;
	}

	public TelefoneNumero() {
		super();
	}

	public TelefoneNumero(String numero) {
		super();
		this.numero = numero;
	}

	public TelefoneNumero(String numero, String tipo) {
		this.numero = numero;
		this.tipo = tipo;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
