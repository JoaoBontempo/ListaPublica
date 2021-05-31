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
	private TextArea txtDescrição;

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
			primaryStage.setTitle("Lista Pública - Login");
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
			Util.MessageBoxShow("Campo inválido", "Nenhum motivo de denúncia foi selecionado", AlertType.WARNING);
			return false;
		}
		if (txtOutro_motivo.editableProperty().get())
			if (!Validacao.verificarTextField(txtOutro_motivo))
				return false;
		if (!Validacao.verificarTextArea(txtDescrição))
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
			//String conteudo = "Nova denúncia aberta - " + Util.getDenunciAtual().getTipo() + "\n";
			/*conteudo += "\nInformações do denunciado:"
			+ "ID: " + Util.getDenunciAtual().getDenunciado().getId() + "\n"
			+ "Nome: " + Util.getDenunciAtual().getDenunciado().getNome() + "\n"
			+ Util.getDenunciAtual().getDenunciado().getCnpj() == null ? "CPF: " + Util.getDenunciAtual().getDenunciado().getCpf() : "CNPJ: " + Util.getDenunciAtual().getDenunciado().getCnpj()+ "\n"
			+ "E-mail: " + Util.getDenunciAtual().getDenunciado().getEmail() + "\n"
			+ "Usuário: " + Util.getDenunciAtual().getDenunciado().getUsuario() + "\n"
			+ "\nInformações do denunciador:"
			+ "ID: " + Util.getContaLogada().getId() + "\n"
			+ "Nome: " + Util.getContaLogada().getNome() + "\n"
			+ Util.getContaLogada().getCnpj() == null ? "CPF: " + Util.getContaLogada().getCpf() : "CNPJ: " + Util.getContaLogada().getCnpj()+ "\n"
			+ "E-mail: " + Util.getContaLogada().getEmail() + "\n"
			+ "Usuário: " + Util.getContaLogada().getUsuario() + "\n"
			+ "\nInformações do telefone denunciado: \n"
			+ "ID: " + Util.getDenunciAtual().getTelefone().getId() + "\n"
			+ "Número: " + Util.getDenunciAtual().getTelefone().getNumero() + "\n"
			+ "Descrição: " + Util.getDenunciAtual().getTelefone().getDescricao() + "\n";*/
			String conteudo = String.format("Nova denúncia aberta - %s"
					+ "\n---------------------------------------------------------------"
					+ "\nInformações do denunciado: "
					+ "\nID: %s"
					+ "\nNome: %s"
					+ "\nE-mail: %s"
					+ "\nUsuário: %s"
					+ "\n---------------------------------------------------------------"
					+ "\nInformações do denunciante: "
					+ "\nID: %s"
					+ "\nNome: %s"
					+ "\nE-mail: %s"
					+ "\nUsuário: %s"
					+ "\n---------------------------------------------------------------"
					+ "\nInformações do telefone denunciado: "
					+ "\nID: %s"
					+ "\nNúmero: %s"
					+ "\nDescrição: %s", 
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
						+ "\nInformações do endereço denunciado: \n"
						+ "ID: " + Util.getDenunciAtual().getEndereco().getId() + "\n"
						+ "Rua: " + Util.getDenunciAtual().getEndereco().getRua() + "\n"
						+ "Número: " + Util.getDenunciAtual().getEndereco().getNumero() + "\n"
						+ "Bairro: " + Util.getDenunciAtual().getEndereco().getBairro() + "\n"
						+ "Cidade: " + Util.getDenunciAtual().getEndereco().getBairro() + "\n"
						+ "Estado: " + Util.getDenunciAtual().getEndereco().getEstado() + "\n"
						+ "Nome: " + Util.getDenunciAtual().getEndereco().getNome() + "\n";
			}
			catch (Exception erro)
			{
				conteudo += "\n---------------------------------------------------------------"
						+ "\nNenhum endereço está atribuído a este telefone."
						+ "\n---------------------------------------------------------------"
						+ "\nDescrição da denúncia:\n"
						+ txtDescrição.getText();
			}			
			try
			{
				System.out.println(String.format("INSERT INTO denuncia (id, descricao, tipo, denunciado, denunciador, status_, tel, end_) "
						+ "VALUES (default, '%s', '%s', %s, %s, 0, %s, %s)", 
						txtDescrição.getText(), Util.getDenunciAtual().getTipo(), Util.getDenunciAtual().getDenunciado().getId(),
						Util.getContaLogada().getId(), Util.getDenunciAtual().getTelefone().getId(), Util.getDenunciAtual().getEndereco().getId()));
				Banco.InserirQuery(String.format("INSERT INTO denuncia (id, descricao, tipo, denunciado, denunciador, status_, tel, end_) "
						+ "VALUES (default, '%s', '%s', %s, %s, 0, %s, %s)", 
						txtDescrição.getText(), Util.getDenunciAtual().getTipo(), Util.getDenunciAtual().getDenunciado().getId(),
						Util.getContaLogada().getId(), Util.getDenunciAtual().getTelefone().getId(), Util.getDenunciAtual().getEndereco().getId()));
			}
			catch (Exception erro)
			{
				System.out.println(String.format("INSERT INTO denuncia (id, descricao, tipo, denunciado, denunciador, status_, tel) "
						+ "VALUES (default, '%s', '%s', %s, %s, 0, %s)", 
						txtDescrição.getText(), Util.getDenunciAtual().getTipo(), Util.getDenunciAtual().getDenunciado().getId(),
						Util.getContaLogada().getId(), Util.getDenunciAtual().getTelefone().getId()));
				Banco.InserirQuery(String.format("INSERT INTO denuncia (id, descricao, tipo, denunciado, denunciador, status_, tel) "
						+ "VALUES (default, '%s', '%s', %s, %s, 0, %s)", 
						txtDescrição.getText(), Util.getDenunciAtual().getTipo(), Util.getDenunciAtual().getDenunciado().getId(),
						Util.getContaLogada().getId(), Util.getDenunciAtual().getTelefone().getId()));
			}

			if(Email.enviarEmail(conteudo, "Nova denúncia de parceiro: " + Util.getDenunciAtual().getTipo()))
				Util.MessageBoxShow("Denúncia enviada com sucesso!", "Um e-mail com as informações foi enviado para os moderadores.\n"
						+ "\nSua denúncia será analisada.\n"
						+ "\nObrigado por contribuir para a melhoria do software!", AlertType.INFORMATION);
		}
	}

	public void initialize()
	{
		cboxMotivo.getItems().add("Número inexistente");
		cboxMotivo.getItems().add("Número e proprietário inconsistentes");
		cboxMotivo.getItems().add("Outra informação inconsistente");
		cboxMotivo.getItems().add("Palavras ofensivas");
		cboxMotivo.getItems().add("Outro");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
