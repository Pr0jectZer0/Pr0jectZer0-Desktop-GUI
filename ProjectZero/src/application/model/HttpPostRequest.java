package application.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Hilfsklasse für HTTP-Request
 * @author Dorsch, Paul, Deutsch, Penner, Kramer
 */
public class HttpPostRequest {
	private static final String USER_AGENT = "Mozilla/5.0", url = "https://pr0jectzer0.ml/api/";

	/**
	 * Diese Methode sendet einen HTTP-Request für Registrierung, Anmeldung etc.
	 * @param urlPath
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public static String send(String urlPath, String[][] parameter) throws Exception {
		URL obj = new URL(url + urlPath);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = parameter[0][0] + "=" + parameter[0][1];
		for (int i = 1; i < parameter.length; i++) {
			urlParameters += "&" + parameter[i][0] + "=" + parameter[i][1];
		}
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		// int responseCode = con.getResponseCode();
		// System.out.println("\nSending 'POST' request to URL : " + urlPath);
		// System.out.println("Post parameters : " + urlParameters);
		// System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
}
