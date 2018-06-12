package co.uk.squishling.snake.objects;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import co.uk.squishling.snake.AppleManager;
import co.uk.squishling.snake.Direction;
import co.uk.squishling.snake.Id;
import co.uk.squishling.snake.Main;
import javafx.scene.paint.Color;

public class Snake extends ArrayList<SnakeTile> {
	private static final long serialVersionUID = -8465007997570512364L;
	
	private int toAdd = 0;
	private int direction;
	
	private int score;
	
	private double lengthMins = 5;
	
	private int t = 0;
	private int tDivide = 15;
	private int time = (int) (3600 * lengthMins) / tDivide;
	
	private Grid grid;
	private AppleManager appleManager;
	
	public Snake(Grid grid, AppleManager appleManager, int length) {
		this.grid = grid;
		this.appleManager = appleManager;
		
		for (int i = 0; i < length; i++) {
			int x = (((int) grid.getWidth() / 2) + i);
			int y = ((int) grid.getHeight() / 2);
			
			add(new SnakeTile(grid.calcTrueX(x), grid.calcTrueY(y), x, y, grid.getSize()));
		}
	}
	
	public void update(int d) {
		if (t % time == 0 && tDivide > 1) {
			tDivide--;
		}
		
        if (t % tDivide == 0) {
        	direction = d;
    		
    		Tile tile = findTile(direction);
    		
    		if (grid.get(tile.getGridX(), tile.getGridY()).getID() == Id.SNAKE) {
    			Main.die();
    		}
    		
    		add(0, new SnakeTile(tile.getX(), tile.getY(), tile.getGridX(), tile.getGridY(), grid.getSize()));
    		
    		if (toAdd == 0) {
    			Tile lastTile = get(size() - 1);
    			
    			grid.set(lastTile.getGridX(), lastTile.getGridY(), Id.EMPTY);
    			remove(size() - 1);
    		} else {
    			toAdd--;
    		}
    		
    		if (grid.get(tile.getGridX(), tile.getGridY()).getID() == Id.APPLE || grid.get(tile.getGridX(), tile.getGridY()).getID() == Id.SUPER_APPLE) {
    			toAdd++;
    			
    			if (grid.get(tile.getGridX(), tile.getGridY()).getID() == Id.APPLE) {
    				appleManager.create(false);
    				score += 5;
    			} else {
        			appleManager.setSuperApple(false);
    				score += 25;
    			}
    		}
    		
    		for (int i = 0; i < size(); i++) {
    			grid.set(get(i).getGridX(), get(i).getGridY(), Id.SNAKE);
    		}
        }
        
        appleManager.update();
        
        t++;
	}
	
	public Tile findTile(int d) {
		if (d == Direction.LEFT) {
			if (get(0).getGridX() == 0) {
				return new Tile(grid.getX() + ((grid.getWidth() - 1) * grid.getSize()), grid.getY() + (get(0).getGridY() * grid.getSize()), (int) grid.getWidth() - 1, get(0).getGridY(), grid.getSize(), Color.WHITE, Id.EMPTY);
			} else {
				return new Tile(grid.getX() + ((get(0).getGridX() + 1) * grid.getSize()), grid.getY() + (get(0).getGridY() * grid.getSize()), (get(0).getGridX() - 1), get(0).getGridY(), grid.getSize(), Color.WHITE, Id.EMPTY);
			}
		} else if (d == Direction.UP) {
			if (get(0).getGridY() == 0) {
				return new Tile(grid.getX() + (get(0).getGridX() * grid.getSize()), grid.getY() + ((grid.getHeight() - 1) * grid.getSize()), get(0).getGridX(), (int) grid.getHeight() - 1, grid.getSize(), Color.WHITE, Id.EMPTY);
			} else {
				return new Tile(grid.getX() + (get(0).getGridX() * grid.getSize()), grid.getY() + ((get(0).getGridY() - 1) * grid.getSize()), get(0).getGridX(), get(0).getGridY() - 1, grid.getSize(), Color.WHITE, Id.EMPTY);
			}
		} else if (d == Direction.RIGHT) {
			if (get(0).getGridX() == grid.getWidth() - 1) {
				return new Tile(grid.getX(), grid.getX() + (get(0).getGridY() * grid.getSize()), 0, get(0).getGridY(), grid.getSize(), Color.WHITE, Id.EMPTY);
			} else {
				return new Tile(grid.getX() + ((get(0).getGridX() + 1) * grid.getSize()), grid.getY() + (get(0).getGridY() * grid.getSize()), (get(0).getGridX() + 1), get(0).getGridY(), grid.getSize(), Color.WHITE, Id.EMPTY);
			}
		} else if (d == Direction.DOWN) {
			if (get(0).getGridY() == grid.getHeight() - 1) {
				return new Tile(grid.getX() + (get(0).getGridX() * grid.getSize()), grid.getY(), get(0).getGridX(), 0, grid.getSize(), Color.WHITE, Id.EMPTY);
			} else {
				return new Tile(grid.getX() + (get(0).getGridX() * grid.getSize()), grid.getY() + ((get(0).getGridY() + 1) * grid.getSize()), get(0).getGridX(), get(0).getGridY() + 1, grid.getSize(), Color.WHITE, Id.EMPTY);
			}
		}
		
		return null;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
}
