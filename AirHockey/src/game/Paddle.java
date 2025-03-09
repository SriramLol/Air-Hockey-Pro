package game;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle extends Polygon{
	
	private static final int MOVE_SPEED = 5;
	private static final int ROTATE_SPEED = 15;
	private Color fillColor = Color.WHITE;
	private Color borderColor = Color.BLUE;

	public Paddle(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
			
	}
	
	public static Point[] createRect(int width, int height) {
		Point[] shape = new Point[4];
		shape[0] = new Point(0,0);
		shape[1] = new Point(width, 0);
		shape[2] = new Point(width, height);
		shape[3] = new Point(0, height);
		return shape;
	}
	
	public void moveUp() {
		position.setY(position.getY() - MOVE_SPEED);
	}
	
	public void moveDown() {
		position.setY(position.getY() + MOVE_SPEED);
	}
	
	public void moveRight() {
		position.setX(position.getX() + MOVE_SPEED);
	}
	
	public void moveLeft() {
		position.setX(position.getX() - MOVE_SPEED);
	}
	
	public void rotateClockwise() {
		rotate(ROTATE_SPEED);
	}
	
	public void draw(Graphics brush) {
		Point[] points = getPoints();
		int[] xToColor = new int[points.length];
		int[] yToColor = new int[points.length];
		
		for(int i = 0; i < points.length; i++) {
			xToColor[i] = (int) points[i].getX();
			yToColor[i] = (int) points[i].getY();
		}
		
		brush.setColor(fillColor);
		brush.fillPolygon(xToColor, yToColor, points.length);
		
		brush.setColor(borderColor);
		brush.drawPolygon(xToColor, yToColor, points.length);
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
		}
		
		public void update() {
			if(on) {
				currentTime += 100;
				if(currentTime >= duration) {
					turnOff();
				}
			}
		}
		
		
	}
	

}
