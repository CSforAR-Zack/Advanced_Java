import java.util.*;
import java.net.*;
import java.sql.*;
import java.io.*;
import java.math.*;

import org.json.*;

public class Database {
    public void CreateDatabase() throws Exception{
        // Don't commit this token to GitHub
        String token = "ENTER TOKEN HERE";
        // The data we want to get from NOAA
        String datatypeIDs = "TMAX,TMIN";
        String units = "standard";
        String datasetID = "GHCND";
        String stationID = "GHCND:USW00003952";
        String startDate = "2022-01-01";
        String endDate = "2022-12-31";

        // URL for NOAA weather data
        URL requestURL = new URI(
            "https://www.ncdc.noaa.gov/cdo-web/api/v2/data?datasetid=" +
            datasetID + "&datatypeid=" + datatypeIDs + "&stationid=" + stationID + "&startdate=" +
            startDate + "&enddate=" + endDate + "&limit=1000&units=" + units
        ).toURL();

        // Create connection to NOAA weather data
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("token", token);

        // Get response code
        int responseCode = connection.getResponseCode();

        // If response code is 200, read the response
        if(responseCode == 200){
            // we have to get the input stream of the connection and then read it
            // We then take the InputStreamReader and wrap it in a BufferedReader
            // because it is more efficient to read a line at a time rather than char by char.
            // The BufferedReader will allow us to read a until the new line character (read by lines)
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader in = new BufferedReader(streamReader);
            StringBuffer response = new StringBuffer();

            
            // Read the response line by line
            // We use the inputLine variable to read the next line of the response
            // to there is something to read.
            // We start off by reading the first line of the response
            String inputLine = in.readLine();
            while (inputLine != null) {
                response.append(inputLine);
                inputLine = in.readLine();
            }
            in.close();
        
            // Convert the response to a JSON object so we can parse it
            JSONObject json = new JSONObject(response.toString());
            // The results are stored in the JSON as an array
            // We can get the array and then loop through it to get the data
            JSONArray dataList = json.getJSONArray("results");
            
            // Create database
            // The driver class we are using is from sqlite-jdbc-3.34.0.jar
            // we need a driver class for each database we want to connect to
            // and does not come with Java
            String dburl = "jdbc:sqlite:data.db";
            Connection conn = DriverManager.getConnection(dburl);

            // Create a sql statement object
            // We use this to execute sql commands
            Statement statement = conn.createStatement();
            // Create a table if it does not exist
            statement.execute("CREATE TABLE IF NOT EXISTS weather (id INTEGER PRIMARY KEY AUTOINCREMENT, date DATETIME UNIQUE, tmin REAL, tmax REAL);");

            // Loop through the data and insert it into the database
            String datatype = "";
            BigDecimal value = null;
            ResultSet rs = null;

            for(int i = 0; i < dataList.length(); i++){
                // Get the data for each item in the array
                // and convert it into a JSON object for easier access
                JSONObject data = dataList.getJSONObject(i);

                // Check if the date is already in the database
                // If not, add it. We only want one entry per date
                rs = statement.executeQuery("SELECT * FROM weather WHERE date = '" + data.get("date") + "';");
                if(!rs.next()){
                    statement.execute("INSERT INTO weather (date) VALUES ('" + data.get("date") + "');");
                }                
                
                // Check the datatype and update the database accordingly
                datatype = (String) data.get("datatype");

                if(datatype.equals("TMAX")){
                    value = (BigDecimal) data.get("value");
                    statement.execute("UPDATE weather SET tmax = " + value + " WHERE date = '" + data.get("date") + "';");
                }else if(datatype.equals("TMIN")){
                    value = (BigDecimal) data.get("value");
                    statement.execute("UPDATE weather SET tmin = " + value + " WHERE date = '" + data.get("date") + "';");
                }
            }
            conn.close();
        }
        else{
            System.out.println("Error: " + responseCode);
        }
        connection.disconnect();
    }    

    public ArrayList<String []> GetData() throws Exception{
        String dburl = "jdbc:sqlite:data.db";
        Connection conn = DriverManager.getConnection(dburl);

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM weather;");

        // Convert ResultSet to ArrayList
        ArrayList<String []> data = new ArrayList<String []>();
        // Loop through the ResultSet and add each row to the ArrayList
        // We use .next() to move to the next row
        // The first row is is the header row so we skip it
        while(rs.next()){
            String [] row = new String[3];
            row[0] = rs.getString("date");
            row[1] = rs.getString("tmin");
            row[2] = rs.getString("tmax");
            data.add(row);
        }

        return data;
    }
}
