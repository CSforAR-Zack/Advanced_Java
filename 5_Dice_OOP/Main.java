// Libraries to import
import javax.swing.*;
import java.util.*;
import java.awt.*;
// Third party libraries
import org.jfree.chart.*; 
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import org.jfree.chart.renderer.category.BarRenderer;
 
// extends JFrame to create a window
public class Main extends JFrame {
    // Number of rolls to simulate and dice to use
    int numberOfRolls = 100000;
    Die die1 = new Die(20);
    Die die2 = new Die(20);
    
    public Main() {
        // Create the chart      
        JFreeChart barChart = ChartFactory.createBarChart(
            "Dice Roll Simulation", // Chart title        
            "Sum (Die1 + Die2)", // X-Axis Label         
            "Frequencies", // Y-Axis Label         
            createDataset(), // Dataset        
            PlotOrientation.VERTICAL, // Plot Orientation       
            false, false, false // Show Legend, Tooltips, and URLs
        );
        
        // Set the chart properties
        BarRenderer renderer = (BarRenderer) barChart.getCategoryPlot().getRenderer();
        // Set the bar width, -20 makes the bars look nice and close together
        renderer.setItemMargin(-20);
        // Set the bar colors to blue
        for (int i = 0; i <= 38; i++){
            renderer.setSeriesPaint(i, Color.blue);            
        }

        // Set the font size of the x-axis labels to fit
        CategoryPlot plot = barChart.getCategoryPlot();
        Font font = new Font("Dialog", Font.PLAIN, 10); 
        plot.getDomainAxis().setTickLabelFont(font);
        
        // Add the chart to a panel
        ChartPanel chartPanel = new ChartPanel(barChart);       
        setContentPane(chartPanel); 
    }
    
    // Create the dataset
    private CategoryDataset createDataset( ) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Simulate rolling the dice and store the results in a HashMap
        HashMap<Integer, Integer> rolls = SimulateRolling(die1, die2, numberOfRolls);

        for (Integer key : rolls.keySet()){
            String x = key.toString();
            Integer y = rolls.get(key);
            // Add the data to the dataset y, x, series name
            // We hack the series name to be the same as the x-axis label
            dataset.addValue(y, x, x);  
        }
        
        return dataset; 
    }

    // Simulate rolling the dice
    private HashMap<Integer, Integer> SimulateRolling(Die die1, Die die2, int numberOfRolls){
        ArrayList<Integer> rolls = new ArrayList<Integer>();
        
        // Roll the dice and add the sum to the rolls array
        for (int i = 0; i < numberOfRolls; i++){
            Integer rolledSum = die1.Roll() + die2.Roll();
            rolls.add(rolledSum);
        }

        // Count the number of occurrences of each sum
        // We use a HashMap to store the sum and the number of occurrences
        // so we can use the sum as the key and the number of occurrences as the value
        HashMap<Integer, Integer> frequencies = new HashMap<Integer, Integer>();

        // We start at 2 because the lowest possible sum is 2
        // and the highest possible sum is the number of sides on each die
        // Iterate through the possible sums and count the number of occurrences
        int rollSum = die1.GetNumberOfSides() + die2.GetNumberOfSides();            
        for (int i = 2; i <= rollSum; i++){
            Integer occurrences = Collections.frequency(rolls, i);
            frequencies.put(i, occurrences);
        }
        
        return frequencies;
    }
    
    public static void main(String[] args){
        Main example = new Main();
        example.pack();
        example.setSize(800, 400);
        example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        example.setVisible(true);
    }        
}