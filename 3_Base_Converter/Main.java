// Purpose: This program will take a number in a given base and convert it to another base

// It is sometimes frowned upon when using the wildcard import *
// but we do this to save time
// This is the full import list for this program
import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

// This is the class declaration.
// It is named Main since our file is named Main.java.
// Replit automatically names the file Main.java.
public class Main {
    // Declare the variables
    private JFrame frame;
    private JPanel panel;
    private JButton encodeButton;
    private JTextField inputNumber;
    private JComboBox<String> inputBaseChoice, outputBaseChoice;
    private JLabel inputBaseLabel, outputBaseLabel, resultLabel;
    private GridLayout gridLayout;

    // This is the constructor.
    public Main() {
        // Create the frame and panel
        frame = new JFrame();
        panel = new JPanel();

        // Set the frame properties
        frame.setTitle("Base Encoder");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        
        // Set the panel properties
        gridLayout = new GridLayout(0, 1, 5, 5);
        panel.setLayout(gridLayout);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.black);

        // Add a TextField for the input number
        inputNumber = new JTextField();
        panel.add(inputNumber);

        // Add a label for the input base
        inputBaseLabel = new JLabel("Select an Input Base System");
        inputBaseLabel.setForeground(Color.white);
        panel.add(inputBaseLabel);

        // Add a ComboBox (Dropdown) for the input base
        String[] choices = {"Binary", "Octal", "Decimal", "Hexidecimal"};
        inputBaseChoice = new JComboBox<String>(choices);
        panel.add(inputBaseChoice);

        // Add a label for the output base
        outputBaseLabel = new JLabel("Select an Output Base System");
        outputBaseLabel.setForeground(Color.white);
        panel.add(outputBaseLabel);

        // Add a ComboBox (Dropdown) for the output base
        outputBaseChoice = new JComboBox<String>(choices);
        panel.add(outputBaseChoice);

        // Add a button to encode the number
        encodeButton = new JButton("Encode");
        encodeButton.addActionListener(new EncodeAction());
        panel.add(encodeButton);

        // Add a label for the result
        resultLabel = new JLabel("0000", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 36));
        resultLabel.setForeground(Color.white);
        panel.add(resultLabel);

        // Make the frame visible
        frame.setVisible(true);
    }

    /**
    * This is the action that is performed when the button is clicked
    * It will take the input number and convert it to the output base
    * and display the result
    * This is an inner class and it inherits from ActionListener
    * This means that it must implement the actionPerformed method
    * Inheritance is a Year 3 topic, but is needed to make this work
    */
    private class EncodeAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get the input and output base from the ComboBoxes
            String inputBase = inputBaseChoice.getSelectedItem().toString();
            String outputBase = outputBaseChoice.getSelectedItem().toString();
            // Get the input number from the TextField
            String rawNumber = inputNumber.getText();

            // Convert the input number to an integer
            Integer result = 0;
            if (inputBase == "Binary") {
                result = Integer.parseInt(rawNumber, 2);
            } else if (inputBase == "Octal") {
                result = Integer.parseInt(rawNumber, 8);
            } else if (inputBase == "Decimal") {
                result = Integer.parseInt(rawNumber, 10);
            } else {
                result = Integer.parseInt(rawNumber, 16);
            }

            // Convert the integer to the output base
            String resultString = "";

            if (outputBase == "Binary") {
                resultString = Integer.toBinaryString(result);
            } else if (outputBase == "Octal") {
                resultString = Integer.toOctalString(result);
            } else if (outputBase == "Decimal") {
                resultString = result.toString();
            } else {
                resultString = Integer.toHexString(result);;
            }

            // Display the result
            resultLabel.setText(resultString);
        }        
    }

    // This is the main method
    public static void main(String[] args) {
        // Create a new instance of the Main class
        new Main();
    }    
}