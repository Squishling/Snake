package co.uk.squishling.snake.objects;

import co.uk.squishling.snake.Id;
import javafx.scene.paint.Color;

public class AppleTile extends Tile {
	public AppleTile(double x, double y, int gridX, int gridY, double size) {
		super(x, y, gridX, gridY,size, Color.RED, Id.APPLE);
	}
}
