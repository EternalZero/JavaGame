import java.lang.*;
import java.awt.Canvas;
import java.awt.Dimension;
 import java.awt.BufferCapabilities;
 import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.Color;




//Main Game class, this class will make the calls to load graphics(Enemy, Player, Menu).
//The master game loop can also be found here.


public class Game extends Canvas implements Runnable{
	
	////Menu class declaration, depending on the State (game or menu), will know which action to
	//present, either display menu(menu state), or display the game(game state)
	public static enum STATE{
		MENU,
		GAME
	};
	
	public static STATE State = STATE.MENU;
	private Menu menu;
	//////////END MENU DECLARATION
	
	//Fixing the size of the game screen
	public static final int WIDTH = 720;
	public static final int HEIGHT = 850;
	public static final int SCALE = 1;
	//
	
	public final String TITLE = "AstroField";
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	//Setting up the variables the will eventually load the image (not necessarily draw it though)
	private BufferedImage enemy1 = null;
	private BufferedImage enemy2 = null;
	private BufferedImage enemy3 = null;
	private BufferedImage enemy4 = null;
	private BufferedImage enemy5 = null;
	private BufferedImage enemy6 = null;
	private BufferedImage enemy7 = null;
	
	private BufferedImage Lives1 = null;
	private BufferedImage Lives2 = null;
	private BufferedImage Lives3 = null;
	
	private BufferedImage dead = null;
	
	//Enemy variables
	private Enemy e1;
	private Enemy e2;
	private Enemy e3;
	private Enemy e4;
	private Enemy e5;
	private Enemy e6;
	private Enemy e7;
	
	//variable that will eventually decide what kind of asteriod to load (big or small asteriod)
	private int astro1;
	private int astro2;
	private int astro3;
	private int astro4;
	private int astro5;
	private int astro6;
	private int astro7;
	
	private int Score;
	
	private int Lives = 3;
	
	private int LivesLoop = 0;
	
	private Player p;	
	
	
	//Initialzer functiion which "sets up" the images to be loaded
	//as well as initializing the locations of the elements of the game (asteriod, player) will 
	//be on the game
	
	public void init(){
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("/res/ship.png");
			enemy1 = loader.loadImage("/res/astro.png");
			enemy2 = loader.loadImage("/res/astro2.png");
			enemy3 = loader.loadImage("/res/astro3.png");
			enemy4 = loader.loadImage("/res/astro.png");
			enemy5 = loader.loadImage("/res/astro.png");
			enemy6 = loader.loadImage("/res/astro2.png");
			enemy7 = loader.loadImage("/res/astro2.png");
			
			dead = loader.loadImage("/res/ship2.png"); 
			
			Lives1 = loader.loadImage("/res/ship.png");
			Lives2 = loader.loadImage("/res/ship.png");
			Lives3 = loader.loadImage("/res/ship.png");
			
			background = loader.loadImage("/res/bg.png");
		}catch(IOException e){
			e.printStackTrace();
		}
	
	//initializing the listeners for keyboard(game purposes)  and mouse (menu purposes)	
	 addKeyListener(new KeyInput(this));
	 this.addMouseListener(new MouseInput());
	 
	 menu = new Menu();
	 
	 
	 p = new Player( 355,775,this);
	 
	Random rnd = new Random();
	
	//////Choosing which asteriod to be as (big or small)  at random)
	astro1 = rnd.nextInt(7) + 1;
	 e1 = new Enemy(10,-5, this, astro1);
	 
	 astro2 = rnd.nextInt(7) + 1;
	  e2 = new Enemy(210, -5, this, astro2);
	  
	  astro3 = rnd.nextInt(7) + 1;
	 e3= new Enemy(410, -5, this, astro3);
	 
	 astro4 = rnd.nextInt(7) +1;
	 e4 = new Enemy(510, -5, this, astro4);
	 
	 astro5 = rnd.nextInt(7) +1;
	 e5 = new Enemy(510, -5, this, astro5);
	 
