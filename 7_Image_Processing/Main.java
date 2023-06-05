import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class Main {
    public static void main(String[] args) {
        // Convert PNG to JPG
        // ConvertPNGToJPG("lotus.png", "lotusOUT.jpg");

        // Convert image to grayscale
        // ConvertToGrayscale("bfly.jpg", "bflyOut.jpg");

        // Convert image to negative
        // ConvertToNegative("bfly.jpg", "bflyOut.jpg");

        // Flip image
        // FlipImageHorizontal("bfly.jpg", "bflyOut.jpg");

        // Mirror image
        // MirrorImageYAxis("bfly.jpg", "bflyOut.jpg");

        // Brigtens image
        // BrightenImage("bfly.jpg", "bflyOut.jpg", -100);

        // Rotate image
        RotateImageRight("bfly.jpg", "bflyOut.jpg");
    }

    static void ConvertPNGToJPG(String inputFileName, String outputFileName) {
        // PNG to JPG
        // However, the image will be saved as a JPG but it will still be in ARGB format
        // Most image viewers will ignore the alpha value and just display the image
        // We could remove the alpha layer to make it a true JPG
        try {
            File inputFile = new File(inputFileName);
            BufferedImage inputImage = ImageIO.read(inputFile);

            File outputFile = new File(outputFileName);
            ImageIO.write(inputImage, "png", outputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    static void ConvertToGrayscale(String inputFileName, String outputFileName){
        try {
            File inputFile = new File(inputFileName);
            BufferedImage inputImage = ImageIO.read(inputFile);

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Convert to grayscale
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    
                    // get pixel value
                    int pixel = inputImage.getRGB(x, y);

                    // get red, green, blue values
                    // We shift since the pixel value is stored in the form of ARGB
                    // We don't need the alpha value
                    // We >> 16 to get the red value, >> 8 to get the green value, and blue is at the end
                    // We & 0xff mask and get the last 8 bits since 8 bits = 1 byte = 1 color value (0-255)
                    // So we are shifting the bits to the right by 16, 8, and 0 and then grabbing the last 8 bits
                    int red = pixel >> 16 & 0xff;
                    int green = pixel >> 8 & 0xff;
                    int blue = pixel & 0xff;

                    int gray = (red + green + blue) / 3;

                    pixel = gray << 16 | gray << 8 | gray;

                    outputImage.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File(outputFileName);
            ImageIO.write(outputImage, "jpg", outputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    static void ConvertToNegative(String inputFileName, String outputFileName) {
        try {
            File inputFile = new File(inputFileName);
            BufferedImage inputImage = ImageIO.read(inputFile);

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Convert to grayscale
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    
                    // get pixel value
                    int pixel = inputImage.getRGB(x, y);

                    // The negative is when we subtract the color value from 255
                    int red = 255 - pixel >> 16 & 0xff;
                    int green = 255 - pixel >> 8 & 0xff;
                    int blue = 255 - pixel & 0xff;

                    pixel = red << 16 | green << 8 | blue;

                    outputImage.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File(outputFileName);
            ImageIO.write(outputImage, "jpg", outputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    static void FlipImageHorizontal(String inputFileName, String outputFileName) {
        try {
            File inputFile = new File(inputFileName);
            BufferedImage inputImage = ImageIO.read(inputFile);

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Flip pixels on x-axis
            for (int y = 0; y < height; y++) {
                for (int x = width - 1; x >= 0; x--) {
                    
                    // get pixel value
                    int pixel = inputImage.getRGB(x, y);

                    outputImage.setRGB(width - x - 1, y, pixel);
                }
            }

            File outputFile = new File(outputFileName);
            ImageIO.write(outputImage, "jpg", outputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    static void MirrorImageYAxis(String inputFileName, String outputFileName) {
        try {
            File inputFile = new File(inputFileName);
            BufferedImage inputImage = ImageIO.read(inputFile);

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Mirror pixels accross y-axis
            // Get the middle so we know where to stop mirroring
            // We copy the pixel over to the left side and then copy it over to the right side
            int middle = width / 2;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < middle; x++) {
                    
                    // get pixel value
                    int pixel = inputImage.getRGB(x, y);

                    if (x < middle) {
                        outputImage.setRGB(x, y, pixel);
                        outputImage.setRGB(width - x - 1, y, pixel);
                    } else {
                        outputImage.setRGB(x, y, pixel);
                    }
                }                
            }

            File outputFile = new File(outputFileName);
            ImageIO.write(outputImage, "jpg", outputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    static void BrightenImage(String inputFileName, String outputFileName, int change) {
        try {
            File inputFile = new File(inputFileName);
            BufferedImage inputImage = ImageIO.read(inputFile);

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Convert to grayscale
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    
                    // get pixel value
                    int pixel = inputImage.getRGB(x, y);

                    // Add the change value to each color value
                    // If the color value is greater than 255, set it to 255
                    // If the color value is less than 0, set it to 0
                    // Could make a function to do this
                    int red = (pixel >> 16 & 0xff) + change;
                    if (red > 255) { red = 255;}
                    else if (red < 0) { red = 0; }
                    
                    int green = (pixel >> 8 & 0xff) + change;
                    if (green > 255) { green = 255;}
                    else if (green < 0) { green = 0; }

                    int blue = (pixel & 0xff) + change;
                    if (blue > 255) { blue = 255;}
                    else if (blue < 0) { blue = 0; }

                    pixel = red << 16 | green << 8 | blue;

                    outputImage.setRGB(x, y, pixel);
                }
            }

            File outputFile = new File(outputFileName);
            ImageIO.write(outputImage, "jpg", outputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    static void RotateImageRight(String inputFileName, String outputFileName) {
        try {
            File inputFile = new File(inputFileName);
            BufferedImage inputImage = ImageIO.read(inputFile);

            // Reverse the width and height because we are rotating the image
            int inputWidth = inputImage.getWidth();
            int inputHeight = inputImage.getHeight();
            int outputWidth = inputHeight;
            int outputHeight = inputWidth;

            BufferedImage outputImage = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_RGB);

            // Convert to grayscale
            for (int y = 0; y < inputHeight; y++) {
                for (int x = 0; x < inputWidth; x++) {
                    
                    // get pixel value
                    int pixel = inputImage.getRGB(x, y);

                    int newX = outputWidth - y - 1;
                    int newY = x;

                    outputImage.setRGB(newX, newY, pixel);
                }
            }

            File outputFile = new File(outputFileName);
            ImageIO.write(outputImage, "jpg", outputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}