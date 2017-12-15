package application.api;

public class User {
	public static LoginState login(String EMail, String Passwort) {
		String token = "";
		return LoginState.Success;
	}
	
	public static RegisterState register(String EMail, String Passwort) {
		return RegisterState.Success;
	}
	
	public enum RegisterState {
		Success,
		UsernameNotAvailable,
		EmailAlreadyUsed,
		ServerError,
	}
	
	public enum LoginState {
		Success,
		WrongPassword,
		WrongEmail,
		ServerError,
	}
	
	public static boolean delete(int UserID) {
		return true;
	}
}