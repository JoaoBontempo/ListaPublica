package application;

import java.sql.SQLException;

import classes.Banco;
import classes.Email;
import classes.Util;
import classes.UtilDashboard;
import classes.Validacao;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TelaDenuncia extends Application {

	@FXML
	private ComboBox<String> cboxMotivo;

	@FXML
	private TextArea txtDescri��o;

	@FXML
	private Button btnDenuncia;

	@FXML
	private TextField txtOutro_motivo;

	@FXML
	private Label lbOutro;

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("telaDenuncia.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista P�blica - Login");
			primaryStage.getIcons().add(icon);
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private boolean verificarCampos()
	{
		if (cboxMotivo.getSelectionModel().getSelectedIndex() == -1)
		{
			Util.MessageBoxShow("Campo inv�lido", "Nenhum motivo de den�ncia foi selecionado", AlertType.WARNING);
			return false;
		}
		if (txtOutro_motivo.editableProperty().get())
			if (!Validacao.verificarTextField(txtOutro_motivo))
				return false;
		if (!Validacao.verificarTextArea(txtDescri��o))
			return false;
		return true;
	}

	@FXML
	private void changeCboxMotivos()
	{
		txtOutro_motivo.setText("");
		if (cboxMotivo.getSelectionModel().getSelectedItem().equals("Outro"))
		{
			lbOutro.setOpacity(1);
			txtOutro_motivo.setEditable(true);
			txtOutro_motivo.setOpacity(1);
			txtOutro_motivo.setCursor(Cursor.TEXT);
		}
		else
		{
			lbOutro.setOpacity(0.3);
			txtOutro_motivo.setOpacity(0.3);
			txtOutro_motivo.setEditable(false);
			txtOutro_motivo.setCursor(Cursor.DEFAULT);
		}
	}

	@FXML
	public void enviarDenuncia() throws SQLException
	{
		if (verificarCampos())
		{
			Util.RecuperarInformacoesEndereco(Integer.parseInt(UtilDashboard.getIdLugar()));
			Util.RecuperarInformacoesTelefoneAtual();
			Util.RecuperarInformacoesDenunciado(Integer.parseInt(UtilDashboard.getIdDono()));
			Util.getDenunciAtual().setTipo(cboxMotivo.getSelectionModel().getSelectedItem() != "Outro" ? cboxMotivo.getSelectionModel().getSelectedItem(): txtOutro_motivo.getText());
			//String conteudo = "Nova den�ncia aberta - " + Util.getDenunciAtual().getTipo() + "\n";
			/*conteudo += "\nInforma��es do denunciado:"
			+ "ID: " + Util.getDenunciAtual().getDenunciado().getId() + "\n"
			+ "Nome: " + Util.getDenunciAtual().getDenunciado().getNome() + "\n"
			+ Util.getDenunciAtual().getDenunciado().getCnpj() == null ? "CPF: " + Util.getDenunciAtual().getDenunciado().getCpf() : "CNPJ: " + Util.getDenunciAtual().getDenunciado().getCnpj()+ "\n"
			+ "E-mail: " + Util.getDenunciAtual().getDenunciado().getEmail() + "\n"
			+ "Usu�rio: " + Util.getDenunciAtual().getDenunciado().getUsuario() + "\n"
			+ "\nInforma��es do denunciador:"
			+ "ID: " + Util.getContaLogada().getId() + "\n"
			+ "Nome: " + Util.getContaLogada().getNome() + "\n"
			+ Util.getContaLogada().getCnpj() == null ? "CPF: " + Util.getContaLogada().getCpf() : "CNPJ: " + Util.getContaLogada().getCnpj()+ "\n"
			+ "E-mail: " + Util.getContaLogada().getEmail() + "\n"
			+ "Usu�rio: " + Util.getContaLogada().getUsuario() + "\n"
			+ "\nInforma��es do telefone denunciado: \n"
			+ "ID: " + Util.getDenunciAtual().getTelefone().getId() + "\n"
			+ "N�mero: " + Util.getDenunciAtual().getTelefone().getNumero() + "\n"
			+ "Descri��o: " + Util.getDenunciAtual().getTelefone().getDescricao() + "\n";*/
			String conteudo = String.format("Nova den�ncia aberta - %s"
					+ "\n---------------------------------------------------------------"
					+ "\nInforma��es do denunciado: "
					+ "\nID: %s"
					+ "\nNome: %s"
					+ "\nE-mail: %s"
					+ "\nUsu�rio: %s"
					+ "\n---------------------------------------------------------------"
					+ "\nInforma��es do denunciante: "
					+ "\nID: %s"
					+ "\nNome: %s"
					+ "\nE-mail: %s"
					+ "\nUsu�rio: %s"
					+ "\n---------------------------------------------------------------"
					+ "\nInforma��es do telefone denunciado: "
					+ "\nID: %s"
					+ "\nN�mero: %s"
					+ "\nDescri��o: %s", 
					Util.getDenunciAtual().getTipo(),
					Util.getDenunciAtual().getDenunciado().getId(), 
					Util.getDenunciAtual().getDenunciado().getNome(),
					Util.getDenunciAtual().getDenunciado().getEmail(),
					Util.getDenunciAtual().getDenunciado().getUsuario(),
					Util.getContaLogada().getId(), 
					Util.getContaLogada().getNome(),
					Util.getContaLogada().getEmail(),
					Util.getContaLogada().getUsuario(),
					Util.getDenunciAtual().getTelefone().getId(),
					Util.getDenunciAtual().getTelefone().getNumero(),
					Util.getDenunciAtual().getTelefone().getDescricao());
			try
			{
				conteudo += "\n---------------------------------------------------------------"
						+ "\nInforma��es do endere�o denunciado: \n"
						+ "ID: " + Util.getDenunciAtual().getEndereco().getId() + "\n"
						+ "Rua: " + Util.getDenunciAtual().getEndereco().getRua() + "\n"
						+ "N�mero: " + Util.getDenunciAtual().getEndereco().getNumero() + "\n"
						+ "Bairro: " + Util.getDenunciAtual().getEndereco().getBairro() + "\n"
						+ "Cidade: " + Util.getDenunciAtual().getEndereco().getBairro() + "\n"
						+ "Estado: " + Util.getDenunciAtual().getEndereco().getEstado() + "\n"
						+ "Nome: " + Util.getDenunciAtual().getEndereco().getNome() + "\n";
			}
			catch (Exception erro)
			{
				conteudo += "\n---------------------------------------------------------------"
						+ "\nNenhum endere�o est� atribu�do a este telefone."
						+ "\n---------------------------------------------------------------"
						+ "\nDescri��o da den�ncia:\n"
						+ txtDescri��o.getText();
			}			
			try
			{
				System.out.println(String.format("INSERT INTO denuncia (id, descricao, tipo, denunciado, denunciador, status_, tel, end_) "
						+ "VALUES (default, '%s', '%s', %s, %s, 0, %s, %s)", 
						txtDescri��o.getText(), Util.getDenunciAtual().getTipo(), Util.getDenunciAtual().getDenunciado().getId(),
						Util.getContaLogada().getId(), Util.getDenunciAtual().getTelefone().getId(), Util.getDenunciAtual().getEndereco().getId()));
				Banco.InserirQuery(String.format("INSERT INTO denuncia (id, descricao, tipo, denunciado, denunciador, status_, tel, end_) "
						+ "VALUES (default, '%s', '%s', %s, %s, 0, %s, %s)", 
						txtDescri��o.getText(), Util.getDenunciAtual().getTipo(), Util.getDenunciAtual().getDenunciado().getId(),
						Util.getContaLogada().getId(), Util.getDenunciAtual().getTelefone().getId(), Util.getDenunciAtual().getEndereco().getId()));
			}
			catch (Exception erro)
			{
				System.out.println(String.format("INSERT INTO denuncia (id, descricao, tipo, denunciado, denunciador, status_, tel) "
						+ "VALUES (default, '%s', '%s', %s, %s, 0, %s)", 
						txtDescri��o.getText(), Util.getDenunciAtual().getTipo(), Util.getDenunciAtual().getDenunciado().getId(),
						Util.getContaLogada().getId(), Util.getDenunciAtual().getTelefone().getId()));
				Banco.InserirQuery(String.format("INSERT INTO denuncia (id, descricao, tipo, denunciado, denunciador, status_, tel) "
						+ "VALUES (default, '%s', '%s', %s, %s, 0, %s)", 
						txtDescri��o.getText(), Util.getDenunciAtual().getTipo(), Util.getDenunciAtual().getDenunciado().getId(),
						Util.getContaLogada().getId(), Util.getDenunciAtual().getTelefone().getId()));
			}

			if(Email.enviarEmail(conteudo, "Nova den�ncia de parceiro: " + Util.getDenunciAtual().getTipo()))
				Util.MessageBoxShow("Den�ncia enviada com sucesso!", "Um e-mail com as informa��es foi enviado para os moderadores.\n"
						+ "\nSua den�ncia ser� analisada.\n"
						+ "\nObrigado por contribuir para a melhoria do software!", AlertType.INFORMATION);
		}
	}

	public void initialize()
	{
		cboxMotivo.getItems().add("N�mero inexistente");
		cboxMotivo.getItems().add("N�mero e propriet�rio inconsistentes");
		cboxMotivo.getItems().add("Outra informa��o inconsistente");
		cboxMotivo.getItems().add("Palavras ofensivas");
		cboxMotivo.getItems().add("Outro");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
