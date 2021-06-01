package classes;

public final class CadastroTelUtil {

	private static String lbTelefone;
	private static String btnTxt;
	private static boolean caso = false;

	public static boolean isCaso() {
		return caso;
	}
	public static void setCaso(boolean caso) {
		CadastroTelUtil.caso = caso;
	}
	public static String getLbTelefone() {
		return  "Alterar Telefone";
	}
	public static void setLbTelefone(String lbTelefone) {
		CadastroTelUtil.lbTelefone = lbTelefone;
	}
	public static String getBtnTxt() {
		return "Alterar" ;
	}
	public static void setBtnTxt(String btnTxt) {
		CadastroTelUtil.btnTxt = btnTxt;
	}
	
	public static Telefone telefone;

	public static Telefone getTelefone() {
		return telefone;
	}
	public static void setTelefone(Telefone telefone) {
		CadastroTelUtil.telefone = telefone;
	}


}
