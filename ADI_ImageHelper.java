import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ADI_ImageHelper {
    static BufferedImage loadImage(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        }
        catch(IOException e) {
            // throw e;
        }
        return img;
    }

}
