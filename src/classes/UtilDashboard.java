package classes;

import java.util.ArrayList;
import java.util.List;

public class UtilDashboard {
	private static int idBuscarInfos=-1;
	private static TableViewUtil tbVutil;
	private static String idDono;
	private static String idLugar;
	private static int idTelefone;
	private static ArrayList<TelefoneNumero> telefones=new ArrayList<>();
	private static String numeroTelefone;
	
	
	public static int getIdTelefone() {
		return idTelefone;
	}

	public static void setIdTelefone(int idTelefone) {
		UtilDashboard.idTelefone = idTelefone;
	}
	
	public static String getNumeroTelefone() {
		return numeroTelefone;
	}

	public static void setNumeroTelefone(String numeroTelefone) {
		UtilDashboard.numeroTelefone = numeroTelefone;
	}


	public static ArrayList<TelefoneNumero> getTelefones() {
		return telefones;
	}

	public static void setTelefones(ArrayList<TelefoneNumero> telefones) {
		UtilDashboard.telefones = telefones;
	}

	public static String getIdDono() {
		return idDono;
	}

	public static void setIdDono(String idDono2) {
		idDono = idDono2;
	}

	public static String getIdLugar() {
		return idLugar;
	}

	public static void setIdLugar(String idLugar2) {
		idLugar = idLugar2;
	}

	public static TableViewUtil getTbVutil() {
		return tbVutil;
	}

	public static void setTbVutil(TableViewUtil tbVutil) {
		UtilDashboard.tbVutil = tbVutil;
	}
	

	public static void setIdBuscarInfos(int id) {
		idBuscarInfos=id;
	}
	
	public static int getIdBuscarInfos() {return idBuscarInfos;}

	
	
	
	
}
