// Purpose: A simple GUI that counts the number of times a button is clicked.

// These are the imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// This is the class declaration.
// It is named Main since our file is named Main.java.
// Replit automatically names the file Main.java.
public class Main {
    // These are the variables
    private int count = 0;
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JButton button;

    // This is the constructor
    public Main() {
        // Create the frame and panel
        frame = new JFrame();
        label = new JLabel("Number of clicks: 0");

        // Set the panel properties
        button = new JButton("Click me!");
        button.setActionCommand("clicked");
        button.addActionListener(new Action());

        // Add the components to the panel
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(label);
        panel.add(button);

        // Set the frame properties
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("My GUI");
        frame.pack();
        frame.setVisible(true);
    }

    // This is the action listener
    // This is an inner class and it inherits from ActionListener
    // This means that it must implement the actionPerformed method
    // Inheritance is a Year 3 topic, but is needed to make this work
    class Action implements ActionListener {   
        // This is the method that is called when the button is clicked     
        public void actionPerformed(ActionEvent e) {
            count++;
            label.setText("Number of clicks: " + count);
        }
    }

    // This is the main method
    public static void main(String[] args){
        new Main();
    }
}
