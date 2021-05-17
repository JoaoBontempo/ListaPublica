package classes;

import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class Util {
	
	private static Parceiro contaLogada;
	
	private static boolean convidado = false;
	
	public static Parceiro getContaLogada() {
		return contaLogada;
	}
	
	public static void setContaLogada(Parceiro contaLogada) {
		Util.contaLogada = contaLogada;
	}

	public static Alert MessageBoxShow(String titulo, String conteudo, AlertType tipo)
	{
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(conteudo);
		Stage stage = new Stage();
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("Recursos/logo.png"));
		alert.showAndWait();
		return alert;
	}
	
	public static ButtonType MessageBoxShow(String titulo, String conteudo)
	{
		Alert alert = MessageBoxShow(titulo, conteudo, AlertType.CONFIRMATION);
		return alert.getResult();
		//return alert;
	}
	
	public static String criptografarSenha(String senha)
	{
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	public static boolean verificarSenha(String senha, String hash)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senha, hash);
	}

	public static boolean isConvidado() {
		return convidado;
	}

	public static void setConvidado(boolean value) {
		convidado = value;
	}
}
