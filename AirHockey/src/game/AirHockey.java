package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * The {@code AirHockey} class represents an air hockey game.
 * It extends {@code Game} and implements {@code KeyListener} to handle user input.
 * The game includes player paddles, a ball, goals, and power-ups.
 */

class AirHockey extends Game implements KeyListener {
	static int counter = 0;
	private Paddle playerOnePaddle;
	private Paddle playerTwoPaddle;
	private Paddle.PaddleEffect paddleOneEffect;
	private Paddle.PaddleEffect paddleTwoEffect;
	private Ball ball;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean rightPressed = false;
	private boolean leftPressed = false;
	private boolean WPressed = false;
	private boolean SPressed = false;
	private boolean DPressed = false;
	private boolean APressed = false;
	private boolean isInit = false;
	private PowerUpItem currentPowerUp;
	private Random random = new Random();
	private int powerUpTimer = 0;
	private int powerUpInterval = 300;
	private int playerOneScore = 0;
	private int playerTwoScore = 0;
	private Rectangle leftGoal;
	private Rectangle rightGoal;
	private final int GOAL_WIDTH = 20;
	private final int GOAL_HEIGHT = 120;
	private int paddleOneFlashTimer = 0;
	private int paddleTwoFlashTimer = 0;
	private final int FLASH_DURATION = 15;


	/**
     * Interface for handling ball and paddle collision events.
     */
	
	private interface CollisionListener{
		void onCollision(Ball ball, Paddle paddle);
	}
	
	/**
     * Handles ball collision events, modifying ball speed and paddle effects.
     */
	
	private CollisionListener collisionListener = new CollisionListener() {
		@Override
		public void onCollision(Ball ball, Paddle paddle) {
			
			paddle.setFillColor(Color.ORANGE);
			
			if(paddle == playerOnePaddle) {
				paddleOneFlashTimer = FLASH_DURATION;
			}else if(paddle == playerTwoPaddle) {
				paddleTwoFlashTimer = FLASH_DURATION;
			}
			
			double dx = ball.getDx();
			double dy = ball.getDy();
			double speed = Math.sqrt(dx * dx + dy * dy);
			
			if(speed < 10) {
				ball.setVelocity(dx * 1.1, dy * 1.1);
			}
		}
	};

	/**
     * Constructs an {@code AirHockey} game with a predefined title and dimensions.
     * It initializes the game window and sets up key listening.
     */
	
