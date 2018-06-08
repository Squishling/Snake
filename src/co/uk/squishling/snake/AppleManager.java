package co.uk.squishling.snake;

import java.util.concurrent.ThreadLocalRandom;

import co.uk.squishling.snake.objects.EmptyTile;
import co.uk.squishling.snake.objects.Grid;
import co.uk.squishling.snake.objects.SuperAppleTile;
import co.uk.squishling.snake.objects.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AppleManager {
	
	private Grid grid;
	private double WIDTH;
	private double HEIGHT;
	
	private int t;
	private int t2 = 0;
	private int t3;
	
	private final int SUPER_APPLE_LENGTH = 450;
	
	public boolean superApple = false;
	
	public AppleManager(Grid grid, double WIDTH, double HEIGHT) {
		this.grid = grid;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
	}
	
	public void create(boolean isSuper) {
		boolean foundPlace = false;
		
		while (!foundPlace) {
			int x = ThreadLocalRandom.current().nextInt(0, (int) grid.getWidth());
			int y = ThreadLocalRandom.current().nextInt(0, (int) grid.getHeight());
			
			if (grid.get(x, y).getID() == Id.EMPTY) {
				if (isSuper) {
					superApple = true;
					grid.set(x, y, Id.SUPER_APPLE);
				} else {
					grid.set(x, y, Id.APPLE);
				}
				
				foundPlace = true;
			}
		}
	}
	
	public void update() {
        if (ThreadLocalRandom.current().nextInt(0, 1201) == 0) {   	
        	if (!grid.superAppleExists()) {
        		create(true);
        		t2 = t;
        	}
        }
        
        if (t - t2 == SUPER_APPLE_LENGTH) {
        	superApple = false;
			t2 = 0;
			grid.removeSuperApple();
        }
        
        t++;
	}
	
	public void draw(GraphicsContext gc) {
		t3 = SUPER_APPLE_LENGTH - (t - t2);
		double w = t3 * (((grid.getWidth() * grid.getSize()) - 300) / SUPER_APPLE_LENGTH);
		
		if (superApple) {
			gc.setFill(Color.BLACK);
			gc.fillRect(((WIDTH / 2) - (w / 2)) - 1, (grid.getY() + (grid.getHeight() * grid.getSize())) - 31, w + 1, 16);
			
			gc.setFill(Color.RED);
			gc.fillRect((WIDTH / 2) - (w / 2), (grid.getY() + (grid.getHeight() * grid.getSize())) - 30, w, 15);
		}
	}
	
	public void setSuperApple(boolean superApple) {
		this.superApple = superApple;
	}
}
