package game;

/**
 * Interface for defining power-up effects.
 * <p>
 * Implemented using lambda expressions.
 * </p>
*/

@FunctionalInterface
public interface PowerUp {
	/**
     * Applies the power-up effect to a paddle.
     * 
     * @param paddle
     */

	void applyEffect(Paddle paddle);
}
