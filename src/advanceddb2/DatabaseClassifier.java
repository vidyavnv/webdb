package advanceddb2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import advanceddb2.vo.Tree.Node;

/*
 * This Class Classifies database 
 */
public class DatabaseClassifier { 
	
	public void qProber(String url, long tEc, float tEs, Node<String> node, String accountKey, String path, Map<String, Integer> cache, Set<String> pathSet, List<Node<String>> classification) throws IOException{
				
		BingSearch search = new BingSearch();
		int hits;
		List<Node<String>> categories;
		// Get queries for the current node
		List<String> allQueries = getQueries(node.getName());
		// Get sub categories of the current category
		categories = node.getChildren();
		// Initialize coverage with 0s
		List<Integer> coverage = new ArrayList<Integer>(Collections.nCopies(categories.size(), 0));
		// Initialize specificity with 0s
		List<Double> specificity = new ArrayList<Double>(Collections.nCopies(categories.size(), 0.0));
		
		// Print Stats
		if(MainClass.counter ==0) {
			System.out.println("\n\nClassifying...");
			MainClass.counter++;
		}
		
		for(int i = 0; i<allQueries.size(); i++){
			// Convert string to list
			List<String> queryList = MainClass.keyWordsToList(allQueries.get(i));
			// Remove category from query list
			List<String> trimQuery = queryList.subList(1, queryList.size());
			// Join query list to form a string
			String finalQuery = MainClass.listToKeyWords(trimQuery);
	//		System.out.println("Query is - " + finalQuery);
			// Check cache
			boolean valExists = cache.containsKey(finalQuery);
			if(valExists){
				hits = cache.get(finalQuery);
			}
			else{
				// Run Bing Search and count of results
				hits = Integer.parseInt(search.getResults(MainClass.bingAccountKey, MainClass.website, finalQuery));
				cache.put(finalQuery, hits);
			}
			// Calculate coverage for each category
			for(int j = 0;j<categories.size();j++){
				// Check if sub category name is present in original query
				if(queryList.contains(categories.get(j).getName())){
					int current = coverage.get(j) + hits;
					coverage.set(j, current);
				}
			}
		}
		
		// Calculate specificity for each sub category
		double totalCoverage = sum(coverage);
		for(int j = 0;j<categories.size();j++){
			Double specVal = (double)coverage.get(j)/totalCoverage;
			specificity.set(j, specVal);
		}
		
		for(int j=0;j<categories.size();j++){
			System.out.println("Specificity for category: " + categories.get(j).getName() + " is " + specificity.get(j));
			System.out.println("Coverage for category: " + categories.get(j).getName() + " is " + coverage.get(j));
		}
		
		// Recurse if coverage and specificity are above threshold
		if(path != null && !path.isEmpty()) {
			path = path + "/" + node.getName();
		} else {
			// Add Root node to classification
			if(!classification.contains(node)) {
				classification.add(node);
			}
			path = node.getName();
		}
		
		// Checks if sub category satisfies the threshold conditions
		boolean isCat = false;
		for(int j=0;j<categories.size();j++){
			if(coverage.get(j) > (int)tEc && specificity.get(j) > tEs){
				isCat = true;
				
				if(categories.get(j).hasChildren()){
					// Add Category with valid coverage and specificity to classification. Will be used in Step 2.
					classification.add(categories.get(j));
					
					qProber(url, tEc, tEs, categories.get(j),accountKey, path, cache, pathSet, classification);
				}
				else{
					// Reached the end of the path as no child is present
					pathSet.add(path + "/" + categories.get(j).getName());
				}
			}
		}
		// If no sub category is found, add the path to pathSet
		if(!isCat){
			pathSet.add(path);
		}
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
	private int sum(List<Integer> list){
	      if(list==null || list.size()<1)
	        return 0;

	      int sum = 0;
	      for(Integer i: list)
	        sum = sum+i;

	      return sum;
	}
}
