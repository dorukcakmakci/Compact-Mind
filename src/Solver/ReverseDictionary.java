package Solver;

import Parser.PuzzleWord;
import UI.CellBlock;
import UI.PuzzlePanel;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import org.apache.xpath.operations.Div;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReverseDictionary
{

    public static ArrayList<String> getReverseDict(String clue, int size) throws IOException{

        ArrayList<String> words = new ArrayList<String>();
        String keyword = clue;
        System.out.println("dictionary called " );
        keyword = keyword.replaceAll(" ", "%20");
        keyword = keyword.replaceAll("/", " per ");
        String requestUrl = "http://reversedictionary.org/wordsfor/" + keyword;

        /*Document doc;// = null;
        doc = Jsoup.connect(requestUrl).get();

        Elements divs = doc.getElementsByClass("item");

        for(int i = 0; i < divs.size(); i++) {
            String matched = divs.get(i).toString().toUpperCase();
            System.out.println(matched);
            if (matched.length() == size)
                words.add(matched);
        }

        for(int i = 0; i < words.size(); i++)
            System.out.println("---> " + words.get(i));

        return words;*/

        WebClient client = new WebClient();
        HtmlPage page = null;

        try {
            page = client.getPage(requestUrl);
        }catch(Exception e) {
            e.printStackTrace();
        }
        JavaScriptJobManager jobManager = page.getEnclosingWindow().getJobManager();
        while(jobManager.getJobCount()>0)
            client.waitForBackgroundJavaScript(1);

        List<?> elements = page.getByXPath("//div//a[@class='item']");

        for(int i = 0; i < elements.size(); i++) {
            String matched = ((List<HtmlElement>) (elements)).get(i).asText().toUpperCase();
            if(matched.length() == size)
                words.add(matched);
        }

        for(int i = 0; i < words.size(); i++)
            System.out.println("---> " + words.get(i));

        return words;
    }



}
