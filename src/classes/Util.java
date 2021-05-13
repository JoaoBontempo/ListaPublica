package classes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class Util {
	
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
}
