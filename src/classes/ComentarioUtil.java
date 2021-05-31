package classes;

public final class ComentarioUtil {
	
	private static String data;
	public static String getData() {
		return data;
	}
	public static void setData(String data) {
		ComentarioUtil.data = data;
	}
	public static String getUsuario() {
		return usuario;
	}
	public static void setUsuario(String usuario) {
		ComentarioUtil.usuario = usuario;
	}
	public static String getComentario() {
		return comentario;
	}
	public static void setComentario(String comentario) {
		ComentarioUtil.comentario = comentario;
	}
	private static String usuario;
	private static String comentario;
}
