package apidiff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import apidiff.enums.Classifier;
import apidiff.util.UtilFile;

public class Test {
	
	public static final String path = "home/Projects";
	
	public Test() {
		
	}

	public static void main(String[] args) throws Exception {
		String nameProject = "gradle/gradle";
		String url = "https://github.com/gradle/gradle";
		Test.getChangesAllHistoryCSV(nameProject, url);
	}
	
	public static void getChangesAllHistoryCSV(String nameProject, String url) {
		APIDiff diff = new APIDiff(nameProject, url);
		diff.setPath(path);
		Result result;
		try {
			result = diff.detectChangeAllHistory("master", Classifier.API);
			List<String> listChanges = new ArrayList<String>();
			listChanges.add("Category;isDeprecated;containsJavadoc");
			for(Change changeMethod : result.getChangeMethod()){
			    String change = changeMethod.getCategory().getDisplayName() + ";" + changeMethod.isDeprecated()  + ";" + changeMethod.containsJavadoc() ;
			    listChanges.add(change);
			}
			UtilFile.writeFile("output.csv", listChanges);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fehler");
		}
		finally {
			System.out.println("Finish");}
		}
		        
		
	
	public static void getChangeAtCommit(String nameProject, String url, String commit) {
		APIDiff diff = new APIDiff(nameProject, url);
		diff.setPath(path);

		Result result = diff.detectChangeAtCommit(commit, Classifier.API);
		for(Change changeMethod : result.getChangeMethod()){
		    System.out.println("\n" + changeMethod.getCategory().getDisplayName() + " - " + changeMethod.getDescription());
		    System.out.println("Schleife");
		}
	}
	
	public static void getChangeAllHistory(String nameProject, String url) throws Exception {
		APIDiff diff = new APIDiff(nameProject, url);		
		diff.setPath(path);
		System.out.println("Successful cloning");
		Result result = diff.detectChangeAllHistory("master", Classifier.API);
		System.out.println("detected Change");
		for(Change changeMethod : result.getChangeMethod()){
		    System.out.println("\n" + changeMethod.getCategory().getDisplayName() + " - " + changeMethod.getDescription());
		    System.out.println("Hello");
		}
	}
}