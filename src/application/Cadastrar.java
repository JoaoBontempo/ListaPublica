package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.javafx.geom.Point2D;

import classes.Banco;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Tooltip;
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
	private TextField txtCPFouCNPJ;

	@FXML
	private TextField txtNome;
	
	@FXML
	private Label lbLogar;

	@FXML
	void realizarCadastro(ActionEvent event) {

		if(ValidarCampos()) 
			realizarCadastro(tipo);
	}

	public boolean tipo = false;
	boolean ValidarCampos() {

		if (!Validacao.verificarTextField(txtNome))
			return false;
		if (!Validacao.verificarTextField(txtUsuario))
			return false;
		if (!Validacao.verificarTextField(txtEmail))
			return false;
		if (!Validacao.verificarTextField(txtCPFouCNPJ))
			return false;
		if (!Validacao.verificarTextField(txtSenha))
			return false;
		if (!Validacao.verificarTextField(txtConfirmarSenha))
			return false;

		if(txtUsuario.getText().contains("@")) {
			Util.MessageBoxShow("Erro ao Cadastrar", "O Usuário inderido é inválido", AlertType.ERROR);
			return false;
		}

		if(!Validacao.validarEmail(txtEmail.getText())) {
			Util.MessageBoxShow("Erro ao Cadastrar", "O E-mail inserido é inválido", AlertType.ERROR);
			return false;
		}


		if(txtCPFouCNPJ.getText().length() == 14) {
			tipo = true; //tipo cnpj
			return Validacao.validarCNPJ(txtCPFouCNPJ);

		}
		else {
			tipo = false; //tipo cpf
			return Validacao.validarCPF(txtCPFouCNPJ);
		}

	}

	public void initialize() {

	}

	@FXML
	void RealizarLogin(MouseEvent event) {
		Login login = new Login();
		login.start(new Stage());
		Stage stageAtual = (Stage) lbLogar.getScene().getWindow();
		stageAtual.close();
	}
	void realizarCadastro(boolean tipo) {

		try {
			if(validarCredenciais()) {


				if(txtSenha.getText().equals(txtConfirmarSenha.getText()))
				{
					if(tipo) {
						Banco.InserirQuery(String.format("INSERT INTO parceiro(id,nome,usuario,tipo,cnpj,email,senha) VALUES(default,'%s','%s','%s','%s','%s','%s')",
								txtNome.getText(), txtUsuario.getText(),"1",  txtCPFouCNPJ.getText(), txtEmail.getText(), Util.criptografarSenha(txtSenha.getText())));
					}
					else {

						Banco.InserirQuery(String.format("INSERT INTO parceiro(id,nome,usuario,tipo,cpf,email,senha) VALUES(default,'%s','%s','%s','%s','%s','%s')",
								txtNome.getText(), txtUsuario.getText(),"0",txtCPFouCNPJ.getText(), txtEmail.getText(), Util.criptografarSenha(txtSenha.getText())));
					}

					Util.MessageBoxShow("Cadastar", "Cadastro realizado com sucesso", AlertType.INFORMATION);
				}
				else { 

					Util.MessageBoxShow("Erro ao Cadastrar", "As senhas inseridas não correspondem", AlertType.ERROR);
					txtConfirmarSenha.requestFocus();



				}
			}
			else {

				Util.MessageBoxShow("Erro ao Cadastrar", "Alguma credencial ja é existente", AlertType.WARNING);
			}
		}
		catch (Exception ex){

			Util.MessageBoxShow("Cadastar", "Erro ao Cadastar: " + ex.getMessage(), AlertType.ERROR);
		}

	}

	private boolean validarCredenciais() throws SQLException{

		ResultSet result;

		if(tipo) 
		{
			result = Banco.InserirQueryReader(String.format("SELECT * FROM parceiro WHERE usuario = '%s' OR email = '%s' OR cnpj = '%s'" 
					, txtUsuario.getText(), txtEmail.getText(), txtCPFouCNPJ.getText()));
		}
		else
		{
			result = Banco.InserirQueryReader(String.format("SELECT * FROM parceiro WHERE usuario = '%s' OR email = '%s' OR cpf = '%s'" 
					, txtUsuario.getText(), txtEmail.getText(), txtCPFouCNPJ.getText()));

		}
		if(result.next())
		{
			System.out.print("encontrei");
			return false;
		}
		else
			return true;
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
