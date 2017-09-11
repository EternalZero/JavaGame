import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.Random;

////Enemy class which is responsible for the updating of player (tick)
// as well as actually drawing the Enemy image
//getters and setters are also implemented to allow Game class to take action when needed(death/reset)


public class Enemy{
	
	private double x,y;
	
	private int speed;
	
	
	private BufferedImage enemy1;
	private BufferedImage enemy2;
	private BufferedImage enemy3;
	private BufferedImage enemy4;
	private BufferedImage enemy5;
	private BufferedImage enemy6;
	private BufferedImage enemy7;
	
	private int astro;
	
	private int width, scale;
	
	public Enemy (double x, double y, Game game, int astro ){
		
		this.astro = astro;	
		this.x = x;
		this.y = y;
		
		//the calling of the loaded asteriod images
		
		SpriteSheet ss = new SpriteSheet(game.getEnemy1());
		SpriteSheet ss2 = new SpriteSheet(game.getEnemy2());
		SpriteSheet ss3 = new SpriteSheet(game.getEnemy3());
		SpriteSheet ss4 = new SpriteSheet(game.getEnemy4());
		SpriteSheet ss5 = new SpriteSheet(game.getEnemy5());
		SpriteSheet ss6 = new SpriteSheet(game.getEnemy6());
		SpriteSheet ss7 = new SpriteSheet(game.getEnemy7());
		
		enemy1 = ss.grabImage();
		enemy2 = ss2.grabImage();
		enemy3 = ss3.grabImage();
		enemy4 = ss4.grabImage();
		enemy5 = ss5.grabImage();
		enemy6 = ss6.grabImage();
		enemy7 = ss7.grabImage();
		
		width = Game.WIDTH;
		scale = Game.SCALE;
		
		Random rn = new Random();
		this.x = rn.nextInt((width-50)-150);
		
		speed = rn.nextInt(6) + 1;
	}
	
	
	//updates the y position of asteriod by increments of the asteriods respective speed;
	public void tick(){
		y +=speed;
		
		//once the asteriod has hit Max height, loop back to top with random x positon and random speed
		if(y>(Game.HEIGHT)){
				y= 0;
			
		Random rn = new Random();
		x = rn.nextInt((width-50)-100);
		
		y = rn.nextInt(-10 + 1 + 30) - 30;
		
		speed = rn.nextInt(6) + 1;		
		}
	}
	
	//Depending on astro number of asteriod(random number between 1 and 7), the size of asteriod is decided here
	public void render(Graphics g, int astro){
		switch(astro){
				case 1: g.drawImage(enemy1, (int)x, (int)y, null);
						break;
						
				case 2: g.drawImage(enemy2, (int)x, (int)y, null);
						break;
						
				case 3: g.drawImage(enemy3,(int)x, (int)y, null);
						break;
						
				case 4: g.drawImage(enemy4,(int)x, (int)y, null);
						break;
				
				case 5: g.drawImage(enemy5,(int)x, (int)y, null);
						break;
						
				case 6: g.drawImage(enemy6,(int)x, (int)y, null);
						break;
						
				case 7: g.drawImage(enemy7,(int)x, (int)y, null);
						break;
						
				default: g.drawImage(enemy1,(int)x,(int)y, null);
			} 	
	}
	
	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}
	
	public void setX(int nx){
		x = nx;
	}
	
	public void setY(int ny){
		y = ny;
	}
	
	public int getAstro(){
		return astro;
	}
	
}