import java.io.*;

public class Main {
    public static void main(String[] args){
        int numberOfFiles = 1;
        String extension = ".txt";
        int delay = 1000;
        
        FileAutomater automater = new FileAutomater(numberOfFiles, extension, delay);

        try{
            System.out.println("Creating Files...");
            automater.createFiles(numberOfFiles);

            System.out.println("Pausing...");
            pause(delay);

            System.out.println("Zipping Files...");
            automater.zipFiles(extension);

            System.out.println("Pausing...");
            pause(delay);

            System.out.println("Unzipping Files...");
            automater.unzipFiles();

            System.out.println("Pausing...");
            pause(delay);

            System.out.println("Deleting Files...");
            automater.deleteFiles(extension);

        } catch (IOException e) {
            System.out.println("Error...");
        }
    }    

    static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Error pausing");
        }
    }
}