import java.util.*;
import java.net.*;
import java.sql.*;

import java.io.InputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Database {
    public void CreateDatabase() throws Exception{
        // Don't commit this token to GitHub
        String token = "INSTER TOKEN HERE";
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

        String response = "";
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();

        if(responseCode == 200){
            InputStream inputStream = connection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            response = scanner.nextLine();
            scanner.close();

            json = (JSONObject) parser.parse(response);
            ArrayList<JSONObject> dataList = (ArrayList<JSONObject>) json.get("results");
            
            String dburl = "jdbc:sqlite:data.db";
            Connection conn = DriverManager.getConnection(dburl);

            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS weather (id INTEGER PRIMARY KEY AUTOINCREMENT, date DATETIME UNIQUE, tmin REAL, tmax REAL);");

            String datatype = "";
            Double value = 0.0;
            ResultSet rs = null;

            for(JSONObject data : dataList){
                rs = statement.executeQuery("SELECT * FROM weather WHERE date = '" + data.get("date") + "';");
                if(!rs.next()){
                    statement.execute("INSERT INTO weather (date) VALUES ('" + data.get("date") + "');");
                }                
                
                datatype = (String) data.get("datatype");

                if(datatype.equals("TMAX")){
                    value = (Double) data.get("value");
                    statement.execute("UPDATE weather SET tmax = " + value + " WHERE date = '" + data.get("date") + "';");
                }else if(datatype.equals("TMIN")){
                    value = (Double) data.get("value");
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
