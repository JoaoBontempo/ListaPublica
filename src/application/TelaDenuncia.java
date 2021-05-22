package application;

import classes.Email;
import classes.Util;
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
	public void enviarDenuncia()
	{
		Util.getDenunciAtual().setTipo(cboxMotivo.getSelectionModel().getSelectedItem() != "Outro" ? cboxMotivo.getSelectionModel().getSelectedItem(): txtOutro_motivo.getText());
		String conteudo = "Nova denúncia aberta - " + Util.getDenunciAtual().getTipo() + "\n\n";
		conteudo += "Informações do denunciado: \n"
				+ "ID: " + Util.getDenunciAtual().getDenunciado().getId() + "\n"
				+ "Nome: " + Util.getDenunciAtual().getDenunciado().getNome() + "\n"
				+ Util.getDenunciAtual().getDenunciado().getCnpj() != null ? "CNPJ: " + Util.getDenunciAtual().getDenunciado().getCnpj() :"CPF: " + Util.getDenunciAtual().getDenunciado().getCpf() + "\n"
				+ "E-mail: " + Util.getDenunciAtual().getDenunciado().getEmail() + "\n"
				+ "Usuário: " + Util.getDenunciAtual().getDenunciado().getUsuario() + "\n"
				+ "\nInformações do denunciador: \n"
				+ "ID: " + Util.getDenunciAtual().getDenunciador().getId() + "\n"
				+ "Nome: " + Util.getDenunciAtual().getDenunciador().getNome() + "\n"
				+ Util.getDenunciAtual().getDenunciador().getCnpj() != null ? "CNPJ: " + Util.getDenunciAtual().getDenunciado().getCnpj() :"CPF: " + Util.getDenunciAtual().getDenunciado().getCpf() + "\n"
				+ "E-mail: " + Util.getDenunciAtual().getDenunciador().getEmail() + "\n"
				+ "Usuário: " + Util.getDenunciAtual().getDenunciador().getUsuario() + "\n"
				+ "\nInformações do telefone denunciado: \n"
				+ "ID: " + Util.getDenunciAtual().getTelefone().getId() + "\n"
				+ "Número: " + Util.getDenunciAtual().getTelefone().getNumero() + "\n"
				+ "Descrição: " + Util.getDenunciAtual().getTelefone().getDescricao() + "\n"
				+ "\nInformações do endereço denunciado: \n"
				+ "ID: " + Util.getDenunciAtual().getEndereco().getId() + "\n"
				+ "Rua: " + Util.getDenunciAtual().getEndereco().getRua() + "\n"
				+ "Número: " + Util.getDenunciAtual().getEndereco().getNumero() + "\n"
				+ "Bairro: " + Util.getDenunciAtual().getEndereco().getBairro() + "\n"
				+ "Cidade: " + Util.getDenunciAtual().getEndereco().getBairro() + "\n"
				+ "Estado: " + Util.getDenunciAtual().getEndereco().getEstado() + "\n"
				+ "Nome: " + Util.getDenunciAtual().getEndereco().getNome() + "\n"
				+ "\nInformações da denúncia\n"
				+ "ID: " + Util.getDenunciAtual().getId() + "\n"
				+ "Descrição: " + Util.getDenunciAtual().getDescricao() + "\n"
				+ "Local: " + Util.getDenunciAtual().getLocal();
		if (verificarCampos())
		{
			if(Email.enviarEmail(Util.getDenunciAtual().getTipo(), conteudo))
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
