package game;

import java.awt.Color;
import java.awt.Graphics;

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

    public void checkCollision(Paddle paddle) {
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

                break;
            }
        }
    }

    public void checkWallCollision(int width, int height) {
        if (position.getX() <= 0 || position.getX() >= width - BALL_SIZE) {
            dx = -dx; 
        }
        if (position.getY() <= 0 || position.getY() >= height - BALL_SIZE) {
            dy = -dy; 
        }
    }

    public void draw(Graphics brush) {
        brush.setColor(ballColor);
        brush.fillOval((int) position.getX(), (int) position.getY(), BALL_SIZE, BALL_SIZE);
    }
}
