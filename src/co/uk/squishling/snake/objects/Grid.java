package co.uk.squishling.snake.objects;

import java.util.ArrayList;

import co.uk.squishling.snake.Id;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grid extends ArrayList<ArrayList<Tile>> {
	private static final long serialVersionUID = 5913145251258708697L;
	
	private int w;
	private int h;
	private int width;
	private int height;
	private double screenWidth;
	private double screenHeight;
	private double size;
	private double x;
	private double y;
	
	public Grid(int w, int h, double screenWidth, double screenHeight) {
		this.w = w;
		this.h = h;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		size = (screenHeight - (screenHeight / 15)) / h;
		width = (int) (w * size);
		height = (int) (h * size);
		
		x = (screenWidth / 2) - ((size * w) / 2);
		y = 0;
		
		for (int i = 0; i < w; i++) {
			ArrayList<Tile> temp = new ArrayList<Tile>();
			
			for (int j = 0; j < h; j++) {
				temp.add(new EmptyTile(calcTrueX(i), calcTrueY(j), i, j, size));
			}
			
			add(temp);
		}
	}
	
	public void set(int x, int y, int ID) {
		if (ID == Id.EMPTY) {
			get(x).set(y, new EmptyTile(this.x + (x * size), this.y + (y * size), x, y, size));
		}
		
		if (ID == Id.SNAKE) {
			get(x).set(y, new SnakeTile(this.x + (x * size), this.y + (y * size), x, y, size));
		}
		
		if (ID == Id.APPLE) {
			get(x).set(y, new AppleTile(this.x + (x * size), this.y + (y * size), x, y, size));
		}
		
		if (ID == Id.SUPER_APPLE) {
			get(x).set(y, new SuperAppleTile(this.x + (x * size), this.y + (y * size), x, y, size));
		}
	}
	
	public Tile get(int x, int y) {
		return get(x).get(y);
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(x, y, w * size, h * size);
		
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < get(i).size(); j++) {
				if (!(get(i, j).getID() == Id.EMPTY)) {
					get(i, j).draw(gc);
				}
			}
		}
	}
	
	public boolean superAppleExists() {
		for (int i = 0; i < size(); i++) {
    		for (int j = 0; j < get(i).size(); j++) {
    			if (get(i).get(j) instanceof SuperAppleTile) {
    				return true;
    			}
    		}
    	}
		return false;
	}
	
	public void removeSuperApple() {
		for (int i = 0; i < size(); i++) {
    		for (int j = 0; j < get(i).size(); j++) {
    			if (get(i).get(j) instanceof SuperAppleTile) {
    				set(i, j, Id.EMPTY);
    			}
    		}
    	}
	}
	
	public int calcTrueX(int x) {
		return (int) (this.x + (((int) width / 2) * size));
	}
	
	public int calcTrueY(int y) {
		return (int) (this.y + (((int) height / 2) * size));
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return w;
	}
	
	public double getHeight() {
		return h;
	}
	
	public double getSize() {
		return size;
	}
	
}
