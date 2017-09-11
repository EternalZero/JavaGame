import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//mouse inputs

public class MouseInput implements MouseListener{
	@Override
	public void mouseEntered(MouseEvent arg0){
		
	}
	@Override
	public void mouseExited(MouseEvent arg0){
		
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(mx>= Game.WIDTH/4+120 && mx <= Game.WIDTH/4+220)
		{
			if(my >= 350 && my <= 400)
			{
					Game.State = Game.STATE.GAME;
			}
			
		}
		
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0){
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0){
		
	}
}