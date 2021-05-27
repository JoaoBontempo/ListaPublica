package classesTableView;

import java.time.LocalDate;

public class ComentarioTable {
	private String usuario;
	private String comentario;
	private String dataComentario;
	
	public ComentarioTable() {
		super();
	}
	@Override
	public String toString() {
		return "ComentarioTable [usuario=" + usuario + ", comentario=" + comentario + ", data=" + dataComentario + "]";
	}
	public ComentarioTable(String usuario, String comentario, String data) {
		super();
		this.usuario = usuario;
		this.comentario = comentario;
		this.dataComentario = data;
	}
	public String getDataComentario() {
		return dataComentario;
	}
	public void setDataComentario(String dataComentario) {
		this.dataComentario = dataComentario;
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
