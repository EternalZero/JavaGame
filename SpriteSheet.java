import java.awt.image.BufferedImage;

//class contains a helper method that will
//go retrieve the loaded image


public class SpriteSheet{
	
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image){
		this.image = image;
	}
	
	public BufferedImage grabImage()
	{
		BufferedImage img = image;
		return img;
}
}