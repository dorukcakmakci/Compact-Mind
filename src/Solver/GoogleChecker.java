package Solver;

import java.util.ArrayList;

public class GoogleChecker {

	public static ArrayList<String> getGoogleSearch(String clue, int size) {
		ArrayList<String> a = new ArrayList<String>();
		/*
		Scanner scan;
		String key = "AIzaSyC6cBUwOBXM5p_XnRN7Gi-5_zgAPs6YnvA";
		String seq = "\\w";
		//System.out.println("google checker called " );
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
				"https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json");
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
		*/
		return a;
	}

}
