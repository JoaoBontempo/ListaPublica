package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Banco {
	
	//Credenciais do banco
	private static String ip = "";
	private static String banco = "";
	private static String usuario = "";
	private static String senha = "";
	private static String porta = "";
	
	//Biblioteca
	private final static String DRIVE = "com.mysql.cj.jdbc.Driver";
	
	//Variáveis do banco
	private static Connection conexao = null;
	private static Statement statement = null;
	private static ResultSet resultados = null;
	
	public static ResultSet getReader()
	{
		return resultados;
	}
	
	//String de conexao
	private static String stringConexao = String.format("jdbc:mysql://%s:%s/%s", ip, porta, banco);
	
	
	public static void Conectar() throws ClassNotFoundException, SQLException
	{
		try
		{
			Class.forName(DRIVE);
			conexao = DriverManager.getConnection(stringConexao, usuario, senha);
			statement = conexao.createStatement();
		}
		catch (Exception erro)
		{
			Util.MessageBoxShow("Erro ao conectar ao banco", erro.getMessage(), AlertType.INFORMATION);
		}
	}
	
	public static ResultSet InserirQueryReader(String query) throws SQLException
	{
		resultados = statement.executeQuery(query);
		statement = conexao.createStatement();
		return resultados;
	}
	
	public static void InserirQuery(String query) throws SQLException
	{
		statement.executeUpdate(query);
	}
	
	public static void Desconectar() throws SQLException
	{
		conexao.close();
	}
	
	
}
