package classes;

public final class Validacao {
	public boolean isNullOrEmpty(String string)
	{
		if (string == null)
			return true;
		if (string.isEmpty())
			return true;
		return false;
	}
}
