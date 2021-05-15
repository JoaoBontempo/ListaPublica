package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Banco;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Cadastrar extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@FXML
	private TextField txtUsuario;

	@FXML
	private Button btnCadastrar;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtConfirmarSenha;

	@FXML
	private PasswordField txtCPFouCNPJ;

	@FXML
	private TextField txtNome;

	@FXML
	void RealizarCadastro(ActionEvent event) {
		if(ValidarCampos()) {
			realizarCadastro(tipo);
		}

	}
	public boolean tipo = false;
	boolean ValidarCampos() {

		if (!Validacao.verificarTextField(txtNome))
			return false;
		if (!Validacao.verificarTextField(txtUsuario))
			return false;
		if (!Validacao.verificarTextField(txtSenha))
			return false;
		if (!Validacao.verificarTextField(txtConfirmarSenha))
			return false;
		if (!Validacao.verificarTextField(txtCPFouCNPJ))
			return false;
		if (!Validacao.verificarTextField(txtEmail))
			return false;
		if(!Validacao.validarEmail(txtEmail.getText()))
			return false;
		if(txtUsuario.getText().contains("@") || txtUsuario.getText().matches("[0-9]+"))
			return false;

		if(txtCPFouCNPJ.getText().length() == 14) {
			tipo = true; //tipo cnpj
			return Validacao.validarCNPJ(txtCPFouCNPJ);

		}
		else {
			tipo = false; //tipo cpf
			return Validacao.validarCPF(txtCPFouCNPJ);
		}

	}

	void realizarCadastro(boolean tipo) {

		try {
			
			if(tipo) {
				Banco.InserirQuery(String.format("INSERT INTO parceiro(nome,usuario,tipo,cnpj,email,senha) VALUES(%s,%s,%s,%s,%s,%s)",
						txtNome.getText(), txtUsuario.getText(),tipo,  txtCPFouCNPJ.getText(), txtEmail.getText(), Util.criptografarSenha(txtSenha.getText())));
			}
			else {

				Banco.InserirQuery(String.format("INSERT INTO parceiro(nome,usuario,tipo,cpf,email,senha) VALUES(%s,%s,%s,%s,%s,%s)",
						txtNome.getText(), txtUsuario.getText(),tipo ,txtCPFouCNPJ.getText(), txtEmail.getText(), Util.criptografarSenha(txtSenha.getText())));
			}
			
			Util.MessageBoxShow("Cadastar", "Cadastro realizado com sucesso", AlertType.INFORMATION);
		}
		catch (Exception ex){
			
			Util.MessageBoxShow("Cadastar", "Erro ao Cadastar: " + ex.getMessage(), AlertType.ERROR);
		}

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("telaCadastro.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Cadastrar");
			primaryStage.getIcons().add(icon);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
