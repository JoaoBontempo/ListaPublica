package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class Banco {

	// Credenciais do banco
	//private static String ip = "0.tcp.ngrok.io";
	private static String ip = "127.0.0.1";
	private static String banco = "db_lista_publica";

	//private static String usuario = "gerenciamento";
	//private static String senha = "S3nh4F0rt3";
	private static String usuario = "root";
	private static String senha = "root";

	private static String porta = "3306";
//	private static String porta = "15770";

	// Biblioteca
	private final static String DRIVE = "com.mysql.cj.jdbc.Driver";

	// Vari�veis do banco
	private static Connection conexao = null;
	private static Statement statement = null;
	private static ResultSet resultados = null;

	public static ResultSet getReader() {
		return resultados;
	}

	// String de conexao
	private static String stringConexao = String.format("jdbc:mysql://%s:%s/%s", ip, porta, banco);

	static {
		try {
			Class.forName(DRIVE);
			conexao = DriverManager.getConnection(stringConexao, usuario, senha);
			statement = conexao.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public static void Conectar() throws ClassNotFoundException, SQLException {
//		try {
//			if(conexao == null) {
//				Class.forName(DRIVE);
//				conexao = DriverManager.getConnection(stringConexao, usuario, senha);
//				statement = conexao.createStatement();
//			}
//			
//		} catch (Exception erro) {
//			Util.MessageBoxShow("Erro ao conectar ao banco", erro.getMessage(), AlertType.INFORMATION);
//		}
//	}
	
	public static ResultSet InserirQueryReader(String query) throws SQLException {

		resultados = statement.executeQuery(query);
		statement = conexao.createStatement();
		return resultados;
	}

	public static boolean InserirQuery(String query) throws SQLException {
		try {
			statement.executeUpdate(query);
			return true;
			
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	public static void Desconectar() throws SQLException {
		conexao.close();
	}

}
