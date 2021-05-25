package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.geonames.Toponym;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import API_IBGE.Municipio;
import API_IBGE.UF;
import classes.Banco;
import classes.Util;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CadastrarLocal extends Application implements Initializable {
	private ArrayList<Integer> idsEstado = new ArrayList<Integer>();
	
	private boolean campoCpfCnpj = false; // false=Cpf

	Stage primaryStage;
	
	@FXML
    private Label lblCnpj1;

    @FXML
    private TextField txtCaminhoImagem;

    @FXML
    private Button btnEscolherArquivo;
	
	@FXML
	private ComboBox<String> cmbTipo;

	@FXML
	private TextField txtEmail;

	@FXML
    private TextField txtCep;
	
	 @FXML
	private Label lblCnpj;
	
	@FXML
	private TextField txtTipo;

	@FXML
	private TextField txtRua;

	@FXML
    private TextArea txtDescricao;
	
	@FXML
	private TextField txtNumeroResidencia;

	@FXML
	private TextField txtBairro;

	@FXML
	private ComboBox<String> cmbEstados;

	@FXML
    private TextField txtNomeLocal;
	
	@FXML
	private ImageView imgFotoLocal;

	@FXML
	private ComboBox<String> cmbCidades;

	public static void main(String[] args) {
		launch(args);
	}
	
	void limparTela() {
		txtCaminhoImagem.clear();
		txtNomeLocal.clear();
		txtBairro.clear();
		txtNumeroResidencia.clear();
		txtRua.clear();
		txtCep.clear();
	}
	
	@FXML
    void cadastrarEndereco(ActionEvent event) {
		if(txtBairro.getText().isEmpty()) {
			Util.MessageBoxShow("Campo vazio", "Preencha o bairro corretamente", AlertType.ERROR);
			return;
		}else if(txtRua.getText().isEmpty()) {
			Util.MessageBoxShow("Campo vazio", "O campo de rua est� vazio.", AlertType.ERROR);
			return;
		}else if(txtNumeroResidencia.getText().isEmpty()) {
			Util.MessageBoxShow("Campo vazio", "O campo de n�mero da resid�ncia est� vazio.", AlertType.ERROR);
			return;
		}
		if(lblCnpj.getText().equalsIgnoreCase("cnpj")) {
			if(txtNomeLocal.getText().isEmpty()) {
				Util.MessageBoxShow("Campo vazio", "O campo do CNPJ est� vazio.", AlertType.ERROR);
				return;
			}
			String cnpjSemMascara=txtNomeLocal.getText().replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/", "");
			System.out.println(cnpjSemMascara);
			if(cnpjSemMascara.length() != 14) {
				Util.MessageBoxShow("Campo errado", "O campo do CNPJ est� inv�lido. Verifique e tente novamente.", AlertType.ERROR);
				return;
			}
		}else {
			if(txtNomeLocal.getText().isEmpty()) {
				Util.MessageBoxShow("Campo vazio", "O campo do nome est� vazio.", AlertType.ERROR);
				return;
			}
		}		
		String anexoImagem=txtCaminhoImagem.getText();
		String query=
				String.format("insert into endereco values (default,'%s','%s','%s','%s','%s','%s',1,'%s')",
						txtRua.getText(),txtNumeroResidencia.getText(),txtBairro.getText(),cmbEstados.getSelectionModel().getSelectedItem(),
						cmbCidades.getSelectionModel().getSelectedItem(),txtNomeLocal.getText(),
						anexoImagem.length()>0?Util.converterStringParaBase64(anexoImagem):"");
		System.out.println("Query de cadastro: "+query);
		try {
			if(Banco.InserirQuery(query)) {
				Util.MessageBoxShow("Cadastro realizado", "O seu novo endere�o foi cadastrado com sucesso.",AlertType.INFORMATION);
				limparTela();
				return;
			}else {
				Util.MessageBoxShow("Erro no cadastro", "Um erro ocorreu ao cadastrar seu endere�o. Verifique seus dados e tente novamente.",AlertType.INFORMATION);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void mudarLabelNome(ActionEvent event) {
    	CheckBox chk = (CheckBox)event.getSource();
    	if(chk.isSelected()) {
    		lblCnpj.setText("CNPJ");
    	}else {
    		lblCnpj.setText("Nome");
    	}
    }


    

	// API
    public ArrayList<UF> doGetEstados()
	{
		String strResposta = "";

		ObjectMapper mapper = new ObjectMapper();
		ArrayList<UF> estados = new ArrayList<UF>();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("https://servicodados.ibge.gov.br/api/v1/localidades/estados?OrderBy=nome");

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity resEnt = response.getEntity();
			strResposta = EntityUtils.toString(resEnt);
			JSONArray obj = new JSONArray(strResposta);

			UF estado;

			for(int i =0; i < obj.length(); i++)
			{
				System.out.println(obj.getJSONObject(i).toString());
				estado = mapper.readValue(obj.getJSONObject(i).toString(), UF.class);
				estados.add(estado);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return estados;
	}
    
    
    @FXML
    void escolherImagem(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Defina uma imagem do local");
    	fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
    	File imagemEscolhida=fileChooser.showOpenDialog(this.primaryStage);
    	txtCaminhoImagem.setText(imagemEscolhida.getAbsolutePath());
    	
    	
    }
    
    @FXML
	private void recuperarCidades ()
	{
		cmbCidades.getItems().clear();
		ArrayList<Municipio> municipios = doGetCidades();
		for (Municipio municipio : municipios)
		{
			cmbCidades.getItems().add(municipio.getNome());
		}
	}
    
    
	public ArrayList<Municipio> doGetCidades()
	{
		String strResposta = "";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<Municipio> municipios = new ArrayList<Municipio>();
		HttpClient httpClient = HttpClientBuilder.create().build();
		int ret=idsEstado.get(cmbEstados.getSelectionModel().getSelectedIndex());
		System.out.println("INDICE RETORNO: "+ret);
		HttpGet httpGet = new HttpGet(String.format("https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/municipios",
		idsEstado.get(cmbEstados.getSelectionModel().getSelectedIndex())));

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity resEnt = response.getEntity();
			strResposta = EntityUtils.toString(resEnt);
			JSONArray obj = new JSONArray(strResposta);

			Municipio municipio;

			for(int i =0; i < obj.length(); i++)
			{
				municipio = mapper.readValue(obj.getJSONObject(i).toString(), Municipio.class);
				municipios.add(municipio);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return municipios;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage=primaryStage;
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("telaCadastroLugar.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Image icon = new Image("Recursos/logo.png");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.setMaximized(false);
		primaryStage.setTitle("Lista P�blica - Cadastro de local");
		primaryStage.getIcons().add(icon);
		primaryStage.show();

	}

	void setFormatterCpnj() {
		txtCep.setTextFormatter(new TextFormatter<>(change ->
	    (change.getControlNewText().matches("([0-9])+")) ? change : null));
	}
	
	void setFormatterNumeroResidencia() {
		txtNumeroResidencia.setTextFormatter(new TextFormatter<>(change ->
	    (change.getControlNewText().matches("([0-9])+")) ? change : null));
	}
	
	@FXML
    void verificarTeclaDeletar(KeyEvent event) {
		System.out.println( txtCep.getText().length()-1);
		if(event.getCode().toString().equals("BACK_SPACE") && (txtCep.getText().length()-1) == 0) {
			txtCep.setTextFormatter(null);
			txtCep.clear();
			setFormatterCpnj();
		}
    }
	
	@FXML
    void verificarTeclaDeletarNumeroResidencia(KeyEvent event) {
		if(event.getCode().toString().equals("BACK_SPACE") && txtNumeroResidencia.getLength() == 0) {
			txtNumeroResidencia.setTextFormatter(null);
			txtNumeroResidencia.clear();
			setFormatterNumeroResidencia();
		}
    }
	
	private void verificaCep() {
		if(txtCep.getText().isEmpty()) {
			//Util.MessageBoxShow("Campo vazio", "O campo de cep est� vazio.",AlertType.ERROR);
			return;
		}
		String cepSemFormato=txtCep.getText().replaceAll("\\-", "").replace("\\.", "");
		System.out.println("Sem formato: "+cepSemFormato);
		if(cepSemFormato.length() == 8) {
			JSONObject ret = Util.obtemInfosApiCep(cepSemFormato);
			System.out.println(ret);
			txtBairro.setText(ret.getString("bairro"));
			txtRua.setText(ret.getString("logradouro"));
			cmbEstados.getSelectionModel().select(ret.getString("uf"));
			cmbCidades.getSelectionModel().select(ret.getString("localidade"));
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<UF> estados = doGetEstados();
		for (UF estado : estados)
		{
			idsEstado.add(estado.getId());
			cmbEstados.getItems().add(estado.getSigla());
		}
		System.out.println("Tamanho idEstados: "+idsEstado.size());
		
		
		txtCep.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue)
		        	verificaCep();
		    }

			
		});
		
		
		// VERIFICA��O DE ENTRADAS
		setFormatterCpnj();
		setFormatterNumeroResidencia();
		

	}

}
