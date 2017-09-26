import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

//drawing and design of menu

public class Menu{
	public Rectangle playButton = new Rectangle(Game.WIDTH/4+120, 350, 100, 50);

	public void render(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("Asteroid Runner", Game.WIDTH /4, 300);
		
		
		Font fnt1 = new Font("arial", Font.BOLD,30);
		g.setFont(fnt1);
		
		g.drawString("Play", playButton.x+19, playButton.y+35);
		g2d.draw(playButton);
		
	}	
}