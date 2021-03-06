package application.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Hilfsklasse f�r HTTP-Request
 * 
 * @author Dorsch, Deutsch, Penner, Kramer
 */
public class HttpWebRequest {
	private static final String USER_AGENT = "Mozilla/5.0", url = "https://pr0jectzer0.ml/api/";

	/**
	 * Diese Methode sendet einen HTTP-Request f�r Registrierung, Anmeldung etc.
	 * 
	 * @param urlPath
	 * @param parameter
	 * @return
	 */
	public static String sendPostRequest(String urlPath, String[][] parameter) throws IOException{
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
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
	
	public static String sendPostRequest(String urlPath) throws IOException{
		URL obj = new URL(url + urlPath);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "";
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}

	public static String sendGetRequest(String urlPath) throws IOException {
		StringBuilder result = new StringBuilder();
		URL urlLink = new URL(url + urlPath);
		HttpURLConnection conn = (HttpURLConnection) urlLink.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}

	public static String sendDeleteRequest(String urlPath) throws IOException {
		URL urlLink = new URL(url + urlPath);
		HttpURLConnection httpCon = (HttpURLConnection) urlLink.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpCon.setRequestMethod("DELETE");
		httpCon.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		httpCon.disconnect();
		return response.toString();
	}
	
	public static String sendPutRequest(String urlPath, String[][] parameter) throws IOException {
		String urlParameters = parameter[0][0] + "=" + parameter[0][1].replace("\n", "%0A");
		for (int i = 1; i < parameter.length; i++) {
			urlParameters += "&" + parameter[i][0] + "=" + parameter[i][1].replaceAll("\n", "%0A");
		}
		URL urlLink = new URL(url + urlPath + "&" + urlParameters);
		HttpURLConnection httpCon = (HttpURLConnection)urlLink.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		httpCon.connect();
		OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		out.write(urlParameters);
		out.flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		out.close();
		httpCon.disconnect();
		return response.toString();
	}
}
