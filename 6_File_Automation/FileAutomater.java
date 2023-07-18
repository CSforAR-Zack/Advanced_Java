import java.io.*;
import java.util.zip.*;

public class FileAutomater{

    public void readFile(String fileName)  {
        // Will use a try catch block to handle any errors
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while(line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
    
    public void createFiles(int numberOfFiles) throws IOException {
        for (int i = 0; i < numberOfFiles; i++) {
            FileWriter fileWriter = new FileWriter("file" + i + ".txt");
            fileWriter.write("This is file " + i);
            fileWriter.close();
        }
    }

    public void deleteFiles(String extension){
        // Delete all files with the given extension
        // The "." represents the current directory
        File currentDirectory = new File(".");
        // Get all files in the current directory
        File[] files = currentDirectory.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(extension)) {
                file.delete();
            }
        }
    }

    public void zipFiles(String extension) throws IOException {
        // The "." represents the current directory
        File currentDirectory = new File(".");
        // Get all files in the current directory
        File[] files = currentDirectory.listFiles();


        // Loop through each file in the current directory
        for (File file : files) {
            if (file.getName().endsWith(extension)) {
                // Setup to read original file and write the new file
                FileInputStream fis = new FileInputStream(file);
                String fileNameBase = file.getName().split(extension)[0];       
                FileOutputStream fos = new FileOutputStream(fileNameBase + ".zip");

                // Setup to write the file
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);

                // Read the file and write it to the zip file working with 1024 bytes at a time
                // and until there are no more bytes to read
                byte[] bytes = new byte[1024];
                int length = fis.read(bytes);
                while(length >= 0) {
                    zipOut.write(bytes, 0, length);
                    length = fis.read(bytes);
                }
                zipOut.closeEntry();

                fis.close();
                fos.close();

                // Delete the original file
                file.delete();
            }
        }
    }

    public void unzipFiles() throws IOException{
        // The "." represents the current directory
        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles();

        for (File file : files) {
            if (file.getName().endsWith(".zip")) {
                FileInputStream fis = new FileInputStream(file);
                ZipInputStream zipIn = new ZipInputStream(fis);

                String fileNameBase = file.getName().split(".zip")[0];
                File unzippedFile = new File(fileNameBase + ".txt");

                ZipEntry zipEntry = zipIn.getNextEntry();
                while(zipEntry != null) {
                    FileOutputStream fos = new FileOutputStream(unzippedFile);
                    byte[] bytes = new byte[1024];
                    int length = zipIn.read(bytes);
                    while(length >= 0) {
                        fos.write(bytes, 0, length);
                        length = zipIn.read(bytes);
                    }
                    fos.close();
                    zipEntry = zipIn.getNextEntry();
                }
                zipIn.close();
                file.delete();
            }
        }
    }
}