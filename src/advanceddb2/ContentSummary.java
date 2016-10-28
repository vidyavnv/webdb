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
 
public class ContentSummary {
      
       private final List<Node<String>> content;
      
       public ContentSummary(List<Node<String>> content) {
             this.content = content;
       }
      
       public Map<Node<String>, List<String>> getDocumentSamples() throws IOException, InterruptedException {
            
             Map<Node<String>, List<String>> map = new HashMap<Node<String>, List<String>>();
            
             BingSearch search = new BingSearch();
            
             for(Node<String> node: content) {
                    Set<AppDocument> docs = new HashSet<AppDocument>();
                    List<String> words = new ArrayList<String>();
                    List<String> allQueries = getQueries(node.getName());
                    
                    if(node.getChildren() != null && node.getChildren().size() >0) {
                    	for(Node<String> child: node.getChildren()) {
                    		if(content.contains(child)) {
                    			allQueries.addAll(getQueries(child.getName()));
                    		}
                    	}
                    }
                   
                    for(String query:allQueries) {
                    		System.out.println("Query is - " + query);
                           List<AppDocument> queryDocs = search.getTop4Results(MainClass.bingAccountKey, MainClass.website, query);
                          
                           for(AppDocument d: queryDocs) {
                                 if(!docs.contains(d)) {
                                        docs.add(d);
                                        Thread.sleep(500);
                                        Set<String> subWords = GetWordsLynx.runLynx(d.getUrl());
                                        words.addAll(subWords);
                                 }
                           }
                          
                    }
                   
                    map.put(node, words);
             }
            
             return map;
            
       }
      
       public void outputDoc(String fileName, List<String> words) throws Exception {
            
            Collections.sort(words);
            
             File file = new File(fileName);
 
             // if file doesnt exists, then create it
             if (!file.exists()) {
                    file.createNewFile();
             }
 
             FileWriter fw = new FileWriter(file.getAbsoluteFile());
             BufferedWriter bw = new BufferedWriter(fw);
            
             String currWord = null;
             for(int i=0;i<words.size();i++) {
                    if(currWord == null) {
                           currWord = words.get(i);
                    } else {
                           if(!currWord.equals(words.get(i))) {
                        	   printWord(words, currWord, bw);
                               currWord = words.get(i);
                           }
                    }
             }
            
             bw.close();
            
       }
      
       private void printWord(List<String> words, String currWord, BufferedWriter bw) throws IOException {
             int occurrences = Collections.frequency(words, currWord);
      //       System.out.println("Occurance -" + occurrences);
             String docFrequency = currWord + "#" + occurrences + "\n";
       //      System.out.println(docFrequency);
             bw.write(docFrequency);
       }
      
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