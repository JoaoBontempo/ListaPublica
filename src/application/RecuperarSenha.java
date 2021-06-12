package application;

import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.geonames.Toponym;

import classes.Banco;
import classes.Email;
import classes.Util;
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
	private TextField txtEmailPrincipal;

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
	void teclaPressionada(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			// se pressionar enter na textbox executa o comando do botao
			VerificarEntradaEmail(null);
		}
	}

	/**
	 * Essa função irá gerar randomicamente uma sequência de números de X tamanho 
	 * 
	 * @param contador: Contador de parada
	 * @param valorAtual: Valor atual concatenado a cada chamada de rotina
	 * @param tamanhoMaximo: Tamanho máximo que o código deverá conter
	 * @return
	 */
	public static String gerarCodigo(int contador, String valorAtual, int tamanhoMaximo){
		if(contador == tamanhoMaximo) return valorAtual;
		return gerarCodigo(++contador,valorAtual+=( new Random().nextInt(9)),tamanhoMaximo);
	}


	void validarCamposTxt(String texto,TextField textField) {

		if(texto.length()>1) {
			JOptionPane.showMessageDialog(null, "Digite apenas um número no campo.");
			Character textoPadrao=textField.getText().toString().charAt(0);
			textField.setText(Character.toString(textoPadrao));
			return;
		}
		if(!(texto.trim().matches("\\d+")) ) {
			textField.clear();
			//JOptionPane.showMessageDialog(null, "Digite apenas números no campo.");
			return;
		}
	}
	/**
	 * Essa função vai verificar se o que o usuário digitou é numero, e validar para
	 * que digite apenas um dígito por campo
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
			
			// form trocar senha
			TrocarSenha telaTrocarSenha = new TrocarSenha();
			telaTrocarSenha.setEmail(txtEmailPrincipal.getText());
			telaTrocarSenha.start(new Stage());

			Stage stageAtual = (Stage) btnConfirmarCodigo.getScene().getWindow();
			stageAtual.close();

		}else {
			Util.MessageBoxShow("Código errado", "Confirme novamente o código digitado e tente novamente", 
					AlertType.ERROR);
		}
	}


	@FXML
	void VerificarEntradaEmail(ActionEvent event) {
		if (!txtEmailPrincipal.getText().contains("@")) {
			JOptionPane.showMessageDialog(null, "Verifique seu e-mail e tente novamente.");
			return;
		}

		if(codigoEnviado) {
			// verificar como implementar essa verificação depois
			Util.MessageBoxShow("Código já enviado","O código ja foi enviado uma vez para o e-mail.",AlertType.WARNING);
			return;
		}

		// verifica se o e-mail existe
		String emailDigitado=txtEmailPrincipal.getText();
		
		try {
			Banco.InserirQueryReader("select id from parceiro where email='"+emailDigitado+"';");
			if(!Banco.getReader().next()) {
				// não existe
				Util.MessageBoxShow("E-mail inválido", "O e-mail informado não existe no sistema.");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// se chegou aqui então ok. Gere o código, armazene, Envie o código e mostre o panel para colocar os
		codigoGerado=gerarCodigo(0,"",5);
		//if(Email.enviarEmail("Seu código de verificação é : "+codigoGerado,"Código de verificação",txtEmailPrincipal.getText()))
		if(Email.enviarEmail("Seu código de verificação é : "+codigoGerado,"Código de verificação",txtEmailPrincipal.getText())) {
			pnlCodigos.setVisible(true);
			btnConfirmarCodigo.setVisible(true);
			btnEnviarCodigo.setVisible(false);
			codigoEnviado=true;			
		}else {
			Util.MessageBoxShow("Falha ao enviar e-mail", "Falha ao enviar e-mail , verifique seu e-mail e tente novamente", AlertType.ERROR);
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
