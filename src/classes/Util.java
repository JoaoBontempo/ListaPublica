package classes;

import java.util.ArrayList;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class Util {
	
	private static Parceiro contaLogada;
	
	private static ArrayList<String> cidadesApi = new ArrayList<>();
	
	private static ArrayList<String> estadosApi = new ArrayList<>();
	
	public static Parceiro getContaLogada() {
		return contaLogada;
	}
	
	public static void setCidadesArrayList(ArrayList<String> cidadesArray) {
		cidadesApi=cidadesArray;
	}
	
	public static void setEstadosArrayList(ArrayList<String> estadosArray) {
		estadosApi=estadosArray;
	}
	
	public static void setContaLogada(Parceiro contaLogada) {
		Util.contaLogada = contaLogada;
	}

	public static void MessageBoxShow(String titulo, String conteudo, AlertType tipo)
	{
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(conteudo);
		Stage stage = new Stage();
		stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("Recursos/logo.png"));
		alert.showAndWait();
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
}
