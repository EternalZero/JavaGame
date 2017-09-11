import java.awt.Graphics;
import java.awt.image.BufferedImage;

//Player class which is responsible for the updating of player (tick)
// as well as actually drawing the player image
//getters and setters are also implemented to allow Game class to take action when needed(death/reset)

public class Player{
	
	private double x;
	private double y;
	
	private double velX = 0;
	private double velY =0;
	
	private BufferedImage player;
	
	
	public Player(double x, double y, Game game){
		
		this.x = x;
		this.y = y;	
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		player = ss.grabImage();
		
	}
	
	public void tick(){
		x+=velX;
		y+= velY;
		
		if(x<= -2)
			x= -2;
		
		if(x>= Game.WIDTH-52)
			x = Game.WIDTH-52;
		
		if(y<= 0)
			y= 0;
		
		if(y>= Game.HEIGHT-64)
			y = Game.HEIGHT-64;
	}
	
	
	
	public void render(Graphics g){
		
		g.drawImage(player, (int)x, (int)y, null);
	}
	
	
	
	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setVelX(double velX){
		this.velX = velX;
	}
	
	public void setVelY(double velY){
		this.velY = velY;
	}
	
}

