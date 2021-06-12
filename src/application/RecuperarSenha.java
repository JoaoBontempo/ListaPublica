package application;

import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.geonames.Toponym;

import classes.Banco;
import classes.Email;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecuperarSenha extends Application {
	private boolean codigoEnviado=false;
	private String codigoGerado="";

	@FXML
	private Button btnConfirmarCodigo;

	@FXML
	private TextField txtInformacao;

	@FXML
	private Button btnEnviarCodigo;

	@FXML
	private Pane pnlCodigos;

	@FXML
	private TextField txtNumeroUm;

	@FXML
	private TextField txtNumeroDois;

	@FXML
	private TextField txtNumeroTres;

	@FXML
	private TextField txtNumeroQuatro;

	@FXML
	private TextField txtNumeroCinco;

	@FXML
	void teclaPressionada(KeyEvent event) throws SQLException {
		if (event.getCode().equals(KeyCode.ENTER)) {
			// se pressionar enter na textbox executa o comando do botao
			VerificarEntradaEmail(null);
		}
	}

	/**
	 * Essa funï¿½ï¿½o irï¿½ gerar randomicamente uma sequï¿½ncia de nï¿½meros de X tamanho 
	 * 
	 * @param contador: Contador de parada
	 * @param valorAtual: Valor atual concatenado a cada chamada de rotina
	 * @param tamanhoMaximo: Tamanho mï¿½ximo que o cï¿½digo deverï¿½ conter
	 * @return
	 */
	public static String gerarCodigo(int contador, String valorAtual, int tamanhoMaximo){
		if(contador == tamanhoMaximo) return valorAtual;
		return gerarCodigo(++contador,valorAtual+=( new Random().nextInt(9)),tamanhoMaximo);
	}


	void validarCamposTxt(String texto,TextField textField) {

		if(texto.length()>1) {
			JOptionPane.showMessageDialog(null, "Digite apenas um nï¿½mero no campo.");
			Character textoPadrao=textField.getText().toString().charAt(0);
			textField.setText(Character.toString(textoPadrao));
			return;
		}
		if(!(texto.trim().matches("\\d+")) ) {
			textField.clear();
			//JOptionPane.showMessageDialog(null, "Digite apenas nï¿½meros no campo.");
			return;
		}
	}
	/**
	 * Essa funï¿½ï¿½o vai verificar se o que o usuï¿½rio digitou ï¿½ numero, e validar para
	 * que digite apenas um dï¿½gito por campo
	 * 
	 * @param event
	 */

	@FXML
	void inserirDigito(KeyEvent event) {
		// faz verificações simples primeiro (TAB)

		Node no=(Node)event.getSource();
		String textChamou = no.getId();
		switch (textChamou) {
		case "txtNumeroUm":
			validarCamposTxt(txtNumeroUm.getText(),txtNumeroUm);
			break;
		case "txtNumeroDois":
			validarCamposTxt(txtNumeroDois.getText(),txtNumeroDois);
			break;
		case "txtNumeroTres":
			validarCamposTxt(txtNumeroTres.getText(),txtNumeroTres);
			break;
		case "txtNumeroQuatro":
			validarCamposTxt(txtNumeroQuatro.getText(),txtNumeroQuatro);
			break;
		case "txtNumeroCinco":
			validarCamposTxt(txtNumeroCinco.getText(),txtNumeroCinco);
			break;
		default:
			break;
		}
	}

	@FXML
	void ConfirmarCodigo(ActionEvent event) {
		if(txtNumeroUm.getText().isEmpty() || txtNumeroDois.getText().isEmpty() || txtNumeroTres.getText().isEmpty()
				|| txtNumeroQuatro.getText().isEmpty() || txtNumeroCinco.getText().isEmpty()) {
			Util.MessageBoxShow("Código incompleto", "Confirme novamente o código digitado e tente novamente", 
					AlertType.ERROR);
			return;
		}
		String builder=txtNumeroUm.getText()+txtNumeroDois.getText()+txtNumeroTres.getText()+txtNumeroQuatro.getText()
		+txtNumeroCinco.getText();
		if(builder.equals(codigoGerado)) {
			Stage stageAtual = (Stage) btnConfirmarCodigo.getScene().getWindow();
			stageAtual.close();
			// form trocar senha
			TrocarSenha telaTrocarSenha = new TrocarSenha();
			telaTrocarSenha.setEmail(email);
			telaTrocarSenha.getEvent(event);
			telaTrocarSenha.start(new Stage());

		}else {
			Util.MessageBoxShow("Código errado", "Confirme novamente o código digitado e tente novamente", 
					AlertType.ERROR);
		}
	}

	private String verificarTipo() {
		// CPF || CNPJ

		if (txtInformacao.getText().length() == 11)
			return "CPF";
		else
			return "CNPJ";

	}
	
	private String email = "";

	@FXML
	void VerificarEntradaEmail(ActionEvent event) throws SQLException {

		if (!Validacao.verificarTextField(txtInformacao))
			return;
		if (!txtInformacao.getText().matches("[0-9]+"))
		{
			Util.MessageBoxShow("Campo inválido", "Por favor, insira apenas os digitos do seu CPF/CNPJ.", AlertType.INFORMATION);
			return;
		}
		if (txtInformacao.getText().length() != 11 && txtInformacao.getText().length() != 14)
		{
			Util.MessageBoxShow("Campo inválido", "Por favor, insira apenas os digitos do seu CPF/CNPJ de forma correta.", AlertType.INFORMATION);
			return;
		}
		else
		{
			String tipo = verificarTipo();
			ResultSet result = Banco.InserirQueryReader(String.format("SELECT email FROM parceiro WHERE %s = '%s'", tipo.toLowerCase()
					,txtInformacao.getText()));

			if (result.next())
			{
				email = result.getString("email");
			}
			else
			{
				Util.MessageBoxShow(tipo + " não encontrado", "O " + tipo + " informado não está cadastrado!", AlertType.INFORMATION);
				return;
			}

			// se chegou aqui então ok. Gere o código, armazene, Envie o código e mostre o panel para colocar os
			codigoGerado=gerarCodigo(0,"",5);
			System.out.println("Código: "+codigoGerado);
			//if(Email.enviarEmail("Seu código de verificação : "+codigoGerado,"Código de verificação",txtEmailPrincipal.getText()))
			if(Email.enviarEmail("Seu código de verificação é : " + codigoGerado,"Código de verificação", email)) {
				pnlCodigos.setVisible(true);
				btnConfirmarCodigo.setVisible(true);
				btnEnviarCodigo.setVisible(false);
				codigoEnviado=true;		
				Util.MessageBoxShow("Código enviado!", "O código de confirmação foi enviado para seu e-mail com sucesso!", AlertType.INFORMATION);
			}else {
				Util.MessageBoxShow("Falha ao enviar e-mail", "Falha ao enviar e-mail , verifique seu e-mail e tente novamente", AlertType.ERROR);
			}
		}

	}

	public void initialize() throws Exception
	{
		// ja deixa o botao de confirmar como falso
		btnConfirmarCodigo.setVisible(false);
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
	public MouseEvent evento;

	public void getEvent(MouseEvent evento) {
		this.evento = evento;
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

			//setar tela modal e tela que chamou 
			primaryStage.initModality(Modality.WINDOW_MODAL);
			primaryStage.initOwner(((Node)evento.getSource()).getScene().getWindow());

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
