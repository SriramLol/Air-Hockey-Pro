package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;

class AirHockey extends Game implements KeyListener{
	static int counter = 0;
	private Paddle playerOnePaddle;
	private Paddle playerTwoPaddle;
	private Paddle.PaddleEffect paddleOneEffect;
	private Paddle.PaddleEffect paddleTwoEffect;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean rightPressed = false;
	private boolean leftPressed = false;
	private boolean WPressed = false;
	private boolean SPressed = false;
	private boolean DPressed = false;
	private boolean APressed = false;
	private boolean isInit = false;
	

  public AirHockey() {
    super("Air Hockey",800,600);
    this.setFocusable(true);
	this.requestFocus();
	addKeyListener(this);
	
  }
  
  private void initialize() {
	  if(!isInit) {
		  	Point[] paddleOneShapeArray = Paddle.createRect(20, 60);
			playerOnePaddle = new Paddle(paddleOneShapeArray, new Point(width - 100, height /2), 0);
			paddleOneEffect = playerOnePaddle.new PaddleEffect(500);
			
			Point[] paddleTwoShapeArray = Paddle.createRect(20, 60);
			playerTwoPaddle = new Paddle(paddleOneShapeArray, new Point(100, height /2), 0);
			paddleTwoEffect = playerOnePaddle.new PaddleEffect(500);
			
			isInit = true;
	  }
  }
  
	public void paint(Graphics brush) {
		initialize();
		
    	brush.setColor(Color.black);
    	brush.fillRect(0,0,width,height);
    	
    	updatePaddles();
    	
    	if(paddleOneEffect != null) {
    		paddleOneEffect.update();
    	}
    	if(paddleTwoEffect != null) {
    		paddleTwoEffect.update();
    	}
    	
    	if(playerOnePaddle != null) {
    		playerOnePaddle.draw(brush);
    	}
    	
    	if(playerTwoPaddle != null) {
    		playerTwoPaddle.draw(brush);
    	}
    	// sample code for printing message for debugging
    	// counter is incremented and this message printed
    	// each time the canvas is repainted
    	counter++;
    	brush.setColor(Color.white);
    	brush.drawString("Player One: Use arrow keys to move and period to rotate", 200, 20);
    	brush.drawString("Player Two: Use arrow keys to move and period to rotate", 200, 35);
  }
  
	public static void main (String[] args) {
   		AirHockey a = new AirHockey();
		a.repaint();
  }
	
	private void updatePaddles() {
		
		if(playerOnePaddle == null || playerTwoPaddle == null) {
			return;
		}
		
		if(upPressed) {
			playerOnePaddle.moveUp();
		}
		
		if(downPressed) {
			playerOnePaddle.moveDown();
		}
		
		if(rightPressed) {
			playerOnePaddle.moveRight();
		}
		
		if(leftPressed) {
			playerOnePaddle.moveLeft();
		}
		
		if(WPressed) {
			playerTwoPaddle.moveUp();
		}
		
		if(SPressed) {
			playerTwoPaddle.moveDown();
		}
		
		if(DPressed) {
			playerTwoPaddle.moveRight();
		}
		
		if(APressed) {
			playerTwoPaddle.moveLeft();
		}
	}

	//this method isn't used
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		
		case KeyEvent.VK_UP:
			upPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = true;
			break;
		case KeyEvent.VK_W:
			WPressed = true;
			break;
		case KeyEvent.VK_S:
			SPressed = true;
			break;
		case KeyEvent.VK_D:
			DPressed = true;
			break;
		case KeyEvent.VK_A:
			APressed = true;
			break;
		case KeyEvent.VK_PERIOD:
			playerOnePaddle.rotateClockwise();
			break;
		case KeyEvent.VK_E:
			playerTwoPaddle.rotateClockwise();
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		
		case KeyEvent.VK_UP:
			upPressed = false;;
			break;
		case KeyEvent.VK_DOWN:
			downPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			rightPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			leftPressed = false;
			break;
		case KeyEvent.VK_W:
			WPressed = false;;
			break;
		case KeyEvent.VK_S:
			SPressed = false;
			break;
		case KeyEvent.VK_D:
			DPressed = false;
			break;
		case KeyEvent.VK_A:
			APressed = false;
			break;
		
		}
	
	}
}