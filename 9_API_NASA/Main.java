import java.io.*;
import java.net.*;

public class Main{

    public static void main(String[] args) throws IOException {
        // Get the URL of the NASA Image for the Day API
        URL url = new URL("https://api.nasa.gov/planetary/apod");
        // Open a connection to the URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // Set the API key as a request property
        con.setRequestProperty("api_key", "DEMO_KEY");
        // Set the request method to GET
        con.setRequestMethod("GET");

        // Get the response code
        int responseCode = con.getResponseCode();

        
        if (responseCode == 200) {
            InputStream inputStream = con.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("nasa_image.jpg");

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            while (bytesRead >= 0) {
                fileOutputStream.write(buffer, 0, bytesRead);
                bytesRead = inputStream.read(buffer);
            }
            fileOutputStream.close();
            inputStream.close();            
        } else {
            System.out.println("Error: " + responseCode);
        }

        con.disconnect();
    }
}