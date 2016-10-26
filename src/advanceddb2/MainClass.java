package advanceddb2;

import java.util.Arrays;
import java.util.List;

public class MainClass {
	
	public static float tEs;
	public static long tEc;
	public static String bingAccountKey;
	public static String website;
	
	public static Queries queries;

	/*
	 * Main Entry class for the program
	 * Accepts arguments in below order
	 * 1 - Bing Account Key
	 * 2 - Specificity Threshold
	 * 3 - Coverage Threshold
	 * 4 - Website Domain
	 */
	public static void main(String[] args) {
		if(args != null && args.length >= 4) {
			
			// Assign arguments to corresponding variables
			bingAccountKey = args[0];
			tEs = Float.parseFloat(args[1]);
			tEc = Long.parseLong(args[2]);
			website = args[3];
			
			queries = new Queries();
			
			
		} else {
			System.out.println("Invalid number of arguments");
			System.exit(1);
		}
	}
	
	/*
	 * Utility method to convert Key Words String to Array List in order.
	 */
	private static List<String> keyWordsToList(String keyWords) {
		String[] keys = keyWords.split(" ");
		List<String> wordList = Arrays.asList(keys);  
		return wordList;
	}
	
	/*
	 * Utility method to convert List of key words back to String.
	 */
	public static String listToKeyWords(List<String> keyWords) {
		String keys = "";
		for(String key: keyWords) {
			keys = keys + key.toLowerCase() + " ";
		}
		return keys.trim();
	}

}
