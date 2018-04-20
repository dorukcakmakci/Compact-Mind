package Search;


import Parser.PuzzleWord;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MovieSearch {
    public static ArrayList<String> search( PuzzleWord puzzleWord) throws IOException {
        ArrayList<String> answers = new ArrayList<String>();
        String API_KEY = "9e3d7f60";
        String[] searchAdresses = { "http://www.omdbapi.com/?t=SEARCH_KEY&plot=full&apikey=",
                "\"http://www.omdbapi.com/?s=SEARCH_KEY&plot=full&apikey=\""};
        String hint = puzzleWord.getHint().toLowerCase();
        boolean movieCheck = false;

        if ( hint.contains("movie") || hint.contains("film") || hint.contains("imdb") || hint.contains("movies") ||
                hint.contains("films") || hint.contains("actor") || hint.contains("Oscar") || hint.contains("tv")
                ||hint.contains("series") || hint.contains("show"))
            movieCheck = true;

        if (movieCheck){
            hint = hint.replace(")", " ").replace("(", " ").
                    replace("\'"," ");

            String[] movieHints = hint.split("\\s+");

            for (String movieHint: movieHints){

                for ( String address: searchAdresses) {
                    //Init parameters for request
                    String searchKey = URLEncoder.encode(movieHint, "UTF-8");
                    String request = address + API_KEY;
                    request.replaceAll("SEARCH_KEY", searchKey);

                    //Init request
                    StringBuffer response = new StringBuffer();

                    URL url = new URL(request);
                    HttpURLConnection conn = null;
                    try {
                        conn = (HttpURLConnection) url.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        conn.setRequestMethod("GET");
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }

                    conn.setRequestProperty("Accept", "/");
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                    InputStream stream = null;
                    try {
                        stream = conn.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    InputStreamReader streamReader = new InputStreamReader(stream);

                    BufferedReader bufferedReader = new BufferedReader(streamReader);
                    StringBuffer responseBuffer = new StringBuffer();

                    String ln =  "";

                    while( ( ln = bufferedReader.readLine()) != null){
                        responseBuffer.append(ln);
                    }

                    bufferedReader.close();
                    conn.disconnect();

                    String ans = responseBuffer.toString();

                    ans = ans.replace("(", " ").replace(")", " ").
                            replace("?", " ").replace("\"", " ").
                            replace("}", " ").replace("{", " ").
                            replace(":", " ");

                    String[] results = ans.split("\\s+");

                    for (String res: results){
                        if (res.length() == puzzleWord.getLength())
                            answers.add(res);
                    }
                }
            }
        }
        return answers;
    }
}
