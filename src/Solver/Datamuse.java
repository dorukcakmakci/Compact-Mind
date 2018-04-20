package backend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import Parser.JSONParse;

public class Datamuse
{
	private JSONParse parser;
	public static ArrayList<String> results;
	public static int[] scores;

	public Datamuse()
	{
		parser = new JSONParse();
		results = new ArrayList<String>();
	}

	public void checkDatamuse(String key, int size)
	{
		key = key.replaceAll(" ", "+");

		if(!results.isEmpty())
			results.clear();
		
		String json;
		json = findSimilar(key);
		if(json.isEmpty())
		{		}
		else
		{
			System.out.println(json);
			results = parser.parseWords(json, size);
			scores = parser.parseScores(json);
		}
		
	}

	public String findSimilar(String word) {
		String s = word.replaceAll(" ", "+");
		return getJSON("http://api.datamuse.com/words?rd="+s);

	}

	public String findSimilarStartsWith(String word, String startLetter) {
		String s = word.replaceAll(" ", "+");
		return getJSON("http://api.datamuse.com/words?rd="+s+"&sp="+startLetter+"*");
	}

	public String findSimilarEndsWith(String word, String endLetter) {
		String s = word.replaceAll(" ", "+");
		return getJSON("http://api.datamuse.com/words?rd="+s+"&sp=*"+endLetter);
	}

	public String wordsStartingWithEndingWith(String startLetter, String endLetter, int numberMissing) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numberMissing; i++) {
			sb.append("?");
		}
		return getJSON("http://api.datamuse.com/words?sp=" + startLetter + sb + endLetter);
	}

	public String wordsStartingWithEndingWith(String startLetter, String endLetter) {
		return getJSON("http://api.datamuse.com/words?sp=" + startLetter + "*" + endLetter);
	}

	public String soundsSimilar(String word) {
		String s = word.replaceAll(" ", "+");
		return getJSON("http://api.datamuse.com/words?sl=" + s);
	}

	public String speltSimilar(String word) {
		String s = word.replaceAll(" ", "+");
		return getJSON("http://api.datamuse.com/words?sp=" + s);
	}

	public String prefixHintSuggestions(String word) {
		String s = word.replaceAll(" ", "+");
		return getJSON("http://api.datamuse.com/sug?s=" + s);
	}

	private String getJSON(String url) {
		URL datamuse;
		URLConnection dc;
		StringBuilder s = null;
		try {
			datamuse = new URL(url);
			dc = datamuse.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(dc.getInputStream(), "UTF-8"));
			String inputLine;
			s = new StringBuilder();
			while(!in.ready());
			while ((inputLine = in.readLine()) != null)
				s.append(inputLine);
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s != null ? s.toString() : null;
	}
}