	public AirHockey() {
		super("Air Hockey", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		addKeyListener(this);
		this.random = new Random();
	}

	/**
     * Initializes the game objects such as paddles, ball, and goals if they are not already initialized.
     */
	
	private void initialize() {
		if (!isInit) {
			Point[] paddleOneShapeArray = Paddle.createRect(20, 60);
			playerOnePaddle = new Paddle(paddleOneShapeArray, new Point(width - 100, height / 2), 0, width, height, Color.RED);
			paddleOneEffect = playerOnePaddle.new PaddleEffect(300);

			Point[] paddleTwoShapeArray = Paddle.createRect(20, 60);
			playerTwoPaddle = new Paddle(paddleTwoShapeArray, new Point(100, height / 2), 0, width, height, Color.BLUE);
			paddleTwoEffect = playerTwoPaddle.new PaddleEffect(300);

			ball = new Ball(new Point(width / 2, height / 2), 4, 4);
			leftGoal = new Rectangle(0, height / 2 - GOAL_HEIGHT / 2, GOAL_WIDTH, GOAL_HEIGHT);
			rightGoal = new Rectangle(width - GOAL_WIDTH, height / 2 - GOAL_HEIGHT / 2, GOAL_WIDTH, GOAL_HEIGHT);

			if (random == null) {
				random = new Random();
			}

			isInit = true;
		}
	}

	/**
     * Paints the game elements onto the screen.
     *
     * @param brush the {@code Graphics} object used to render elements.
     */
	
	public void paint(Graphics brush) {
		initialize();

		// Background
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		// Convert to Graphics2D for better drawing
		Graphics2D g2d = (Graphics2D) brush;
		g2d.setStroke(new BasicStroke(5)); // Set thicker stroke

		// Draw neon green border with rounded edges
		g2d.setColor(Color.GREEN);
		g2d.drawRoundRect(5, 5, width - 10, height - 10, 50, 50); // Rounded corners

		// Draw center line
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(width / 2, 5, width / 2, height - 5);

		// Draw face-off circle
		g2d.setColor(Color.RED);
		g2d.drawOval(width / 2 - 50, height / 2 - 50, 100, 100); // Center circle

		// Draw goals
		g2d.setColor(Color.BLUE);
		g2d.fillRect(leftGoal.x, leftGoal.y, leftGoal.width, leftGoal.height);
		g2d.setColor(Color.RED);
		g2d.fillRect(rightGoal.x, rightGoal.y, rightGoal.width, rightGoal.height);

		// Update power-ups
		updatePowerUps();

		// Update paddles and ball
		updatePaddles();
		ball.move();
		
		updateFlashTimers();

		ball.checkWallCollision(width, height);

		checkGoals();
		
		if(ball.checkCollision(playerOnePaddle)) {
			collisionListener.onCollision(ball, playerOnePaddle);
		}
		
		if(ball.checkCollision(playerTwoPaddle)) {
			collisionListener.onCollision(ball, playerTwoPaddle);
		}

		if (currentPowerUp != null) {
			currentPowerUp.checkCollision(playerOnePaddle, playerTwoPaddle);
			if (currentPowerUp != null) {
				currentPowerUp.draw(brush);
			}
		}

		if (paddleOneEffect != null) {
			paddleOneEffect.update();
		}
		if (paddleTwoEffect != null) {
			paddleTwoEffect.update();
		}

		if (playerOnePaddle != null) {
			playerOnePaddle.draw(brush);
		}

		if (playerTwoPaddle != null) {
			playerTwoPaddle.draw(brush);
		}

		if (ball != null) {
			ball.draw(brush);
		}

		// Debug information
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Player One: Use arrow keys to move and period to rotate", 250, 25);
		brush.drawString("Player Two: Use W, A, S, D to move and E to rotate", 250, 40);

		// Draw out the score
		Font scoreFont = new Font("Arial", Font.BOLD, 36);
		brush.setFont(scoreFont);
		brush.drawString(String.valueOf(playerTwoScore), width / 4, 50);
		brush.drawString(String.valueOf(playerOneScore), 3 * width / 4, 50);
		brush.setFont(new Font("Arial", Font.PLAIN, 12));
	}

	private void checkGoals() {
		double ballX = ball.position.getX();
		double ballY = ball.position.getY();

		if (leftGoal.contains(ballX, ballY)) {
			playerOneScore++;
			resetBall(true);
		}
		if (rightGoal.contains(ballX, ballY)) {
			playerTwoScore++;
			resetBall(false);
		}
	}
	
	private void updateFlashTimers() {
		if(paddleOneFlashTimer > 0) {
			paddleOneFlashTimer--;
			if(paddleOneFlashTimer == 0 && !paddleOneEffect.isOn()) {
				playerOnePaddle.setFillColor(Color.WHITE);
			}
		}
		
		if(paddleTwoFlashTimer > 0) {
			paddleTwoFlashTimer--;
			if(paddleTwoFlashTimer == 0 && !paddleTwoEffect.isOn()) {
				playerTwoPaddle.setFillColor(Color.WHITE);
			}
		}
		
		
	}

	private void resetBall(boolean leftToRight) {
		double dx = leftToRight ? 2 : -2;
		ball = new Ball(new Point(width / 2, height / 2), dx, 0);
	}

	// Lambda expressions for different power-up effects
	private PowerUp sizePowerUp = paddle -> {

		double originalX = paddle.position.getX();
		double originalY = paddle.position.getY();

		if (paddle == playerOnePaddle) {
			paddleOneEffect.turnOn();
		} else {
			paddleTwoEffect.turnOn();
		}

		paddle.setFillColor(Color.YELLOW);

		paddle.setScale(1.5);
	};

	private void updatePowerUps() {
		powerUpTimer++;

		if (powerUpTimer >= powerUpInterval && currentPowerUp == null) {
			int powerX = random.nextInt(width - 100) + 50;
			int powerY = random.nextInt(height - 100) + 50;
			currentPowerUp = new PowerUpItem(new Point(powerX, powerY), sizePowerUp);
			powerUpTimer = 0;
		}
	}

	private class PowerUpItem {
		private Point position;
		private final int SIZE = 20;
		private PowerUp effect;
		private Color color = Color.CYAN;

		public PowerUpItem(Point position, PowerUp effect) {
			this.position = position;
			this.effect = effect;
			if (this.effect == null) {
				this.effect = paddle -> {
					paddle.setFillColor(Color.YELLOW);
				};
			}
		}

		private void draw(Graphics brush) {
			brush.setColor(color);
			brush.fillOval((int) position.getX() - SIZE / 2, (int) position.getY() - SIZE / 2, SIZE, SIZE);

			brush.setColor(Color.BLACK);
			brush.drawString("P", (int) position.getX() - 3, (int) position.getY() + 5);
		}

		public void checkCollision(Paddle one, Paddle two) {
			Point checkPoint = new Point(position.getX(), position.getY());
			if (effect != null) {
				if (one.contains(checkPoint)) {
					effect.applyEffect(one);
					currentPowerUp = null;
				} else if (two.contains(checkPoint)) {
					effect.applyEffect(two);
					currentPowerUp = null;
				}
			}
		}
	}

	
	/**
     * Main method to start the game.
     *
     * @param args command-line arguments (not used).
     */
	
	public static void main(String[] args) {
		AirHockey a = new AirHockey();
		a.repaint();
	}

	private void updatePaddles() {
		if (playerOnePaddle == null || playerTwoPaddle == null) {
			return;
		}

		if (upPressed) {
			playerOnePaddle.moveUp();
		}

		if (downPressed) {
			playerOnePaddle.moveDown();
		}

		if (rightPressed) {
			playerOnePaddle.moveRight();
		}

		if (leftPressed) {
			playerOnePaddle.moveLeft();
		}

		if (WPressed) {
			playerTwoPaddle.moveUp();
		}

		if (SPressed) {
			playerTwoPaddle.moveDown();
		}

		if (DPressed) {
			playerTwoPaddle.moveRight();
		}

		if (APressed) {
			playerTwoPaddle.moveLeft();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP -> upPressed = true;
		case KeyEvent.VK_DOWN -> downPressed = true;
		case KeyEvent.VK_RIGHT -> rightPressed = true;
		case KeyEvent.VK_LEFT -> leftPressed = true;
		case KeyEvent.VK_W -> WPressed = true;
		case KeyEvent.VK_S -> SPressed = true;
		case KeyEvent.VK_D -> DPressed = true;
		case KeyEvent.VK_A -> APressed = true;
		case KeyEvent.VK_PERIOD -> playerOnePaddle.rotateClockwise();
		case KeyEvent.VK_E -> playerTwoPaddle.rotateClockwise();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP -> upPressed = false;
		case KeyEvent.VK_DOWN -> downPressed = false;
		case KeyEvent.VK_RIGHT -> rightPressed = false;
		case KeyEvent.VK_LEFT -> leftPressed = false;
		case KeyEvent.VK_W -> WPressed = false;
		case KeyEvent.VK_S -> SPressed = false;
		case KeyEvent.VK_D -> DPressed = false;
		case KeyEvent.VK_A -> APressed = false;
		}
	}
}
