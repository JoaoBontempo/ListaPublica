package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import classes.Banco;
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
import javafx.scene.control.RadioButton;
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
	private TextField txtDescrição;

	@FXML
	private TextField txtDDD;

	@FXML
	private Label lbTelefone;
	@FXML
	private ComboBox<String> cboxEndereco;

	private ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
	@FXML
	private Button btnCadastrar;
	
    @FXML
    private RadioButton rbtnFixo0;

    @FXML
    private RadioButton rbtnCelular1;

    @FXML
    private RadioButton rbtnOutro2;

    @FXML
    private Label lbNumero;
    @FXML
    private Label lbDDD;
    
    int tipo = 0;
    
    String numeroC, tipoC;

	public CadastroTelefone() {
		// TODO Auto-generated constructor stub
	}

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

	private boolean validarCampos() throws SQLException
	{
		if (!Validacao.verificarTextField(txtNumero))
			return false;
		if (tipo != 2)
		{
			if (!Validacao.verificarTextField(txtDDD))
				return false;
			if (!Validacao.verificarNumerosTextField(txtDDD))
				return false;
			if (txtDDD.getText().length() != 2)
			{
				Util.MessageBoxShow("Campo inválido", "O campo 'DDD' deve ter exatamente 2 números", AlertType.ERROR);
				txtDDD.requestFocus();
				return false;
			}
			if (!Validacao.verificarNumerosTextField(txtNumero))
				return false;
		}
		switch (tipo)
		{
			//FIXO
			case 0:
				if (txtNumero.getText().length() != 8)
				{
					Util.MessageBoxShow("Campo inválido", "Um telefone fixo deve conter exatamente 8 números", AlertType.ERROR);
					txtNumero.requestFocus();
					return false;
				}
				break;
				
			//CELULAR	
			case 1:
				if (txtNumero.getText().length() != 9)
				{
					Util.MessageBoxShow("Campo inválido", "Um telefone celular deve conter exatamente 9 números", AlertType.ERROR);
					txtNumero.requestFocus();
					return false;
				}
				break;
		}
		
		if (!Validacao.verificarTextField(txtDescrição))
			return false;
		
		if (tipo == 2)
			return VerificarExistenciaTelefone(String.format("SELECT id FROM telefone WHERE telefone.numero = '%s'", txtNumero.getText()));
		else
			return VerificarExistenciaTelefone(String.format("SELECT id FROM telefone WHERE telefone.numero = '%s'", txtDDD.getText() + txtNumero.getText()));
	}
	
	private boolean VerificarExistenciaTelefone(String query) throws SQLException
	{
		ResultSet result = Banco.InserirQueryReader(query);
		if (result.next())
		{
			Util.MessageBoxShow("Cadastro inválido","Este telefone já está cadastrado", AlertType.ERROR);
			return false;
		}
		return true;
	}

	private void LimparCampos()
	{
		txtDDD.setText("");
		txtNumero.setText("");
		txtDescrição.setText("");
		cboxEndereco.getSelectionModel().selectFirst();
	}
	
	//TXTNUMERO COM DDD: 282, 34, LABEL POSITION: X: 105, Y: 235
	//TXT NUMERO SEM DDD: 369, 34, LABEL: X: 15
	@FXML
	public void TrocarRadioButton(Event e)
	{
		RadioButton selected = (RadioButton)e.getSource();
		int num = Integer.parseInt(String.valueOf(selected.getId().charAt(selected.getId().length()-1)));
		Util.ChangeRadioButtons(new RadioButton[] {rbtnFixo0, rbtnCelular1, rbtnOutro2}, num);
		tipo = num;
		if (num == 2)
		{
			txtDDD.setText("");
			lbDDD.setVisible(false);
			txtDDD.setVisible(false);
			lbNumero.setLayoutX(15);
			txtNumero.setLayoutX(15);
			txtNumero.setPrefWidth(369);;
		}
		else
		{
			lbDDD.setVisible(true);
			txtDDD.setVisible(true);
			lbNumero.setLayoutX(105);
			txtNumero.setLayoutX(105);
			txtNumero.setPrefWidth(282);;
		}
	}
	
	private void FormatarCamposCadastro()
	{
		switch (tipo)
		{
			case 0:
				tipoC = "fixo";
				numeroC = txtDDD.getText() + txtNumero.getText();
				break;
				
			case 1:
				tipoC = "celular";
				numeroC = txtDDD.getText() + txtNumero.getText();
				break;
				
			case 2:
				tipoC = "outro";
				numeroC = txtNumero.getText();
				break;
		}
	}

	@FXML
	public void cadastrarTelefone() throws SQLException, IOException
	{
		if (validarCampos())
		{
			FormatarCamposCadastro();
			if (cboxEndereco.getSelectionModel().getSelectedIndex() != 0)
			{
				Banco.InserirQuery(String.format("INSERT INTO telefone (id, numero, dono, lugar, descricao, tipo) VALUES"
						+ " (default, '%s', %s, %s, '%s', '%s')", numeroC, Util.getContaLogada().getId(), 
						enderecos.get(cboxEndereco.getSelectionModel().getSelectedIndex() - 1).getId(), txtDescrição.getText(), tipoC));
			}
			else
			{
				Banco.InserirQuery(String.format("INSERT INTO telefone (id, numero, dono, descricao, tipo) VALUES"
						+ " (default, '%s', %s, '%s', '%s')", numeroC, Util.getContaLogada().getId(), txtDescrição.getText(), tipoC));
			}
			Util.MessageBoxShow("Cadastro realizado!", "Seu novo telefone foi cadastrado com sucesso!", AlertType.INFORMATION);
			LimparCampos();
			Util.dashboard.AtualizarCbxTelefones();
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
			primaryStage.setTitle("Lista Pública - Cadastrar novo telefone");
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
