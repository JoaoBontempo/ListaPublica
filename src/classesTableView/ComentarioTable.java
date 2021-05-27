package classesTableView;

import java.time.LocalDate;

public class ComentarioTable {
	private String usuario;
	private String comentario;
	private LocalDate data;
	
	public ComentarioTable() {
		super();
	}
	public ComentarioTable(String usuario, String comentario, LocalDate data) {
		super();
		this.usuario = usuario;
		this.comentario = comentario;
		this.data = data;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}
