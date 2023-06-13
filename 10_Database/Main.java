// Need json-simple-1.1.1.jar
// Need sqlite-jdbc-3.34.0.jar
// Need JFreeChart-1.5.3.jar

import org.jfree.chart.*;
import org.jfree.data.time.*;
import javax.swing.*;
import java.util.*;

import java.text.SimpleDateFormat;

public class Main extends JFrame{
    public static void main(String[] args) throws Exception{
        Database db = new Database();
        db.CreateDatabase();

        // Create chart
        Main chartWindow = new Main();
        chartWindow.pack();
        chartWindow.setSize(600, 400);
        chartWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chartWindow.setVisible(true);
    }

    public Main() throws Exception{
        TimeSeriesCollection dataset = createDataset();
        // Create chart
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Weather Data", // Title
                "Date", // X-Axis Label
                "Temperature (\u00b0F)", // Y-Axis Label
                dataset // Data to plot
        );
        // Add chart to panel to display
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private static TimeSeriesCollection createDataset() throws Exception{
        // Create two series to store min and max temperatures
        TimeSeries series1 = new TimeSeries("Min. Temperatures");
        TimeSeries series2 = new TimeSeries("Max Temperatures");

        // Get data from database
        Database db = new Database();
        ArrayList<String []> data = db.GetData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        // Loop through data and add to appropriate series
        for (int i = 1; i < data.size(); i++) {
            String stringDate = data.get(i)[0];

            Date date = new Date();
            date = formatter.parse(stringDate);
            Day day = new Day(date);

            Double minTemp = Double.parseDouble(data.get(i)[1]);
            Double maxTemp = Double.parseDouble(data.get(i)[2]);
            
            series1.add(day, minTemp);
            series2.add(day, maxTemp);
        }

        // Create the collection and add the series
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }
}