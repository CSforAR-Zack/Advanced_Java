// Purpose: Visualize the sorting algorithms

// Frowned upon when using the wildcard import *
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.*;

// This is the class declaration
public class Main {
    // These are the variables
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Integer> heights = new ArrayList<Integer>();
    private ArrayList<JLabel> bars = new ArrayList<JLabel>(); 

    private int numberOfBars = 30;
    private int startX = 10;
    private int startY = 10;
    private int barWidth = 20;
    private int barMaxHeight = 100;
    private int barMinHeight = 1;
    private int xPadding = 10;
    private int speed = 100;
    private Random random = new Random();

    private Color sortedColor = Color.green;
    private Color current = Color.yellow;
    private Color lookingAt = Color.cyan;
    private Color unsorted = Color.gray;
    private Color bestColor = Color.red;
    
    // This is the constructor
    public Main() {
        // Create the frame and panel
        frame = new JFrame();
        panel = new JPanel();

        // Set the frame properties
        frame.setTitle("Visual Sorter");
        frame.setSize(1000, 170);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        // Add the bars to the panel
        for (int i = 0; i < numberOfBars; i++) {
            JLabel bar = new JLabel();
            bar.setBackground(Color.gray);
            bar.setOpaque(true);
            panel.add(bar);
            bars.add(bar);
        }

        setInitialHeights();
        frame.setVisible(true);

        // Sort the bars
        
        // insertionSort();
        // selectionSort();
        // bubbleSort();
        // insertionSortColor();
        selectionSortColor();
        // bubbleSortColor();

        updateHeights();

        frame.setVisible(true);
    }

    // This pauses the program and updates heights
    // so we can see the sorting.
    private void pauseUpdate() {
        // This is a try-catch block and is required when using TimeUnit
        try {
            TimeUnit.MILLISECONDS.sleep(speed);
            updateHeights();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // This swaps two elements in the ArrayList
    private void swap(int i, int j)
    {
        Integer temp = heights.get(i);
        heights.set(i, heights.get(j));
        heights.set(j, temp);
    }
    
    // This sets the initial heights of the bars
    private void setInitialHeights() {        
        for (int i = 0; i < numberOfBars; i++) {
            Integer height = random.nextInt(barMaxHeight - barMinHeight) + barMinHeight;
            heights.add(height);
        }
    }

    // This updates the heights of the bars
    private void updateHeights() {      
        for (int i = 0; i < numberOfBars; i++) {
            JLabel bar = bars.get(i);
            int height = heights.get(i);
            bar.setBounds(startX + i * (barWidth + xPadding), barMaxHeight + startY - height, barWidth, height);
        }
    }

    // Sorts without Color

    // This is the insertion sort algorithm without color
    private void insertionSort() {        
        for (int j = 1; j < numberOfBars; j++) {
            int k = j - 1;
            while ((k >= 0) && (heights.get(k) > heights.get(k + 1))) {
                swap(k, k + 1);
                k--;
                pauseUpdate();
            }
        }
    }

    // This is the selection sort algorithm without color
    private void selectionSort() {        
        for (int i = 0; i < numberOfBars; i++) {
            int best = i;
            for (int j = i + 1; j < numberOfBars; j++) {
                if (heights.get(j) < heights.get(best)) {
                    best = j;
                }
                pauseUpdate();
            }
            if (best != i) {
                swap(i, best);
            }
        }
    }

    // This is the bubble sort algorithm without color
    private void bubbleSort() {
        boolean done = false;
        int sorted = 0;

        while (!done) {
            done = true;
            for (int k = 0; k < numberOfBars - sorted - 1; k++) {
                if (heights.get(k) > heights.get(k + 1)) {
                    swap(k, k + 1);
                    done = false;
                }
                pauseUpdate();
            }
            sorted++;
        }
    }

    // Sorts with Color

    // This is the insertion sort algorithm with color
    private void insertionSortColor() {        
        bars.get(0).setBackground(sortedColor); //-----------------------     
        for (int j = 1; j < numberOfBars; j++) {
            bars.get(j).setBackground(lookingAt); //-----------------------
            pauseUpdate(); //-----------------------
            int k = j - 1;
            while ((k >= 0) && (heights.get(k) > heights.get(k + 1))) {
                bars.get(k + 1).setBackground(current); //-----------------------
                bars.get(k).setBackground(lookingAt); //-----------------------
                pauseUpdate(); //-----------------------
                swap(k, k + 1);
                bars.get(k + 1).setBackground(sortedColor); //-----------------------
                pauseUpdate(); //-----------------------
                k--;
            }
            bars.get(k + 1).setBackground(sortedColor); //-----------------------
        }
    }

    // This is the selection sort algorithm with color
    private void selectionSortColor() {        
        for (int i = 0; i < numberOfBars; i++) {
            int best = i;

            bars.get(i).setBackground(current); //-----------------------
            pauseUpdate(); //-----------------------

            for (int j = i + 1; j < numberOfBars; j++) {
                bars.get(j).setBackground(lookingAt); //-----------------------
                pauseUpdate(); //-----------------------

                if (heights.get(j) < heights.get(best)) {
                    if (best == i) {
                        bars.get(i).setBackground(current); //-----------------------
                    } else {
                        bars.get(best).setBackground(unsorted); //-----------------------
                    }
                    best = j;
                    bars.get(best).setBackground(bestColor); //-----------------------
                    pauseUpdate(); //-----------------------
                } else {
                    pauseUpdate(); //-----------------------
                    bars.get(j).setBackground(unsorted); //-----------------------
                }
            }
            if (best != i) {
                swap(i, best);
            }
            pauseUpdate(); //-----------------------
            bars.get(best).setBackground(unsorted); //-----------------------
            bars.get(i).setBackground(sortedColor); //-----------------------
        }
        pauseUpdate(); //-----------------------   
    }

    // This is the bubble sort algorithm with color
    private void bubbleSortColor() {
        boolean done = false;
        int sorted = 0;

        while (!done) {
            done = true;
            for (int k = 0; k < numberOfBars - sorted - 1; k++) {
                bars.get(k).setBackground(current); //-----------------------
                bars.get(k + 1).setBackground(lookingAt); //-----------------------
                pauseUpdate(); //-----------------------

                if (heights.get(k) > heights.get(k + 1)) {
                    swap(k, k + 1);
                    pauseUpdate(); //-----------------------
                    bars.get(k).setBackground(unsorted); //-----------------------
                    done = false;
                } else {
                    pauseUpdate(); //-----------------------
                    bars.get(k).setBackground(unsorted); //-----------------------
                }
            }
            sorted++;
            bars.get(numberOfBars - sorted).setBackground(sortedColor); //-----------------------
        }
        pauseUpdate(); //-----------------------
    }

    // This is the main method
    public static void main(String[] args) {
        new Main();
    }    
}
