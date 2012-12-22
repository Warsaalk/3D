package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import renderer.Camera;

public class UserEventMediator extends KeyAdapter {
	private Camera cam;
	
	public UserEventMediator(Camera cam){
		this.cam = cam;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e. getKeyChar () == 'z'){
			cam . forward ();
		} else if (e. getKeyChar () == 's'){
			cam . backward ();
		} else if (e.getKeyChar() == 'p'){
			cam . up();
		} else if (e.getKeyChar() == 'm'){
			cam . down();
		} else if (e.getKeyChar() == 'q'){
			cam . left();
		} else if (e.getKeyChar() == 'd'){
			cam . right();
		}
	}
	
	
}
