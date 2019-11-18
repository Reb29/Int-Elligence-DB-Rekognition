import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

public class ImageSplitter {

    private String imageFile;
    private BufferedImage[][] imagePieces;
    private static int imageSlices = 2;
    private String fileType;

    public ImageSplitter(String filePath) {
    this.imageFile = filePath;
        imagePieces = new BufferedImage[imageSlices][imageSlices];

        if (filePath.contains(".jpg")){
            fileType = ".jpg";
        } else if (filePath.contains(".png")){
            fileType = ".png";
        }
    }

    public ArrayList<String> split() throws IOException {
        ArrayList<String> filePaths = new ArrayList<>();

        BufferedImage image = ImageIO.read(new File(imageFile));
System.out.println(imageFile);

        int count = 1;

        int imageWidth = image.getWidth() / imageSlices;
        int imageHeight = image.getHeight() / imageSlices;

        for (int x = 0; x < imageSlices; x++) {
            for (int y = 0; y < imageSlices; y++) {

                imagePieces[x][y] = image.getSubimage(x * imageWidth, y * imageHeight,
                        imageWidth, imageHeight);

                ImageIO.write(imagePieces[x][y], fileType.replace(".", ""), new File(imageFile.replace(fileType,"") + count + fileType));

                filePaths.add(imageFile.replace(fileType,"") + count + fileType);
                count++;
            }

        }
        return filePaths;
    }
}