import java.io.*;
import java.net.*;

public class Main{

    public static void main(String[] args) throws IOException {
        // Get the URL of the NASA Image for the Day API
        URL url = new URL("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2015-6-3&api_key=DEMO_KEY");
        // Open a connection to the URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        // Get the response code
        int responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        
        // Parse JSON data from the response without third party libraries
        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();
        // Read the response line by line
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        // Close the connection
        in.close();
        con.disconnect();

        int start = content.indexOf("img_src");
        int end = content.indexOf(".JPG") + 4;
        
        // Get the URL of the image
        String imageUrlString = content.substring(start + 10, end);
        System.out.println(imageUrlString);
        URL imageUrl = new URL(imageUrlString);
        
        InputStream is = imageUrl.openStream();
        OutputStream os = new FileOutputStream("image.jpg");
        int bytesRead = 0;
        byte[] buffer = new byte[2048];
        // Read the image data
        while ((bytesRead = is.read(buffer, 0, 2048)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        // Close the streams
        is.close();
        os.close();

    }
}