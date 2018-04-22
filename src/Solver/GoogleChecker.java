package Solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleChecker {

	public static ArrayList<String> getGoogleSearch(String clue, int size) throws MalformedURLException, IOException{
		Scanner scan;
		String key="AIzaSyAVoL8InHjcBqyhgY4mECR_SAt7mH20zPY";
		String key1 = "AIzaSyAR8O8Qt2L-WKm_M9yZkTzkM0vas--z4-k";
		String seq = "\\w";
		System.out.println("google checker called " );
		Pattern pattern = Pattern.compile(seq);
		scan = new Scanner(clue);
		String qry = "";
		ArrayList<String> a = new ArrayList<String>();
		String temp;
		while(scan.hasNext()){
			temp = scan.next();
			if(temp.equals("___"))
				temp = "...";
			qry += temp + "+";
		}
		URL url = new URL(
				"https://www.googleapis.com/customsearch/v1?key="+key1+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output;
		String search;
		while ((output = br.readLine()) != null) {
			search = "";
			output = output.replaceAll("\"","");
			output = output.replaceAll(",","");
			output = output.replaceAll("'","");
			output = output.replaceAll("\\.","");
			if(output.contains("title:")){
				search  = output;
			}
			else if( output.contains("snippet:"))
				search  = output;
			scan = new Scanner(search);
			String addable = "";
			while(search!= "" && scan.hasNext()){
				addable = scan.next();
				if(addable.length() == size)// && !a.contains(addable))
				{

						a.add(addable);
				}
			}
		}
		conn.disconnect();
		br.close();
		return a;
	}

}
