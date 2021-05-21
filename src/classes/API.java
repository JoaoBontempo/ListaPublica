package classes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import API_IBGE.Municipio;
import API_IBGE.UF;

public final class API {
	
	public static ArrayList<Telefone> doPostTelefone(TableViewUtil queryFilter)
	{
		String result;
		ArrayList<Telefone> telefones = new ArrayList<Telefone>();

		ObjectMapper mapper = new ObjectMapper();

		HttpPost post = new HttpPost("http://localhost:5000/ListaPublica/getFiltro");
		post.addHeader("content-type", "application/json");

		JSONObject js = new JSONObject();
		js.put("nome", queryFilter.getNome());
		js.put("numero", queryFilter.getNumero());
		js.put("cidade", queryFilter.getCidade());
		js.put("estado", queryFilter.getEstado());
		js.put("email", queryFilter.getEmail());

		try {
			post.setEntity(new StringEntity(js.toString()));
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response;
			response = httpClient.execute(post);

			result = EntityUtils.toString(response.getEntity());
			JSONArray obj = new JSONArray(result);
			
			Telefone telefone;
			for(int i = 0; i < obj.length();i++)
			{
				telefone = mapper.readValue(obj.getJSONObject(i).toString()  , Telefone.class);
				telefones.add(telefone);
			}
			

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return telefones;
	}
	
	public static ArrayList<Municipio> doGetCidades(int idEstado)
	{
		String strResposta = "";

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<Municipio> municipios = new ArrayList<Municipio>();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(String.format("https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/municipios",
				idEstado));

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

	
	public static ArrayList<Telefone> doGetTelefones(int limite)
	{
		String strResposta = "";

		ObjectMapper mapper = new ObjectMapper();
		//mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<Telefone> telefones = new ArrayList<Telefone>();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("http://localhost:5000/ListaPublica/getLast/" + limite);
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			HttpEntity resEnt = response.getEntity();
			strResposta = EntityUtils.toString(resEnt);
			JSONArray obj = new JSONArray(strResposta);

			Telefone telefone;

			for(int i =0; i < obj.length(); i++)
			{
				telefone = mapper.readValue(obj.getJSONObject(i).toString(), Telefone.class);
				telefones.add(telefone);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return telefones;
	}
	
	public static ArrayList<UF> doGetEstados()
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


}
