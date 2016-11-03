package advanceddb2;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import advanceddb2.vo.Tree;
import advanceddb2.vo.Tree.Node;
 
public class MainClass {
              
   public static float tEs;
   public static long tEc;
   public static String bingAccountKey;
   public static String website;
  
   public static Queries queries;
  
   public static Tree<Node<String>> root;
  
   public static Node<String> rootNode;
   public static Node<String> computers;
   public static Node<String> health;
   public static Node<String> sports;
   
   public static int counter = 0;
 
   /*
   * Main Entry class for the program
   * Accepts arguments in below order
   * 1 - Bing Account Key
   * 2 - Specificity Threshold
   * 3 - Coverage Threshold
   * 4 - Website Domain
   */
   public static void main(String[] args) throws Exception {
	   // If all arguments are not passed, exit the program gracefully
      if(args != null && args.length >= 4) {
                    
             // Assign arguments to corresponding variables
             bingAccountKey = args[0];
             tEs = Float.parseFloat(args[1]);
             tEc = Long.parseLong(args[2]);
             website = args[3];
             
             // t_es should be between 0 and 1. Else exit
             if(tEs > 1.0 || tEs < 0) {
            	 System.out.println("Invalide value for threshold t_es");
            	 System.exit(0);
             }
            
             queries = new Queries();
             populateTree();
            
             Map<String, Integer> cache = new HashMap<String, Integer>();
             
             //Part 1 - Database Classification
             DatabaseClassifier dbClassifier = new DatabaseClassifier();
             String path = "";
             double specParent = 1.0;
             Set<String> pathSet = new HashSet<String>();
             // To Store final classification
     		 List<Node<String>> classification = new ArrayList<Node<String>>();
             dbClassifier.qProber(website, tEc, tEs, rootNode, bingAccountKey, path, cache, pathSet, classification, specParent);
             
             // Print Classification
             System.out.println("\n\nClassification:");
             for (String s : pathSet) {
            	    System.out.println(s);
            	}
             
             //Part 2 - Distributed Search Over Web Databases
             ContentSummary summary = new ContentSummary(classification);
             Map<Node<String>, List<String>> map = summary.getDocumentSamples();
             
             // For each node create corresponding document and store content summary
             for(Node<String> node: map.keySet()) {
                    String fileName = node.getName() + "-" + website + ".txt";
                    summary.outputDoc(fileName, map.get(node));
             }
                    
                    
      } else {
             System.out.println("Invalid number of arguments");
             System.exit(1);
      }
   }
  
   /*
   * Utility method to convert Key Words String to Array List in order.
   */
   public static List<String> keyWordsToList(String keyWords) {
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
  
   /*
    * Method to create tree of categories used in the project
    */
   public static void populateTree() {
          Node<String> hardware = new Node<String>("Hardware");
          Node<String> programming = new Node<String>("Programming");
          computers = new Node<String>("Computers");
          hardware.addParent(computers);
          programming.addParent(computers);
          computers.addChildren(hardware);
          computers.addChildren(programming);
         
          Node<String> fitness = new Node<String>("Fitness");
          Node<String> diseases = new Node<String>("Diseases");
          health = new Node<String>("Health");
          fitness.addParent(health);
          diseases.addParent(health);
          health.addChildren(fitness);
          health.addChildren(diseases);
         
          Node<String> basketball = new Node<String>("Basketball");
          Node<String> soccer = new Node<String>("Soccer");
          sports = new Node<String>("Sports");
          basketball.addParent(sports);
          soccer.addParent(sports);
          sports.addChildren(basketball);
          sports.addChildren(soccer);
         
          rootNode = new Node<String>("Root");
          computers.addParent(rootNode);
          health.addParent(rootNode);
          sports.addParent(rootNode);
          rootNode.addChildren(computers);
          rootNode.addChildren(health);
          rootNode.addChildren(sports);
         
          root = new Tree<Node<String>>(rootNode);
                 
   }
 
}