package game;

import java.awt.Color;

import java.awt.Graphics;

/**
 * Represents a player-controlled paddle in the Air Hockey game.
 * <p>
 * The Paddle class extends Polygon and provides movement, rotation,
 * and special effect functionality. Each player controls
 * a paddle to hit the ball.
 * </p>
 */


public class Paddle extends Polygon {
	
	/** All necessary instance variables for color and movement of the Paddle */
	private static final int MOVE_SPEED = 7;
	private static final int ROTATE_SPEED = 15;
	private Color fillColor = Color.WHITE;
	private Color borderColor;
	private double velocityX = 0;
	private double velocityY = 0;
	private double scale = 1.0;
	private boolean isMoving = false;
	
	private int gameWidth, gameHeight; 
	
	/**
	 * Constructs a new paddle.
	 * 
	 * @param inShape The array of points defining the paddle shape
	 * @param inPosition The initial position of the paddle
	 * @param inRotation The initial rotation of the paddle
	 * @param gameWidth
	 * @param gameHeight
	 * @param borderColor
	 */


	public Paddle(Point[] inShape, Point inPosition, double inRotation, int gameWidth, int gameHeight, Color borderColor) {
		super(inShape, inPosition, inRotation);
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.borderColor = borderColor;
	}
	
	/**
	 * Creates a rectangular shape for the paddle.
	 * 
	 * @param width
	 * @param height
	 * @return An array of Points defining a rectangle
	 */

	
	public static Point[] createRect(int width, int height) {
		Point[] shape = new Point[4];
		shape[0] = new Point(0, 0);
		shape[1] = new Point(width, 0);
		shape[2] = new Point(width, height);
		shape[3] = new Point(0, height);
		return shape;
	}
	
	/**
	 * Moves the paddle up, down, left, or right if not at the boundary.
	 * <p>
	 * Updates the paddle's vertical and horizontal velocity and position.
	 * </p>
	 */

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
	
	/**
	 * Stops the paddle's movement.
	 * <p>
	 * Sets velocities to zero and updates the movement flag.
	 * </p>
	 */


	public void stopMoving() {
		velocityX = 0;
		velocityY = 0;
		isMoving = false;
	}

	/**
	 * Rotates the paddle clockwise by the rotation speed amount.
	 */

	public void rotateClockwise() {
		rotate(ROTATE_SPEED);
	}
	
	/**
	 * Sets the fill color of the paddle.
	 * 
	 * @param color
	 */

	public void setFillColor(Color color) {
	    this.fillColor = color;
	}

	/**
	 * Sets the scale factor for the paddle size.
	 * 
	 * @param scale
	 */

	public void setScale(double scale) {
	    this.scale = scale;
	}

	/**
	 * Draws the paddle with its current fill color, border color, and scale.
	 * 
	 * @param brush The Graphics object
	 */

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
	
	/**
	 * Updates the paddle shape while preserving position and rotation.
	 * 
	 * @param newShape new array of Points defining the paddle shape
	 */

	public void updateShape(Point[] newShape) {
		
		Polygon newPolygon = new Polygon(newShape, this.position.clone(), this.rotation);
		
		Point[] points = newPolygon.getPoints();
		this.shape = new Point[points.length];
		for(int i = 0; i < points.length; i++) {
			this.shape[i] = points[i].clone();
		}
	}

	/**
	 * Checks if the paddle is currently in motion.
	 * 
	 * @return true if paddle is moving, else false
	 */

	// New methods for tracking movement
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * Gets the current horizontal velocity of the paddle.
	 * 
	 * @return The x-velocity
	 */

	public double getVelocityX() {
		return velocityX;
	}
	
	/**
	 * Gets the current vertical velocity of the paddle.
	 * 
	 * @return The y-velocity
	 */

	public double getVelocityY() {
		return velocityY;
	}

	/**
	 * Calculates the width of the paddle based on its points.
	 * 
	 * @return the width of the paddle
	 */

	public int getWidth() {
		return (int) (getPoints()[2].getX() - getPoints()[0].getX());
	}
	
	/**
	 * Calculates the height of the paddle based on its points.
	 * 
	 * @return the height of the paddle
	 */


	public int getHeight() {
		return (int) (getPoints()[2].getY() - getPoints()[0].getY());
	}

	/**
	 * Inner class that manages temporary special effects for paddles.
	 * <p>
	 * Effects have a duration and can change the paddles color .
	 * </p>
	 */

	public class PaddleEffect {
		
	    private int duration;
	    private int currentTime;
	    private boolean on = false;
	    
	    /**
	     * Constructs a new paddle effect with the specified duration.
	     * 
	     * @param duration
	     */

	    public PaddleEffect(int duration) {
	        this.duration = duration;        
	    }
	    
	    /**
	     * Checks if the effect is currently active.
	     * 
	     * @return true if effect is on, else false
	     */

	    public boolean isOn() {
	        return on;
	    }
	    
	    /**
	     * Activates the effect.
	     * <p>
	     * Sets the paddle color to yellow
	     * </p>
	     */

	    public void turnOn() {
	        on = true;
	        currentTime = 0;
	        fillColor = Color.YELLOW;
	    }
	    
	    /**
	     * Deactivates the effect.
	     * <p>
	     * Resets the paddle color to white
	     * </p>
	     */

	    private void turnOff() {
	        on = false;
	        fillColor = Color.WHITE;
	        scale = 1.0; 
	    }
	    
	    /**
	     * Updates the effect state.
	     * <p>
	     * Increments the effect timer and turns off the effect
	     * </p>
	     */

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
