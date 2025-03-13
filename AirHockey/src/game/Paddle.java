package game;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle extends Polygon {
	
	private static final int MOVE_SPEED = 7;
	private static final int ROTATE_SPEED = 15;
	private Color fillColor = Color.WHITE;
	private Color borderColor;
	private double velocityX = 0;
	private double velocityY = 0;
	private double scale = 1.0;
	private boolean isMoving = false;
	
	private int gameWidth, gameHeight; 

	public Paddle(Point[] inShape, Point inPosition, double inRotation, int gameWidth, int gameHeight, Color borderColor) {
		super(inShape, inPosition, inRotation);
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.borderColor = borderColor;
	}
	
	public static Point[] createRect(int width, int height) {
		Point[] shape = new Point[4];
		shape[0] = new Point(0, 0);
		shape[1] = new Point(width, 0);
		shape[2] = new Point(width, height);
		shape[3] = new Point(0, height);
		return shape;
	}
	
	public void moveUp() {
		if (position.getY() > 0) { 
			position.setY(position.getY() - MOVE_SPEED);
			velocityY = -MOVE_SPEED;
			isMoving = true;
		} else {
			stopMoving();
		}
	}

	public void moveDown() {
		if (position.getY() < gameHeight - getHeight()) {
			position.setY(position.getY() + MOVE_SPEED);
			velocityY = MOVE_SPEED;
			isMoving = true;
		} else {
			stopMoving();
		}
	}

	public void moveRight() {
		if (position.getX() < gameWidth - getWidth()) {
			position.setX(position.getX() + MOVE_SPEED);
			velocityX = MOVE_SPEED;
			isMoving = true;
		} else {
			stopMoving();
		}
	}

	public void moveLeft() {
		if (position.getX() > 0) { 
			position.setX(position.getX() - MOVE_SPEED);
			velocityX = -MOVE_SPEED;
			isMoving = true;
		} else {
			stopMoving();
		}
	}

	public void stopMoving() {
		velocityX = 0;
		velocityY = 0;
		isMoving = false;
	}

	public void rotateClockwise() {
		rotate(ROTATE_SPEED);
	}
	
	public void setFillColor(Color color) {
	    this.fillColor = color;
	}

	public void setScale(double scale) {
	    this.scale = scale;
	}

	public void draw(Graphics brush) {
	    Point[] points = getPoints();
	    int[] xToColor = new int[points.length];
	    int[] yToColor = new int[points.length];
	    
	    double centerX = position.getX();
	    double centerY = position.getY();
	    
	    for (int i = 0; i < points.length; i++) {
	        // Apply scaling here
	        double dx = (points[i].getX() - centerX) * scale;
	        double dy = (points[i].getY() - centerY) * scale;
	        
	        // Use scaled coordinates
	        xToColor[i] = (int) (centerX + dx);
	        yToColor[i] = (int) (centerY + dy);
	    }
	    
	    brush.setColor(fillColor);
	    brush.fillPolygon(xToColor, yToColor, points.length);
	    
	    brush.setColor(borderColor);
	    brush.drawPolygon(xToColor, yToColor, points.length);
	}
	
	public void updateShape(Point[] newShape) {
		/*Creates a new Polygon with the new shape 
		size but preserves position and rotation
		*/
		
		Polygon newPolygon = new Polygon(newShape, this.position.clone(), this.rotation);
		
		Point[] points = newPolygon.getPoints();
		this.shape = new Point[points.length];
		for(int i = 0; i < points.length; i++) {
			this.shape[i] = points[i].clone();
		}
	}

	// New methods for tracking movement
	public boolean isMoving() {
		return isMoving;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public int getWidth() {
		return (int) (getPoints()[2].getX() - getPoints()[0].getX());
	}

	public int getHeight() {
		return (int) (getPoints()[2].getY() - getPoints()[0].getY());
	}

	public class PaddleEffect {
	    private int duration;
	    private int currentTime;
	    private boolean on = false;
	    
	    public PaddleEffect(int duration) {
	        this.duration = duration;        
	    }
	    
	    public boolean isOn() {
	        return on;
	    }
	    
	    public void turnOn() {
	        on = true;
	        currentTime = 0;
	        fillColor = Color.YELLOW;
	    }
	    
	    private void turnOff() {
	        on = false;
	        fillColor = Color.WHITE;
	        scale = 1.0; 
	    }
	    
	    public void update() {
	        if (on) {
	            currentTime += 1;
	            if (currentTime >= duration) {
	                turnOff();
	            }
	        }
	    }
	}
}
