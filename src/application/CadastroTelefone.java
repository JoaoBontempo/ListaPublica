package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import classes.Banco;
import classes.CadastroTelUtil;
import classes.Endereco;
import classes.Telefone;
import classes.Util;
import classes.Validacao;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CadastroTelefone extends Application{

	@FXML
	private TextField txtNumero;

	@FXML
	private TextField txtDescri��o;

	@FXML
	private TextField txtDDD;

	@FXML
	private Label lbTelefone;
	@FXML
	private ComboBox<String> cboxEndereco;

	private ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
	@FXML
	private Button btnCadastrar;



	public CadastroTelefone() {
		// TODO Auto-generated constructor stub
	}

	public void initialize() throws SQLException
	{
		cboxEndereco.getItems().add("Nenhum endere�o");
		//Aqui precisa recuperar os endere�os da conta, s� o nome pra colocar no comboBox
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

		if(CadastroTelUtil.isCaso()) {

			lbTelefone.setText(CadastroTelUtil.getLbTelefone());
			btnCadastrar.setText(CadastroTelUtil.getBtnTxt());
			txtDescri��o.setText(CadastroTelUtil.getTelefone().getDescricao());
			txtNumero.setText(CadastroTelUtil.getTelefone().getNumero().substring(2, CadastroTelUtil.getTelefone().getNumero().length()));
			txtDDD.setText(CadastroTelUtil.getTelefone().getNumero().substring(0,2));

			txtNumero.setDisable(true);
			txtDDD.setDisable(true);

			if(cboxEndereco.getItems().contains(CadastroTelUtil.getTelefone().getEndereco().getNome())){
				cboxEndereco.getSelectionModel().select(CadastroTelUtil.getTelefone().getEndereco().getNome());
			}
		}

	}

	private boolean validarCampos() throws SQLException
	{
		if (!Validacao.verificarTextField(txtDDD))
			return false;
		if (!Validacao.verificarTextField(txtNumero))
			return false;
		if (!Validacao.verificarNumerosTextField(txtDDD))
			return false;
		if (!Validacao.verificarNumerosTextField(txtNumero))
			return false;
		if (txtDDD.getText().length() != 2)
		{
			Util.MessageBoxShow("Campo inv�lido", "O campo 'DDD' deve conter exatamente dois n�meros.", AlertType.ERROR);
			txtDDD.requestFocus();
			return false;
		}
		if (!Validacao.verificarTextField(txtDescri��o))
			return false;

		ResultSet result = Banco.InserirQueryReader(String.format("SELECT id FROM telefone WHERE telefone.numero = '%s'", txtDDD.getText() + txtNumero.getText()));
		if (result.next())
		{
			Util.MessageBoxShow("Cadastro inv�lido","Este telefone j� est� cadastrado", AlertType.ERROR);
			return false;
		}
		return true;
	}

	private void LimparCampos()
	{
		txtDDD.setText("");
		txtNumero.setText("");
		txtDescri��o.setText("");
		cboxEndereco.getSelectionModel().selectFirst();
	}

	@FXML
	public void cadastrarTelefone() throws SQLException, IOException
	{
		if(!CadastroTelUtil.isCaso()) {

			if (validarCampos())
			{
				if (cboxEndereco.getSelectionModel().getSelectedIndex() != 0)
				{
					Banco.InserirQuery(String.format("INSERT INTO telefone (id, numero, dono, lugar, descricao) VALUES"
							+ " (default, '%s', %s, %s, '%s')", txtDDD.getText() + txtNumero.getText(), Util.getContaLogada().getId(), 
							enderecos.get(cboxEndereco.getSelectionModel().getSelectedIndex() - 1).getId(), txtDescri��o.getText()));
				}
				else
				{
					Banco.InserirQuery(String.format("INSERT INTO telefone (id, numero, dono, descricao) VALUES"
							+ " (default, '%s', %s, '%s')", txtDDD.getText() + txtNumero.getText(), Util.getContaLogada().getId(), txtDescri��o.getText()));
				}
				Util.MessageBoxShow("Cadastro realizado!", "Seu novo telefone foi cadastrado com sucesso!", AlertType.INFORMATION);
				LimparCampos();
				Util.dashboard.AtualizarCbxTelefones();
			}
		}
		else {

			if(!Validacao.verificarTextField(txtDescri��o))
				return;

			if (cboxEndereco.getSelectionModel().getSelectedIndex() != 0)
			{
				Banco.InserirQuery(String.format("UPDATE telefone SET lugar = %s, descricao = '%s' WHERE id = %s "
						, enderecos.get(cboxEndereco.getSelectionModel().getSelectedIndex() - 1).getId(), txtDescri��o.getText(), CadastroTelUtil.getTelefone().getId()));
			}
			else
			{
				Banco.InserirQuery(String.format("UPDATE telefone SET  lugar = null, descricao = '%s' WHERE id = %s "
						, txtDescri��o.getText(), CadastroTelUtil.getTelefone().getId()));
			}
			Util.MessageBoxShow("Altera��o realizada!", "Seu novo telefone foi alterado com sucesso!", AlertType.INFORMATION);

		}
	}

	private Event evento;

	public void setEvent(Event evento) {
		this.evento = evento;
	}

	private ComboBox<String> cbx;
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("telaNovoTelefone.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista P�blica - Cadastrar novo telefone");
			primaryStage.getIcons().add(icon);
			primaryStage.setResizable(false);
			//setar tela modal e tela que chamou 
			primaryStage.initModality(Modality.WINDOW_MODAL);
			primaryStage.initOwner(((Node)evento.getSource()).getScene().getWindow());
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
