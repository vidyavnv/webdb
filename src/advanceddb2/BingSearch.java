package advanceddb2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import advanceddb2.vo.AppDocument;

/*
 * Utility class to query Bing API, store results in AppDocument object
 */
public class BingSearch {

	public static String BING_URL = "https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Composite?Query=%27site%3a";
	public static String BING_URL_2 = "https://api.datamarket.azure.com/Data.ashx/Bing/SearchWeb/v1/Web?Query=%27site%3a";
	public static String EXTRA_PARAMS = "%27&$top=10&$format=JSON";
	
	/*
	 * Method to fetch Total number of results for each query. Used in Part 1 of application
	 */
	public String getResults(String accountKey, String site, String searchQuery) throws IOException {
		
		searchQuery = searchQuery.replaceAll(" ", "%20");
		String webCount = "";
		String query=BING_URL + site + "%20" + searchQuery + EXTRA_PARAMS;
		
		byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);

	//	System.out.println("Bing web query: " + query);
		URL url = new URL(query);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
		final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));		
		try 
		{
            String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            final JSONObject json = new JSONObject(response.toString());
            final JSONObject d = json.getJSONObject("d");
            final JSONObject resultObj = d.getJSONArray("results").getJSONObject(0);
            webCount  = (String) resultObj.get("WebTotal");
        }
		catch(Exception ex) {
			// Catch exception
			System.out.println("Sorry. An unexpected error occurred while sending request to Bing");
			ex.printStackTrace();
		}
		
		return webCount;
	}
	
	/*
	 * Method to fetch Top4 results for each query. Used in Part 2 of application
	 */
	public List<AppDocument> getTop4Results(String accountKey, String site, String searchQuery) throws IOException {
		
		searchQuery = searchQuery.replaceAll(" ", "%20");
		
		List<AppDocument> docs = new ArrayList<AppDocument>();
		String query=BING_URL_2 + site + "%20" + searchQuery + EXTRA_PARAMS;
		
		byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);

//		System.out.println("Bing web query: " + query);
		URL url = new URL(query);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
		final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));		
		try 
		{
            String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            final JSONObject json = new JSONObject(response.toString());
            final JSONObject d = json.getJSONObject("d");
            final JSONArray results = d.getJSONArray("results");
            final int resultsLength = results.length();
            final int iterLength = Math.min(4, resultsLength);
            for (int i = 0; i < iterLength; i++) {
                final JSONObject aResult = results.getJSONObject(i);
                AppDocument doc = new AppDocument();
                doc.setUrl((String) aResult.get("Url"));
                docs.add(doc);
            }
        }
		catch(Exception ex) {
			// Catch exception
			System.out.println("Sorry. An unexpected error occurred while sending request to Bing.");
			ex.printStackTrace();
		}
		
		return docs;
	}
	
}
