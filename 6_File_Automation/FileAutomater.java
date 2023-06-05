import java.io.*;
import java.util.zip.*;

public class FileAutomater{
    int numberOfFiles = 1;
    String extension = ".txt";
    int delay = 2000;

    public FileAutomater(int numberOfFiles, String extension, int delay){
        this.numberOfFiles = numberOfFiles;
        this.extension = extension;
        this.delay = delay;
    }

    public void createFiles(int numberOfFiles) throws IOException {
        for (int i = 0; i < numberOfFiles; i++) {
            FileWriter fileWriter = new FileWriter("file" + i + ".txt");
            fileWriter.write("This is file " + i);
            fileWriter.close();
        }
    }

    public void deleteFiles(String extension){
        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(extension)) {
                file.delete();
            }
        }
    }

    public void zipFiles(String extension) throws IOException {
        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles();

        for (File file : files) {
            if (file.getName().endsWith(extension)) {  
                FileInputStream fis = new FileInputStream(file);
                String fileNameBase = file.getName().split(extension)[0];       
                FileOutputStream fos = new FileOutputStream(fileNameBase + ".zip");

                ZipOutputStream zipOut = new ZipOutputStream(fos);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length = fis.read(bytes);
                while(length >= 0) {
                    zipOut.write(bytes, 0, length);
                    length = fis.read(bytes);
                }
                zipOut.closeEntry();

                fis.close();
                fos.close();

                file.delete();
            }
        }
    }

    public void unzipFiles() throws IOException{
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
                    int length;
                    while((length = zipIn.read(bytes)) >= 0) {
                        fos.write(bytes, 0, length);
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