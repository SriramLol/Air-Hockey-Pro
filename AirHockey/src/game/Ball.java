package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Ball extends Polygon {
    private static final int BALL_SIZE = 20;
    private static final int SPEED = 5;
    private double dx;
    private double dy;
    private Color ballColor = Color.RED;

    public Ball(Point position, double dx, double dy) {
        super(createCircle(BALL_SIZE), position, 0);
        this.dx = dx;
        this.dy = dy;
    }

    private static Point[] createCircle(int size) {
        return new Point[]{
            new Point(-size / 2, -size / 2),
            new Point(size / 2, -size / 2),
            new Point(size / 2, size / 2),
            new Point(-size / 2, size / 2)
        };
    }

    public void move() {
        position.setX(position.getX() + dx);
        position.setY(position.getY() + dy);
    }

    public boolean checkCollision(Paddle paddle) {
        Point[] ballPoints = getPoints();
        for (Point p : ballPoints) {
            if (paddle.contains(p)) {
             
                Point paddleCenter = paddle.position;
                double ballCenterX = position.getX();
                double ballCenterY = position.getY();

              
                double angle = Math.atan2(ballCenterY - paddleCenter.getY(), ballCenterX - paddleCenter.getX());

               
                double speed = Math.sqrt(dx * dx + dy * dy);
                dx = speed * Math.cos(angle);
                dy = speed * Math.sin(angle);

                if (paddle.isMoving()) {
                    dx += paddle.getVelocityX() * 0.3;
                    dy += paddle.getVelocityY() * 0.3;
                }

                return true;
            }
        }
        
        return false;
    }

    public void checkWallCollision(int width, int height) {
        
        if (position.getY() <= 0 || position.getY() >= height - BALL_SIZE) {
            dy = -dy; 
        }
        
        if (position.getX() <= 0) {
            int GOAL_HEIGHT = 120;
            int goalY = height/2 - GOAL_HEIGHT/2;
            
            if (position.getY() < goalY || position.getY() > goalY + GOAL_HEIGHT) {
                dx = -dx;
                position.setX(1);
            }
        }
        
        if (position.getX() >= width - BALL_SIZE) {
            int GOAL_HEIGHT = 120;
            int GOAL_WIDTH = 20;
            int goalY = height/2 - GOAL_HEIGHT/2;
            
            if (position.getY() < goalY || position.getY() > goalY + GOAL_HEIGHT) {
                dx = -dx;
                position.setX(width - BALL_SIZE - 1);
            }
        }
        
        if (position.getX() < -BALL_SIZE || position.getX() > width + BALL_SIZE) {
            position.setX(width / 2);
            position.setY(height / 2);
            dx = -dx / 2; 
        }
    }
    
    public boolean checkGoalCollision(Rectangle goal) {
        Rectangle ballRect = new Rectangle((int)position.getX(), (int)position.getY(), BALL_SIZE, BALL_SIZE);
        return ballRect.intersects(goal);
    }
    

    public double getDx() {
        return dx;
    }
    
    public double getDy() {
        return dy;
    }
    
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    public void draw(Graphics brush) {
        brush.setColor(ballColor);
        brush.fillOval((int) position.getX(), (int) position.getY(), BALL_SIZE, BALL_SIZE);
    }
}
