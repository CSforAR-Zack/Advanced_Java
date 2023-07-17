
/**
 * This is a documentation comment. It is used to describe the program.
 * The documentation comment generally goes before a class or method.
 * This is the main class of the program. It is the entry point of the program.
 * It is the first class that is executed when the program is run.
 * public is an access modifier. It means that the class is visible to all other classes.
 * class is a keyword that is used to declare a class.
 */

public class Example {
    // This is the main method. It is the first method that is executed when the program is run.
    // It is the entry point of the program.
    // public is an access modifier. It means that the method is visible to all other classes.
    // static means that the method is associated with the class, not a specific instance (object) of that class.
    // void means that the method has no return value.
    // main is the name of the method.
    // String[] args is a parameter. It's a way to pass information to the method through the command line.
    public static void main(String[] args) {
        // System is a class. It contains methods and variables related to the system.
        // This class comes already in our namespace, so we don't need to import it.
        // out is a static variable (instance) within the System class. It represents the standard output stream.
        // println() is a method of the out object. It prints a line of text to the output stream.
        System.out.println("Hello World!");
    }
}