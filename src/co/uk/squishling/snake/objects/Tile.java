package co.uk.squishling.snake.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Tile {
	private double x;
	private double y;
	private int gridX;
	private int gridY;
	private double size;
	
	private Paint color;
	private int ID;
	
	public Tile(double x, double y, int gridX, int gridY, double size, Paint color, int ID) {
		this.x = x;
		this.y = y;
		this.gridX = gridX;
		this.gridY = gridY;
		this.size = size;
		this.color = color;
		this.ID = ID;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(x, y, size, size);
		
		gc.setFill(color);
		gc.fillRect(x + 1, y + 1, size - 2, size - 2);
	}
	
	public int getID() {
		return ID;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getGridX() {
		return gridX;
	}
	
	public int getGridY() {
		return gridY;
	}
	
}
