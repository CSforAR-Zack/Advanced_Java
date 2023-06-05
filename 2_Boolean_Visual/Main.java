
// Purpose: This program is a visual representation of boolean logic.

// These are the imports
// The * means import everything from the package
// This is a bad practice, but is used here for simplicity
// javax.swing is a package that contains the GUI elements
import javax.swing.*;
import javax.swing.border.*;

// java.awt is a package that contains the Abstract Window Toolkit
import java.awt.*;
import java.awt.event.*;

// This is the class declaration
public class Main {    
    // These are the variables
    // They are not initialized here, but in the constructor
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton leftButton, rightButton;
    private JLabel resultLabel;
    private JComboBox<String> operationChoice;
    private GridLayout mainGridLayout;
    private GridLayout topGridLayout;
    private GridLayout bottomGridLayout;

    // These are the colors for the GUI
    // We created them here so we can easily change them
    // and be consistent throughout the program
    private Color bgColor = Color.black;
    private Color offColor = Color.gray;
    private Color onColor = Color.cyan;

    // This is the constructor
    public Main() {
        // Create the frame and panels
        frame = new JFrame();
        mainPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        
        // Set the frame properties
        frame.setTitle("Visual Bools");
        frame.setSize(360, 170);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);

        // Set the layout and panel properties
        // The GridLayout lets us easily add elements in a grid fashion
        mainGridLayout = new GridLayout(0, 1, 5, 5);
        mainPanel.setLayout(mainGridLayout);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);
        mainPanel.setBackground(bgColor);

        // The topGridLayout is inside the mainGridLayout
        topGridLayout = new GridLayout(0, 3, 5, 5);
        topPanel.setLayout(topGridLayout);
        topPanel.setBackground(bgColor);

        // Add the left button
        leftButton = new JButton("false");
        leftButton.setBackground(offColor);
        leftButton.setActionCommand("leftButton");
        leftButton.addActionListener(new CalculateAction());
        topPanel.add(leftButton);

        // Add the ComboBox
        String[] choices = {"and", "or", "xor"};
        operationChoice = new JComboBox<String>(choices);
        operationChoice.setBackground(offColor);
        operationChoice.setActionCommand("operationChoice");
        operationChoice.addActionListener(new CalculateAction());
        topPanel.add(operationChoice);

        // Add the right button
        rightButton = new JButton("false");
        rightButton.setBackground(offColor);
        rightButton.setActionCommand("rightButton");
        rightButton.addActionListener(new CalculateAction());
        topPanel.add(rightButton);

        // The bottomGridLayout is inside the mainGridLayout
        bottomGridLayout = new GridLayout(0, 1, 5, 5);
        bottomPanel.setLayout(bottomGridLayout);
        bottomPanel.setBackground(bgColor);

        // Add the result label
        resultLabel = new JLabel("false", SwingConstants.CENTER);
        resultLabel.setBackground(offColor);
        resultLabel.setOpaque(true);
        bottomPanel.add(resultLabel);
        
        // Make the frame visible
        frame.setVisible(true);      
    }

    // This is the action listener for the encode button
    private class CalculateAction implements ActionListener {        
        public void actionPerformed(ActionEvent event) {
            String action = event.getActionCommand();
            if (action == "leftButton" || action == "rightButton") {
                JButton button = (JButton)event.getSource();
    
                if (Boolean.parseBoolean(button.getText()) && button != null) {
                    button.setText("false");
                    button.setBackground(offColor);
                } else {                
                    button.setText("true");
                    button.setBackground(onColor);
                }
            }
            processResults();
        }        
    }

    // This method processes the results
    private void processResults() {
        boolean leftButtonValue = Boolean.parseBoolean(leftButton.getText());
        boolean rightButtonValue = Boolean.parseBoolean(rightButton.getText());

        String operation = operationChoice.getSelectedItem().toString();
        boolean resultValue = false;
        
        // This is a switch statement as an alternative to the if statement
        // switch (operation) {
        //     case "and":
        //         resultValue = leftButtonValue && rightButtonValue;
        //         break;
        //     case "or":
        //         resultValue = leftButtonValue || rightButtonValue;
        //         break;
        //     case "xor":
        //         resultValue = leftButtonValue ^ rightButtonValue;
        //         break;
        // }

        if (operation == "and") {
            resultValue = leftButtonValue && rightButtonValue;
        } else if (operation == "or") {
            resultValue = leftButtonValue || rightButtonValue;
        } else {
            resultValue = leftButtonValue ^ rightButtonValue;
        }

        if (resultValue) {
            resultLabel.setText("true");
            resultLabel.setBackground(Color.CYAN);
        } else {
            resultLabel.setText("false");
            resultLabel.setBackground(Color.GRAY);
        }
    }

    // This is the main method that gets called when the program runs
    public static void main(String[] args) {
        // Create an instance of the Main class
        // we don't need to store it in a variable because we don't use it.
        new Main();
    }
}
