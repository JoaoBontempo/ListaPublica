package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.sun.javafx.geom.Point2D;

import classes.Banco;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
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

public class Cadastrar extends Application {

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
	void realizarCadastro(ActionEvent event) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		if (ValidarCampos())
			realizarCadastro(tipo);
	}

	public boolean tipo = false;

	boolean ValidarCampos() {

		if (!Validacao.verificarTextField(txtNome)) return false; 
		if(!Validacao.verificarTextField(txtUsuario)) return false; 
		if(!Validacao.verificarTextField(txtEmail)) return false; 
		if(!Validacao.verificarTextField(txtCPFouCNPJ)) return false; 
		if(!Validacao.verificarTextField(txtSenha)) return false; 
		if(!Validacao.verificarTextField(txtConfirmarSenha)) return false;

		if (!txtUsuario.getText().matches("[a-zA-Z]+"))/* regex para ver somente letras e acentuadas */ {
			System.out.print("não é letra");
			Util.MessageBoxShow("Erro ao Cadastrar", "O Usuário inserido é inválido, insira somente letras",
					AlertType.ERROR);
			return false;
		}

		if (!Validacao.validarEmail(txtEmail.getText())) {
			Util.MessageBoxShow("Erro ao Cadastrar", "O E-mail inserido é inválido", AlertType.ERROR);
			return false;
		}

		if (txtCPFouCNPJ.getText().length() == 14) {
			tipo = true; // tipo cnpj
			return Validacao.validarCNPJ(txtCPFouCNPJ);

		} else {
			tipo = false; // tipo cpf
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

			if (validarCredenciais(tipo)) {

				if (txtSenha.getText().equals(txtConfirmarSenha.getText())) {
					if (tipo) {
						Banco.InserirQuery(String.format(
								"INSERT INTO parceiro(id,nome,usuario,tipo,cnpj,email,senha) VALUES(default,'%s','%s','%s','%s','%s','%s')",
								txtNome.getText(), txtUsuario.getText(), "1", txtCPFouCNPJ.getText(),
								txtEmail.getText(), Util.criptografarSenha(txtSenha.getText())));
					} else {

						Banco.InserirQuery(String.format(
								"INSERT INTO parceiro(id,nome,usuario,tipo,cpf,email,senha) VALUES(default,'%s','%s','%s','%s','%s','%s')",
								txtNome.getText(), txtUsuario.getText(), "0", txtCPFouCNPJ.getText(),
								txtEmail.getText(), Util.criptografarSenha(txtSenha.getText())));
					}

					Util.MessageBoxShow("Cadastar", "Cadastro realizado com sucesso", AlertType.INFORMATION);
				} else {

					Util.MessageBoxShow("Erro ao Cadastrar", "As senhas inseridas não correspondem", AlertType.ERROR);
					txtConfirmarSenha.requestFocus();

				}
			} 
		} catch (Exception ex) {

			Util.MessageBoxShow("Cadastar", "Erro ao Cadastar: " + ex.getMessage(), AlertType.ERROR);
		}

	}

	private boolean validarCredenciais(boolean tipo) throws SQLException {

		ResultSet result;

		if (tipo) {
			result = Banco.InserirQueryReader(
					String.format("SELECT * FROM parceiro WHERE usuario = '%s' OR email = '%s' OR cnpj = '%s'",
							txtUsuario.getText(), txtEmail.getText(), txtCPFouCNPJ.getText()));
		} else {
			result = Banco.InserirQueryReader(
					String.format("SELECT * FROM parceiro WHERE usuario = '%s' OR email = '%s' OR cpf = '%s'",
							txtUsuario.getText(), txtEmail.getText(), txtCPFouCNPJ.getText()));

		}
		if (result.next()) {
			int num = 0;
			String msg = "";
			if (result.getString("usuario").equals(txtUsuario.getText())) {
				msg += "usuario";
				num++;
				System.out.print(num);
			}

			if (result.getString("email").equals(txtEmail.getText())) {
				if (!Validacao.isNullOrEmpty(msg))
					msg += ", email";
				else
					msg += "email";

				num++;
				System.out.print(num);
			}
			if (tipo) {
				//System.out.print("cnpj");
				if (!Validacao.isNullOrEmpty(result.getString("cnpj")))
				{
					System.out.print("cnpj");
					if (result.getString("cnpj").equals(txtCPFouCNPJ.getText())) {
						if (!Validacao.isNullOrEmpty(msg))
							msg += ", cnpj";
						else
							msg += "cnpj";

						num++;
					}
				}

			} else {
				//System.out.print("cpf");
				if (!Validacao.isNullOrEmpty(result.getString("cpf")))
				{
					System.out.print("cpf");
					if (result.getString("cpf").equals(txtCPFouCNPJ.getText())) {
						if (!Validacao.isNullOrEmpty(msg))
							msg += " e cpf";
						else
							msg += "cpf";
						
						num++;
					}
				}
			}
			System.out.print(num);
			if (num > 1) {
				
				if(num == 2)
					 msg.replace(',', 'e');
				
				Util.MessageBoxShow("Erro ao Cadastrar", "As credenciais " + msg + " ja são existentes", AlertType.WARNING);
			}
			else
				Util.MessageBoxShow("Erro ao Cadastrar", "A credencial " + msg + " ja é existente", AlertType.WARNING);
			
			return false;
		} else
			return true;
	}

	//private Event evento;

	//public void setEvento(Event evento) {
	//this.evento = evento;
	//}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("telaCadastro.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Cadastrar");
			primaryStage.getIcons().add(icon);
			//primaryStage.initModality(Modality.WINDOW_MODAL);
			//primaryStage.initOwner(((Node) evento.getSource()).getScene().getWindow());
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
