package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import classes.API;
import classes.Endereco;
import classes.Telefone;
import classes.UtilDashboard;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TelaLocal extends Application {

	// essa variável recebe da dashboard, quando ocorre um double click em um registro do datagrid, para então fazer o processo dos dados
	private String idBuscarInfos=null;
	
	public void setIdBuscarInfos(String id) {this.idBuscarInfos=id;}
	
	@FXML
    private TextField txtNumeroResidencia;

    @FXML
    private TextField txtBairro;

    @FXML
    private TextField txtRua;

    @FXML
    private TextField txtCidade;

    @FXML
    private TextField txtEstado;

    @FXML
    private Label lblCnpj1;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private ListView<String> lvTelefones;

    @FXML
    void obterTelefoneClicado(MouseEvent event) {
    	System.out.println(lvTelefones.getSelectionModel().getSelectedItem());
    }
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("telaLugar.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("Recursos/logo.png");

			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista Pública - Informações detalhadas do local");

			primaryStage.getIcons().add(icon);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() throws Exception
	{
		// obtenho os valores do arraylist da tela anterior e jogo no ListView
		lvTelefones.getItems().addAll(UtilDashboard.getTelefones());
		
		// inicia a api
		iniciaApi();
	}

	void iniciaApi() {
		String result;

		ObjectMapper mapper = new ObjectMapper();
		String url="http://localhost:5000/ListaPublica/getUserAddress/"+UtilDashboard.getIdDono();
		System.out.println("URL : "+url);
		HttpGet get = new HttpGet(url);
		
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response;
			response = httpClient.execute(get);

			result = EntityUtils.toString(response.getEntity());
			JSONArray obj = new JSONArray(result);
			Endereco endereco;
			ArrayList<Endereco> enderecos = new ArrayList<>();
			for(int i = 0; i < obj.length();i++)
			{
				endereco = mapper.readValue(obj.getJSONObject(i).toString()  , Endereco.class);
				enderecos.add(endereco);
			}
			
			
			// JOGA OS VALORES NOS CAMPOS
			txtEstado.setText(enderecos.get(0).getEstado());
			// txtNomeLocal.setText(enderecos.get(0).getCidade()); <<< NÃO É NECESSARIO
			txtBairro.setText(enderecos.get(0).getBairro());
			txtRua.setText(enderecos.get(0).getRua()); 
			txtCidade.setText(enderecos.get(0).getCidade());
			//txtTelefone.setText(Integer.toString(enderecos.get(0))); << jogar no listview
			
			txtNumeroResidencia.setText(Integer.toString(enderecos.get(0).getNumero()));

			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
