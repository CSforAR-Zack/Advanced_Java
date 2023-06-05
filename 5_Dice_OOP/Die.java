// This is a Class that represents a Die.

// We use java.util.Random to generate random numbers
import java.util.*;

// This is the class declaration
public class Die{
    // These are the variables
    // We use private to make them only accessible within this class
    int numSides = 0;
    
    // This is the constructor that takes in the number of sides
    // and assigns it to the numSides variable
    public Die(int numSides){
        this.numSides = numSides;
    }

    // This is a getter method
    // It returns the number of sides
    public int GetNumberOfSides(){
        return numSides;
    }

    // This simulates rolling the die
    // It randomly generates a number between 1 and the number of sides
    // It returns the number that was rolled
    public int Roll(){
        Random rand = new Random();
        
        return rand.nextInt(1, numSides + 1);
    }
}