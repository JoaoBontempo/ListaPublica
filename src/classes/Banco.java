package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class Banco {

	// Credenciais do banco
	//private static String ip = "dblistapublica.ccenmvdsqpiw.us-east-2.rds.amazonaws.com";
	private static String ip = "127.0.0.1";
	private static String banco = "db_lista_publica";

	//private static String usuario = "gerenciamento";
	//private static String senha = "S3nh4F0rt3";
	private static String usuario = "root";
	private static String senha = "P@ssw0rd";

	private static String porta = "3306";

	// Biblioteca
	private final static String DRIVE = "com.mysql.cj.jdbc.Driver";

	// Variáveis do banco
	private static Connection conexao = null;
	private static Statement statement = null;
	private static ResultSet resultados = null;

	public static ResultSet getReader() {
		return resultados;
	}

	// String de conexao
	private static String stringConexao = String.format("jdbc:mysql://%s:%s/%s", ip, porta, banco);

	public static void Conectar() throws ClassNotFoundException, SQLException {
		try {
			Class.forName(DRIVE);
			conexao = DriverManager.getConnection(stringConexao, usuario, senha);
			statement = conexao.createStatement();
		} catch (Exception erro) {
			Util.MessageBoxShow("Erro ao conectar ao banco", erro.getMessage(), AlertType.INFORMATION);
		}
	}
	
	public static ResultSet InserirQueryReader(String query) throws SQLException {
		resultados = statement.executeQuery(query);
		statement = conexao.createStatement();
		return resultados;
	}

	public static boolean InserirQuery(String query) throws SQLException {
		try {
			if(statement == null) {
				Conectar();
			}
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
