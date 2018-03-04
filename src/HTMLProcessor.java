import java.net.*;
import java.io.*;
import java.util.*;

public class HTMLProcessor{

	String content;
	ArrayList<String>[] hints;
	
	
    public HTMLProcessor() throws Exception {
    	final String PUZZLE_DIR = "./ph/";
    	hints = new ArrayList[2];
    	hints[0] = new ArrayList<String>();
    	hints[1] = new ArrayList<String>();
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int month = cal.get(Calendar.MONTH) + 1;
    	int day = cal.get( Calendar.DAY_OF_WEEK);
    	int year = cal.get( Calendar.YEAR);

    	
        URL oracle = new URL("https://www.nytimes.com/crosswords/game/mini");
        BufferedReader in = new BufferedReader(
        			new InputStreamReader(oracle.openStream()));
        String puzzlePath = PUZZLE_DIR + month + "-" + day + "-" + year + ".txt";
		File file = new File( puzzlePath);
		BufferedWriter writer = new BufferedWriter(new FileWriter(puzzlePath));
       // System.out.println("Succesfully created html");
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            try{
                writer.write(inputLine);
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
        }
        in.close();
        writer.close();
        
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(puzzlePath));
            String str;
            while ((str = fileReader.readLine()) != null) {
                contentBuilder.append(str);
            }
            fileReader.close();
        } catch (IOException e) {
        	System.out.print("s��t�k");
        }
        content = contentBuilder.toString();
        //PARSING FOR HINTS
        String hintNoPattern = "\"Clue-label--";
        String hintPattern = "\"Clue-text--";
        int acrossDown = 0; // 0 for across, 1 for down
        content = content.substring(content.indexOf(">Across<")+8);
        //System.out.println("Across");
        while(true){
        	String hintString;
        	int downIndex = 99999999;
        	if(content.contains(">Down<")){
        		downIndex = content.indexOf(">Down<");
        	}
        	int hintNoIndex = content.indexOf(hintNoPattern); //finding index
        	if(downIndex < hintNoIndex){
        		acrossDown = 1;
        		content = content.substring(content.indexOf(">Down<")+6);
            	hintNoIndex = content.indexOf(hintNoPattern); //finding index after update
        		//System.out.println("Down");
        	}
        	if(hintNoIndex == -1)
        		break;
        	hintNoIndex = hintNoIndex+20;
        	String hintNo = ""+ content.charAt(hintNoIndex);
        	if(content.charAt(hintNoIndex+1) != '<'){
        		hintNo = hintNo +content.charAt(hintNoIndex+1);
        	}
        	hintString = hintNo + "-"; 
        	content = content.substring(hintNoIndex);
        	
        	int hintStart = content.indexOf(hintPattern); //finding hint
        	if(hintStart == -1)
        		break;
        	hintStart = hintStart + 19;
        	String hint = "";
        	while(content.charAt(hintStart) != '<'){
        		hint = hint + content.charAt(hintStart);
        		hintStart++;
        	}
        	hintString = hintString + hint;
        	if(acrossDown == 0)
        		hints[0].add(hintString);
        	if(acrossDown == 1)
        		hints[1].add(hintString);
        	content = content.substring(hintStart);
        }
    }
    
}