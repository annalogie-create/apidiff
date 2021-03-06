package apidiff;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import apidiff.enums.Classifier;
import apidiff.util.UtilFile;

public class Test {
	
	public static final String path = "";
	
	public Test() {
		
	}

	public static void main(String[] args) throws Exception {
		String nameProject = "easymock/easymock";
		String url = "https://github.com/easymock/easymock";
		Test.getChangesAllHistoryCSV(nameProject, url);
	}
	
	public static void getChangesAllHistoryCSV(String nameProject, String url) {
		APIDiff diff = new APIDiff(nameProject, url);
		diff.setPath(path);
		Result result;
		try {
			result = diff.detectChangeAllHistory("master", Classifier.API);
			List<String> listChanges = new ArrayList<String>();
			listChanges.add("Category;isDeprecated;containsJavadoc;Commit");
			for(Change changeMethod : result.getChangeMethod()){
				if (changeMethod.isBreakingChange()){
				long epoch = (long) changeMethod.getRevCommit().getCommitTime();
				String date = convertEpochToDate(epoch);
			    String change = changeMethod.getCategory().getDisplayName() + ";" + 
				changeMethod.isDeprecated()  + ";" + changeMethod.containsJavadoc() + 
				";" + date ;
			    listChanges.add(change);}
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
	public static String convertEpochToDate(Long epoch)
	   {
	   //convert seconds to milliseconds
	   Date date = new Date(epoch*1000L); 
	   // format of the date
	   SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	   jdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	   String java_date = jdf.format(date);
	   return java_date;
	   }

}