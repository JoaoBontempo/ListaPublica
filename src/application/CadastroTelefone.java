package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import classes.Banco;
import classes.Endereco;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CadastroTelefone extends Application{

	@FXML
	private TextField txtNumero;

	@FXML
	private TextField txtDescricao;

	@FXML
	private ComboBox<String> cboxEndereco;

	private ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
	@FXML
	private Button btnCadastrar;

	public void initialize() throws SQLException
	{
		cboxEndereco.getItems().add("Nenhum endereço");
		//Aqui precisa recuperar os endereços da conta, só o nome pra colocar no comboBox
		ResultSet result = Banco.InserirQueryReader(String.format("SELECT id, nome FROM endereco WHERE usuario = " + Util.getContaLogada().getId()));
		while (result.next())
		{
			Endereco endereco = new Endereco();
			endereco.setId(result.getInt("id"));
			endereco.setNome(result.getString("nome"));
			enderecos.add(endereco);
			cboxEndereco.getItems().add(endereco.getNome());
		}
		cboxEndereco.getSelectionModel().selectFirst();
	}

	private boolean validarCampos()
	{
		if (!Validacao.verificarTextField(txtNumero))
			return false;
		if (!Validacao.verificarTextField(txtDescricao))
			return false;
		return true;
	}
	
	@FXML
	public void cadastrarTelefone() throws SQLException
	{
		if (validarCampos())
		{
			if (cboxEndereco.getSelectionModel().getSelectedIndex() != 0)
			{
				Banco.InserirQuery(String.format("INSERT INTO telefone (id, numero, dono, lugar, descricao) VALUES"
						+ " (default, '%s', %s, %s, '%s')", txtNumero.getText(), Util.getContaLogada().getId(), 
						enderecos.get(cboxEndereco.getSelectionModel().getSelectedIndex() - 1).getId(), txtDescricao.getText()));
			}
			else
			{
				Banco.InserirQuery(String.format("INSERT INTO telefone (id, numero, dono, descricao) VALUES"
						+ " (default, '%s', %s, '%s')", txtNumero.getText(), Util.getContaLogada().getId(), txtDescricao.getText()));
			}
			Util.MessageBoxShow("Cadastro realizado!", "Seu novo telefone foi cadastrado com sucesso!", AlertType.INFORMATION);
		}
	}


	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("telaNovoTelefone.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Cadastrar novo telefone");
			primaryStage.getIcons().add(icon);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
