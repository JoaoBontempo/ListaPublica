package classes;

import java.time.LocalDateTime;

public class Comentario {
	private int idComentario;
	private int idLugar;
	private int idParceiro;
	private String data;
	private String comentario;
	
	
	public Comentario(int idComentario, int idLugar, int idParceiro, String data, String comentario) {
		super();
		this.idComentario = idComentario;
		this.idLugar = idLugar;
		this.idParceiro = idParceiro;
		this.data = data;
		this.comentario = comentario;
	}
	
	public int getIdComentario() {
		return idComentario;
	}
	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}
	public int getIdLugar() {
		return idLugar;
	}
	public void setIdLugar(int idLugar) {
		this.idLugar = idLugar;
	}
	public int getIdParceiro() {
		return idParceiro;
	}
	public void setIdParceiro(int idParceiro) {
		this.idParceiro = idParceiro;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	

}
