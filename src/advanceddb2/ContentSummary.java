package advanceddb2;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
import advanceddb2.vo.AppDocument;
import advanceddb2.vo.Tree.Node;
 
/*
 * Class to Create Content Summary
 */
public class ContentSummary {
      
       private final List<Node<String>> content;
      
       public ContentSummary(List<Node<String>> content) {
             this.content = content;
       }
      
       /*
        * Part 2A. For each Node, accumulates all queries and get top 4 documents for each query
        */
       public Map<Node<String>, List<String>> getDocumentSamples() throws IOException, InterruptedException {
            
             Map<Node<String>, List<String>> map = new HashMap<Node<String>, List<String>>();
            
             BingSearch search = new BingSearch();
             
             System.out.println("\n\nExtracting topic content summaries...");
            
             // Iterate through the nodes classified
             for(Node<String> node: content) {
                    Set<AppDocument> docs = new HashSet<AppDocument>();
                    List<String> words = new ArrayList<String>();
                    List<String> allQueries = getQueries(node.getName());
                    
                    // Accumulate all queries of the node in a List
                    if(node.getChildren() != null && node.getChildren().size() >0) {
                    	for(Node<String> child: node.getChildren()) {
                    		if(content.contains(child)) {
                    			allQueries.addAll(getQueries(child.getName()));
                    		}
                    	}
                    }
                   
                    System.out.println("Creating Content Summary for: " + node.getName());
                    
                    // Iterate through all queries
                    for(int i=0;i<allQueries.size();i++) {
                    	String query= allQueries.get(i);
                    	System.out.println("" + (i+1)+ "/" + allQueries.size());
                    	
                    	// Remove Initial word before query
                    	List<String> completeQuery = MainClass.keyWordsToList(query);
                    	List<String> trimQuery = completeQuery.subList(1, completeQuery.size());
                    	query = MainClass.listToKeyWords(trimQuery);
                    	
                    	System.out.println("Query is - " + query);
                    	// Get Top4 results for each query
                    	List<AppDocument> queryDocs = search.getTop4Results(MainClass.bingAccountKey, MainClass.website, query);
                      
                    	// Fetch document from each url, split them to words.
                    	for(AppDocument d: queryDocs) {
	                         if(!docs.contains(d)) {
	                                docs.add(d);
	                                Thread.sleep(500);
	                                System.out.println("\nGetting page: "+ d.getUrl());
	                                Set<String> subWords = GetWordsLynx.runLynx(d.getUrl());
	                                words.addAll(subWords);
	                         } else {
	                        //	 System.out.println("Not querying: " + d.getUrl());
	                         }
                    	}
                          
                    }
                   
                    map.put(node, words);
             }
            
             return map;
            
       }
      
       /*
        * Part 2B. Write Content Summary to Document
        */
       public void outputDoc(String fileName, List<String> words) throws Exception {
            
    	   // Sort the words, so that it can be iterated and words can be counted in 1 iteration.
            Collections.sort(words);
            
             File file = new File(fileName);
 
             // if file doesnt exists, then create it
             if (!file.exists()) {
                    file.createNewFile();
             }
 
             FileWriter fw = new FileWriter(file.getAbsoluteFile());
             BufferedWriter bw = new BufferedWriter(fw);
            
             String currWord = null;
             // After sorting, iterate through each word and find corresponding count.
             for(int i=0;i<words.size();i++) {
                    if(currWord == null) {
                           currWord = words.get(i);
                    } else {
                           if(!currWord.equals(words.get(i))) {
                        	   // Output word to document
                        	   printWord(words, currWord, bw);
                               currWord = words.get(i);
                           }
                    }
             }
            
             bw.close();
            
       }
      
       /*
        * Utility method to write count to document
        */
       private void printWord(List<String> words, String currWord, BufferedWriter bw) throws IOException {
             int occurrences = Collections.frequency(words, currWord);
      //       System.out.println("Occurance -" + occurrences);
             String docFrequency = currWord + "#" + occurrences + "\n";
       //      System.out.println(docFrequency);
             bw.write(docFrequency);
       }
      
       /*
        * Utility method to queries corresponding to node
        */
       private List<String> getQueries(String name) {
    	   	if(name.equals("Root")) {
    	   		return MainClass.queries.getRootQueries();
    	   	} else if(name.equals("Computers")) {
    	   		return MainClass.queries.getComputersQueries();
    	   	} else if(name.equals("Health")) {
    	   		return MainClass.queries.getHealthQueries();
    	   	} else if(name.equals("Sports")) {
    	   		return MainClass.queries.getSportsQueries();
    	   	} else {
    	   		return null;
    	   	}
       }
      
}