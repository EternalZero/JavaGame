import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;



//Helper class that goes and loads the image, to have it ready to draw(image on standby)

public class BufferedImageLoader{
	
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) throws IOException{
		image = ImageIO.read(getClass().getResource(path));
		return image;
		
	}
}