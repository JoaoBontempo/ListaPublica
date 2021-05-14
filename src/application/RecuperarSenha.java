package application;

import java.awt.TextField;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RecuperarSenha extends Application {

	@FXML
	private TextField txtEmailPrincipal;

	@FXML
	private Button btnEnviarCodigo;

	@FXML
	private Pane pnlCodigos;

	@FXML
	private TextField txtEmailPrincipal1;

	@FXML
	private TextField txtEmailPrincipal11;

	@FXML
	private TextField txtEmailPrincipal12;

	@FXML
	private TextField txtEmailPrincipal13;

	@FXML
	private TextField txtEmailPrincipal131;

	@FXML
	void VerificarEntradaEmail(ActionEvent event) {
		if(txtEmailPrincipal.getText().contains("@")) {
			JOptionPane.showMessageDialog(null, "Verifique seu e-mail e tente novamente");
			return;
		}
		// se após o @ não tiver ao menos dois caracteres, dê o aviso novamente.
		if(txtEmailPrincipal.getText().substring(txtEmailPrincipal.getText().indexOf('@'), txtEmailPrincipal.getText().indexOf('@')+2)
				== null) {
			JOptionPane.showMessageDialog(null, "Verifique seu e-mail e tente novamente");
			return;
		}
			
		
		
	}

	@FXML
	void txtNumeroCinco(ActionEvent event) {

	}

	@FXML
	void txtNumeroDois(ActionEvent event) {

	}

	@FXML
	void txtNumeroQuatro(ActionEvent event) {

	}

	@FXML
	void txtNumeroTres(ActionEvent event) {

	}

	@FXML
	void txtNumeroUm(ActionEvent event) {

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("esqueceuSenha.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");

			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Esqueci a senha");

			primaryStage.getIcons().add(icon);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
