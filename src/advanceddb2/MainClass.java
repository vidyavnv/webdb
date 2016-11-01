package advanceddb2;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
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
 
   /*
   * Main Entry class for the program
   * Accepts arguments in below order
   * 1 - Bing Account Key
   * 2 - Specificity Threshold
   * 3 - Coverage Threshold
   * 4 - Website Domain
   */
   public static void main(String[] args) throws Exception {
      if(args != null && args.length >= 4) {
                    
             // Assign arguments to corresponding variables
             bingAccountKey = args[0];
             tEs = Float.parseFloat(args[1]);
             tEc = Long.parseLong(args[2]);
             website = args[3];
            
             queries = new Queries();
             populateTree();
            
             Map<String, Integer> cache = new HashMap<String, Integer>();
             
             //Part 1
             DatabaseClassifier dbClassifier = new DatabaseClassifier();
             String path = "";
             String category = dbClassifier.qProber(website, tEc, tEs, rootNode, bingAccountKey, path, cache);
             System.out.println(category);
            
             //Part 2
             List<Node<String>> classification = new ArrayList<Node<String>>();
             classification.add(rootNode);
             classification.add(sports);
            
             ContentSummary summary = new ContentSummary(classification);
             Map<Node<String>, List<String>> map = summary.getDocumentSamples();
             
      /*       Map<Node<String>, List<String>> map = new HashMap<Tree.Node<String>, List<String>>();
             
             List<String> words = new ArrayList<String>();
             words.add("aaaa");
             words.add("a");
             words.add("bla");
             words.add("a");
             words.add("a");
             words.add("a");
             words.add("bla");
             words.add("a");
             words.add("a");
             words.add("bla");
             
             map.put(rootNode, words);*/
            
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