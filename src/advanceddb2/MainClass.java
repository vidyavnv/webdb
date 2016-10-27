package advanceddb2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import advanceddb2.vo.Tree;

public class MainClass {
	
	public static float tEs;
	public static long tEc;
	public static String bingAccountKey;
	public static String website;
	
	public static Queries queries;
	
	public static Tree<String> root;

	/*
	 * Main Entry class for the program
	 * Accepts arguments in below order
	 * 1 - Bing Account Key
	 * 2 - Specificity Threshold
	 * 3 - Coverage Threshold
	 * 4 - Website Domain
	 */
	public static void main(String[] args) throws IOException {
		if(args != null && args.length >= 4) {
			
			// Assign arguments to corresponding variables
			bingAccountKey = args[0];
			tEs = Float.parseFloat(args[1]);
			tEc = Long.parseLong(args[2]);
			website = args[3];
			
			queries = new Queries();
			populateTree();
			
			//Part 1
			
			//Part 2
			List<String> classification = new ArrayList<String>();
			classification.add("root");
			classification.add("sports");
			classification.add("soccer");
			
			
			
			
			BingSearch search = new BingSearch();
			search.getResults(bingAccountKey, website, "premiership");
			
			
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
	
	public static void populateTree() {
		Tree<String> hardware = new Tree<String>("Hardware");
		Tree<String> programming = new Tree<String>("Programming");
		Tree<String> computers = new Tree<String>("Computers");
		hardware.addParent(computers.getNode());
		programming.addParent(computers.getNode());
		computers.addChildren(hardware.getNode());
		computers.addChildren(programming.getNode());
		
		Tree<String> fitness = new Tree<String>("Fitness");
		Tree<String> diseases = new Tree<String>("Diseases");
		Tree<String> health = new Tree<String>("Health");
		fitness.addParent(health.getNode());
		diseases.addParent(health.getNode());
		health.addChildren(fitness.getNode());
		health.addChildren(diseases.getNode());
		
		Tree<String> basketball = new Tree<String>("Basketball");
		Tree<String> soccer = new Tree<String>("Soccer");
		Tree<String> sports = new Tree<String>("Sports");
		basketball.addParent(sports.getNode());
		soccer.addParent(sports.getNode());
		sports.addChildren(basketball.getNode());
		sports.addChildren(soccer.getNode());
		
		root = new Tree<String>("Root");
		computers.addParent(root.getNode());
		health.addParent(root.getNode());
		sports.addParent(root.getNode());
		root.addChildren(computers.getNode());
		root.addChildren(health.getNode());
		root.addChildren(sports.getNode());
		
	}

}
