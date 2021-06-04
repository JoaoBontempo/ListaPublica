package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Banco;
import classes.Parceiro;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class Login extends Application {

	private int id;

	@FXML
	private TextField txtUsuario;

	@FXML
	private Label lbEsqueciSenha;

	@FXML
	private Button btnLogar;

	@FXML
	private Label lbCadastrar;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private Button btnGuest;

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("telaLogin.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Login");
			primaryStage.getIcons().add(icon);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize() throws ClassNotFoundException, SQLException {
//		try {
//			Banco.Desconectar();
//		}
//		catch (Exception ex){
//
//		}
//		finally {
//
//			Banco.Conectar();
//		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private boolean verificarCampos() {
		if (!Validacao.verificarTextField(txtUsuario))
			return false;
		if (!Validacao.verificarTextField(txtSenha))
			return false;
		return true;
	}

	private String verificarTipoLogin() {
		// CPF || CNPJ
		if (txtUsuario.getText().matches("[0-9]+"))
			return "CPF/CNPJ";
		// E-MAIL
		else if (txtUsuario.getText().contains("@"))
			return "Email";
		else
			return "Usuario";
	}

	private boolean realizarLoginBanco(String tipo) throws SQLException {
		if (tipo.equals("Email")) {
			if (!Validacao.validarEmail(txtUsuario.getText())) {
				Util.MessageBoxShow("Campo inválido", "O email inserido é inválido ou está incorreto", AlertType.ERROR);
				return false;
			}
		}
		if (tipo.equals("CPF/CNPJ")) {
			// cpf
			if (txtUsuario.getText().length() == 11) {
				if (!Validacao.validarCPF(txtUsuario))
					return false;
				else {
					tipo = "CPF";
				}
			} else if (txtUsuario.getText().length() == 14) // cnpj
			{
				if (!Validacao.validarCNPJ(txtUsuario))
					return false;
				else {
					tipo = "CNPJ";
				}
			}
		}
		ResultSet result = Banco.InserirQueryReader(String.format("SELECT %s, senha, id FROM parceiro WHERE %s = '%s'",
				tipo.toLowerCase(), tipo.toLowerCase(), txtUsuario.getText()));

		if (!result.next()) {
			Util.MessageBoxShow("Falha ao realizar login", tipo + " não encontrado", AlertType.ERROR);
			return false;
		} else {
			if (Util.verificarSenha(txtSenha.getText(), result.getString("senha"))) {
				id = result.getInt("id");
				return true;
			} else {
				Util.MessageBoxShow("Falha ao realizar login", "Senha incorreta", AlertType.ERROR);
				return false;
			}
		}
	}

	private void loginRealizado() throws SQLException {

		if (!Util.isConvidado()) {
			ResultSet result = Banco.InserirQueryReader("SELECT * FROM parceiro WHERE id = " + id);
			result.next();
			Parceiro contaLogada = new Parceiro(result.getInt("id"), result.getInt("tipo"), result.getString("nome"),
					result.getString("cpf"), result.getString("cnpj"), result.getString("email"),
					result.getString("usuario"));
			Util.setContaLogada(contaLogada);

		}
		Dashboard dash = new Dashboard();
		dash.start(new Stage());
		Stage stageAtual = (Stage) btnLogar.getScene().getWindow();
		stageAtual.close();
	}

	@FXML
	public void RealizarLogin() throws SQLException {
		if (verificarCampos()) {
			if (realizarLoginBanco(verificarTipoLogin()))
				loginRealizado();
			else
				return;
		}
	}

	@FXML
	void meCadastrar(MouseEvent event) {

		Cadastrar cadastrar = new Cadastrar();
		//cadastrar.setEvento(event);
		cadastrar.start(new Stage());
		Stage stageAtual = (Stage) lbCadastrar.getScene().getWindow();
		stageAtual.close();
	}

	@FXML
	public void loginConvidado() throws SQLException {
		if (Util.MessageBoxShow("Tem certeza que deseja entrar como convidado?",
				"Só será possível visualizar telefones cadastrados\n" + "por outras pessoas ou empresas")
				.equals(ButtonType.OK)) {
			Util.setConvidado(true);
			loginRealizado();
		}
	}

	@FXML
	void esqueciSenha(MouseEvent event) {

		RecuperarSenha recuperar = new RecuperarSenha();
		recuperar.getEvent(event);
		recuperar.start(new Stage());
	}

}
