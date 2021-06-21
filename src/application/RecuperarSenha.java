package application;

import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.geonames.Toponym;

import classes.Banco;
import classes.Email;
import classes.Parceiro;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private TextField txtCPF_ou_CNPJ;

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
    private Label lbTempoRestante;
    
    @FXML
    private TextField txtEmail;

	@FXML
	void teclaPressionada(KeyEvent event) throws SQLException {
		if (event.getCode().equals(KeyCode.ENTER)) {
			// se pressionar enter na textbox executa o comando do botao
			VerificarEntradaEmail(null);
		}
	}
	
	int tentativas = 0;
	
	/**
	 * Essa fun��o ir� gerar randomicamente uma sequ�ncia de n�meros de X tamanho 
	 * 
	 * @param contador: Contador de parada
	 * @param valorAtual: Valor atual concatenado a cada chamada de rotina
	 * @param tamanhoMaximo: Tamanho m�ximo que o c�digo dever� conter
	 * @return
	 */
	public static String gerarCodigo(int contador, String valorAtual, int tamanhoMaximo){
		if(contador == tamanhoMaximo) return valorAtual;
		return gerarCodigo(++contador,valorAtual+=( new Random().nextInt(9)),tamanhoMaximo);
	}


	void validarCamposTxt(String texto,TextField textField) {

		if(texto.length()>1) {
			Util.MessageBoxShow("Opera��o inv�lida", "Digite apenas um n�mero em cada campo", AlertType.WARNING);
			textField.setText("");
			return;
		}
		if(!(texto.trim().matches("\\d+")) ) {
			textField.clear();
			//JOptionPane.showMessageDialog(null, "Digite apenas n�meros no campo.");
			return;
		}
	}
	/**
	 * Essa fun��o vai verificar se o que o usu�rio digitou � numero, e validar para
	 * que digite apenas um d�gito por campo
	 * 
	 * @param event
	 */

	@FXML
	void inserirDigito(KeyEvent event) {
		// faz verifica��es simples primeiro (TAB)

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
	
	Thread thread;
	int segundos = 0 ;
	boolean codigoCerto = false;
	
	private void iniciarTimer()
	{
		thread = new Thread(new Runnable() {
			@Override
			public void run()
			{
				try {
					for (int i = 120; i >= 0; i--)
					{
						Thread.sleep(1000);
						segundos = i;
						setLabelTemporizador();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	public void setLabelTemporizador()
	{
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				if (codigoCerto)
				{
					lbTempoRestante.setVisible(false);;
				}
				// TODO Auto-generated method stub
				lbTempoRestante.setText("Tempo restante: " + segundos + " segundos");
				
				if (segundos == 0 && !codigoCerto)
				{
					lbTempoRestante.setVisible(false);;
					Util.MessageBoxShow("Tempo esgotado", "O c�digo de valida��o de troca de senha expirou."
							+ "\nPor favor, solicite um novo c�digo", AlertType.INFORMATION);
					Stage stageAtual = (Stage) btnConfirmarCodigo.getScene().getWindow();
					stageAtual.close();
				}
			}
		});
	}

	@FXML
	void ConfirmarCodigo(ActionEvent event) {
		if(txtNumeroUm.getText().isEmpty() || txtNumeroDois.getText().isEmpty() || txtNumeroTres.getText().isEmpty()
				|| txtNumeroQuatro.getText().isEmpty() || txtNumeroCinco.getText().isEmpty()) {
			tentativas--;
			if (tentativas == 0)
			{
				Util.MessageBoxShow("Tentativas esgotadas", "Voc� n�o possui mais tentativas");
				Stage stageAtual = (Stage) btnConfirmarCodigo.getScene().getWindow();
				stageAtual.close();
				return;
			}
			Util.MessageBoxShow("C�digo errado", "Confirme novamente o c�digo digitado e tente novamente"
					+ "\nVoc� possui mais " + tentativas + " tentativas", 
					AlertType.ERROR);
			return;	
		}
		String builder=txtNumeroUm.getText()+txtNumeroDois.getText()+txtNumeroTres.getText()+txtNumeroQuatro.getText()
		+txtNumeroCinco.getText();
		if(builder.equals(codigoGerado)) {
			Parceiro parceiro = new Parceiro();
			parceiro.setId(id);
			Util.setContaLogada(parceiro);
			codigoCerto = true;
			Stage stageAtual = (Stage) btnConfirmarCodigo.getScene().getWindow();
			stageAtual.close();
			// form trocar senha
			TrocarSenha telaTrocarSenha = new TrocarSenha();
			telaTrocarSenha.setEmail(email);
			telaTrocarSenha.getEvent(event);
			telaTrocarSenha.start(new Stage());

		}else {
			tentativas--;
			if (tentativas == 0)
			{
				Util.MessageBoxShow("Tentativas esgotadas", "Voc� n�o possui mais tentativas");
				Stage stageAtual = (Stage) btnConfirmarCodigo.getScene().getWindow();
				stageAtual.close();
				return;
			}
			Util.MessageBoxShow("C�digo errado", "Confirme novamente o c�digo digitado e tente novamente"
					+ "\nVoc� possui mais " + tentativas + " tentativas", 
					AlertType.ERROR);
		}
	}

	private String verificarTipo() {
		// CPF || CNPJ

		if (txtCPF_ou_CNPJ.getText().length() == 11)
			return "CPF";
		else
			return "CNPJ";

	}
	
	private String email = "";
	private int id;
	
	@FXML
	void VerificarEntradaEmail(ActionEvent event) throws SQLException {
		if (!Validacao.verificarTextField(txtCPF_ou_CNPJ))
			return;
		if (!Validacao.verificarTextField(txtEmail))
			return;
		if (!txtCPF_ou_CNPJ.getText().matches("[0-9]+"))
		{
			Util.MessageBoxShow("Campo inv�lido", "Por favor, insira apenas os digitos do seu CPF/CNPJ.", AlertType.INFORMATION);
			return;
		}
		if (txtCPF_ou_CNPJ.getText().length() != 11 && txtCPF_ou_CNPJ.getText().length() != 14)
		{
			Util.MessageBoxShow("Campo inv�lido", "Por favor, insira apenas os digitos do seu CPF/CNPJ de forma correta.", AlertType.INFORMATION);
			return;
		}
		else
		{
			String tipo = verificarTipo();
			ResultSet result = Banco.InserirQueryReader(String.format("SELECT id, email FROM parceiro WHERE %s = '%s'", tipo.toLowerCase()
					,txtCPF_ou_CNPJ.getText()));

			if (result.next())
			{
				email = result.getString("email");
				if (!email.equals(txtEmail.getText()))
				{
					Util.MessageBoxShow("E-mail incorreto", "O e-mail inserido n�o est� atribuido ao respectivo CPF/CNPJ."
							+ "\nPor favor, verifique suas informa��es", AlertType.WARNING);
					return;
				}
			}
			else
			{
				Util.MessageBoxShow(tipo + " n�o encontrado", "O " + tipo + " informado n�o est� cadastrado!", AlertType.INFORMATION);
				return;
			}
			
			
			// se chegou aqui ent�o ok. Gere o c�digo, armazene, Envie o c�digo e mostre o panel para colocar os
			codigoGerado=gerarCodigo(0,"",5);
			id = result.getInt("id");
			//if(Email.enviarEmail("Seu c�digo de verifica��o : "+codigoGerado,"C�digo de verifica��o",txtEmailPrincipal.getText()))
			if(Email.enviarEmail("Seu c�digo de verifica��o � : " + codigoGerado +
					"\n\nEste c�digo ir� expirar em 2 minutos.","C�digo de verifica��o", email)) {
				pnlCodigos.setVisible(true);
				btnConfirmarCodigo.setVisible(true);
				btnEnviarCodigo.setVisible(false);
				codigoEnviado=true;	
				tentativas = 3;
				lbTempoRestante.setVisible(true);
				iniciarTimer();
				Util.MessageBoxShow("C�digo enviado!", "O c�digo de confirma��o foi enviado para seu e-mail com sucesso!", AlertType.INFORMATION);
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
			primaryStage.setTitle("Lista P�blica - Esqueci a senha");
			primaryStage.setResizable(false);
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
