import java.io.*;
import java.net.*;

import org.json.JSONObject;

public class Main{

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Get the URL of the NASA Image for the Day API
        URL url = new URI("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY").toURL();
        // Open a connection to the URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        // Get the response code
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Read the response
        // InputStreamReader reads bytes and decodes them into characters using a specified charset
        InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
        // BufferedReader lets us read line by line from the InputStreamReader
        BufferedReader in = new BufferedReader(streamReader);
        String inputLine = null;
        // StringBuffer lets us append strings. We add all the lines to the StringBuffer to get the full response
        StringBuffer response = new StringBuffer();

        // Read the response line by line
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject myResponse = new JSONObject(response.toString());
        // Print the title from the json
        System.out.println("title: " + myResponse.getString("title"));

        // Print the explanation from the json
        System.out.println("explanation: " + myResponse.getString("explanation"));
        
        // Print the url from the json
        System.out.println("url: " + myResponse.getString("url"));

        // Get the image url from the json
        String imageUrl = myResponse.getString("url");
        // Get the file name from the url
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        System.out.println("Downloading image: " + fileName);
        
        // Download the image
        URL urlImage = new URI(imageUrl).toURL();
        // Open a connection to the URL
        InputStream inImage = urlImage.openStream();
        // BufferedOutputStream lets us write bytes to a file
        OutputStream outImage = new BufferedOutputStream(new FileOutputStream(fileName));
        // Write the image to the file
        for (int i; (i = inImage.read()) != -1;) {
            outImage.write(i);
        }
        outImage.close();
        inImage.close();
    }
}