	 astro6 = rnd.nextInt(7) +1;
	 e6 = new Enemy(510, -5, this, astro6);
	 
	 astro7 = rnd.nextInt(7) +1;
	 e7 = new Enemy(510, -5, this, astro7);
	
	}
	
	
	
	
	
	//starts the master loop;
	private synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	//changes state of the master loop from running to stop
	private synchronized void stop(){
		if(!running)
			return;
		
		running = false;
		try{
		thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	
	
	//This is the master loop, that calls the update function as well as the actual drawing
	//of the graphics
	
	public void run(){
			init();
			//this is game loop
			long lastTime = System.nanoTime();
			final double amountOfTicks = 60.0;
			double ns = 1000000000/amountOfTicks;
			double delta = 0;
			
			int updates = 0;
			int frames = 0;
			long timer = System.currentTimeMillis();
			
			while(running){
				long now = System.nanoTime();
				delta += (now - lastTime)/ns;
				lastTime = now;
				if(delta >= 1){
					tick();
					delta--;
					updates++;
					
				}
				render();
				frames++;
				
				if(System.currentTimeMillis() - timer>1000){
					timer+= 1000;
					//System.out.println(updates + " Ticks, FPS " + frames);
					updates = 0;
					frames = 0;
					if(Lives != -1 && State == STATE.GAME)
					Score += 100;
				}
				
			}
			stop();	
	}
	
	
	//the Tick method is used to call the objects respective tick method (located in objects own class)
	//which is responisble for updating the movement and and location of the object
	//in other words and "update" function, checks for collison before updating though
	private void tick(){
		
		if(State == STATE.GAME){
		
		checkCollision(p,e1);
		checkCollision(p,e2);
		checkCollision(p,e3);
		checkCollision(p,e4);
		checkCollision(p,e5);
		checkCollision(p,e6);
		checkCollision(p,e7);
		
		p.tick();
		e1.tick();
		e2.tick();
		e3.tick(); 
		e4.tick();
		e5.tick();
		e6.tick();
		e7.tick();
		}
		
	}
	
	
	//method responsible for the calling of the functions as well as actually drawing
	//and also keeps track of the current state of the game, deciding what action program should taken
	// (show menu, or continue with game)
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3); //the number of loaded images to have on standby
			return;
			
		}
		
		Graphics g = bs.getDrawGraphics();
		////////////TO DRAW//////////////////////
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(background, 0,0,null);
		
		if(State == STATE.GAME){
		//IF GAME IS OVER
		if(Lives <= -1){
			
			if(LivesLoop > 500) //buys time to draw game over screen and score before displaying menu
		{
			Lives = 3;
			Score = 0;
			State = STATE.MENU;
			LivesLoop = 0;
			resetE(e1,e2,e3,e4,e5,e6,e7);
			
	
		}
			
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.yellow);
		
		String score = Integer.toString(Score);
		g.drawString("GamerOver", Game.WIDTH /4, 300);
		g.drawString(("Score: "+score),Game.WIDTH-300, 100);	
		g.drawImage(dead, 100, 250, null);
		
		
		LivesLoop +=1;
		
		
		}
		////////////////END GAME OVER PART/////////////
		
		///////////////Game in Progress part/////////////
		else{
			if(Lives == 0){
				Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.yellow);
		
		String score = Integer.toString(Score);
		g.drawString(("Score: "+score),Game.WIDTH-300, 100);
			}
			
			
		if(Lives == 1){
			Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.yellow);
			String score = Integer.toString(Score);
			g.drawImage(Lives1, 5, 10, null);
			g.drawString(("Score: "+score),Game.WIDTH-300, 100);
			
		}
		else if(Lives ==2){
			Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.yellow);
			String score = Integer.toString(Score);
			g.drawImage(Lives1, 75, 10, null);
			g.drawImage(Lives2, 145, 10, null);
			g.drawString(("Score: "+score),Game.WIDTH-300, 100);
		}
		else if(Lives ==3){
			Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.yellow);
			String score = Integer.toString(Score);
			g.drawImage(Lives1, 5, 10, null);
			g.drawImage(Lives2, 75, 10, null);
			g.drawImage(Lives3, 145, 10, null);
			g.drawString(("Score: "+score),Game.WIDTH-300, 100);
		}
		
		p.render(g);
	
		e1.render(g, astro1);
		e2.render(g, astro2);
		e3.render(g, astro3);
		e4.render(g, astro4);
		e5.render(g, astro5);
		e6.render(g, astro6);
		e7.render(g, astro7);
		}
		////////END GAME IN PROGRESS PART////////////
		
		}
		
		else if(State == STATE.MENU){ //If menu state, just display menu
			menu.render(g);
		
		}
		////////End Menu state////////////////
		////////////END TO DRAW////////////////
		g.dispose();
		bs.show();
	}
	
	
	//Move when key is pressed
	public void keyPressed(KeyEvent e){	
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			p.setVelX(6);
		} else if(key == KeyEvent.VK_LEFT){
			p.setVelX(-6);
		} else if(key == KeyEvent.VK_UP){
			p.setVelY(-6);
		} else if(key == KeyEvent.VK_DOWN){
			p.setVelY(6);
		}
	}
	
	//STOP movement once pressed key is released
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			p.setVelX(0);
		} else if(key == KeyEvent.VK_LEFT){
			p.setVelX(0);
		} else if(key == KeyEvent.VK_UP){
			p.setVelY(0);
		} else if(key == KeyEvent.VK_DOWN){
			p.setVelY(0);
		}
	}
	
	
	//main program that sets the Frames to be displayed as well as acutally start the game)
	public static void main(String args[]){
		Game game =new Game();
		
		game.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
		
		game.start();
	}	
	
	
	
	//Functions that retrieve the loaded images used by other functions/classes that will render(actually draw out) the loaded image
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	public BufferedImage getEnemy1(){
		return enemy1;
	}
	
	
	 public BufferedImage getEnemy2(){
		return enemy2;
	}
	
	
	public BufferedImage getEnemy3(){
		return enemy3;
	}
	
	
	public BufferedImage getEnemy4(){
		return enemy4;
	} 
	
	public BufferedImage getEnemy5(){
		return enemy5;
	} 
	
	public BufferedImage getEnemy6(){
		return enemy6;
	}
	
	public BufferedImage getEnemy7(){
		return enemy7;
	} 
	

	
	//Function to check if there is a collision between Player and asteriod.
	// finds the middle of object and checks if its postion at any point intersects with player
	//size of object is taken into account 
	public void checkCollision(Player plyer,Enemy enmy){
		
		int siz = enmy.getAstro();
		
		if(siz == 1 || siz == 4|| siz ==5)//the astro numbers for big asteriods since they have a larger area)
		{
			if(Math.abs(plyer.getY()-(enmy.getY()+90)) <=75 && Math.abs(plyer.getX()- (enmy.getX()+130)) <=100)
			{
				Lives --;
				plyer.setX(355);
				plyer.setY(755);
				resetE(e1,e2,e3,e4,e5,e6,e7);	
			}
		}
		
		//small asteriods check smaller area
		else{	
			if(Math.abs(plyer.getY()-enmy.getY()) <=42 && Math.abs(plyer.getX()- enmy.getX()) <=42)
			{
				Lives--;
				plyer.setX(355);
				plyer.setY(755);
				resetE(e1,e2,e3,e4,e5,e6,e7);
			}
		}
	}
	
	
	//Resets the Location of the asteriods after either player crashes into asteriod, or when game is overand restarted)
	public void resetE(Enemy one,Enemy two,Enemy three,Enemy four,Enemy five,Enemy six, Enemy seven){
		one.setY(0);
		two.setY(0);
		three.setY(0);
		four.setY(0);
		five.setY(0);
		six.setY(0);
		seven.setY(0);
		
	}

	
	
}