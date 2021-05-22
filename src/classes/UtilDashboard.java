package classes;

import java.util.ArrayList;
import java.util.List;

public class UtilDashboard {
	private static int idBuscarInfos=-1;
	private static TableViewUtil tbVutil;
	private static String idDono;
	private static String idLugar;
	private static ArrayList<String> telefones=new ArrayList<>();

	public static ArrayList<String> getTelefones() {
		return telefones;
	}

	public static void setTelefones(ArrayList<String> telefones) {
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

	public static void setIdLugar(String idLugar) {
		idLugar = idLugar;
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
