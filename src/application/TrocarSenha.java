package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Banco;
import classes.Util;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TrocarSenha extends Application {
	private String email;

	@FXML
    private TextField txtNovaSenha;

    @FXML
    private TextField txtRedigiteSenha;
    
    public void setEmail(String email) {
    	this.email=email;
    }

    @FXML
    void CadastrarNovaSenha(ActionEvent event) {
    	if(txtNovaSenha.getLength() < 4) {
    		Util.MessageBoxShow("Senha muito curta","A senha possui menos de quatro dígitos",AlertType.ERROR);
    		return;
    	}
    	if(txtNovaSenha.getText().equals(txtRedigiteSenha.getText())) {
			try {
				Banco.InserirQuery(String.format("UPDATE parceiro SET senha=%s WHERE email=%s",Util.criptografarSenha(txtNovaSenha.getText()),
						email));
				Util.MessageBoxShow("Senha alterada", "Senha alterada com sucesso.", AlertType.INFORMATION);
				Stage stageAtual = (Stage) txtNovaSenha.getScene().getWindow();
				stageAtual.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    	}else {
    		Util.MessageBoxShow("Senhas divergentes", "As senhas digitadas estão divergentes", AlertType.ERROR);
    	}
    }
    
    @Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("telaTrocarSenha.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Trocar senha");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
    public static void main(String[] args) {
		launch(args);
	}
}
