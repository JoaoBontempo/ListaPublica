package classes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class Util {
	
	public static void MessageBoxShow(String titulo, String conteudo, AlertType tipo)
	{
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(conteudo);
		alert.showAndWait();
	}
}
