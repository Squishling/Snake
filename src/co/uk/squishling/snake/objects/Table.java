package co.uk.squishling.snake.objects;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class Table extends ArrayList<ArrayList<Cell>> {

	private static final long serialVersionUID = 1363427248835992047L;
	
	private int x;
	private int y;
	
	private int w;
	private int h;
	
	private int colW;
	private int rowH;
	
	private boolean isColor2 = false;
	
	private Paint color1;
	private Paint color2;
	private Paint tempColor;
	
	private Font font;
	
	public Table(int x, int y, int w, int h, int colW, int rowH, Paint color1, Paint color2, Font font) {
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
		
		this.colW = colW;
		this.rowH = rowH;
		
		this.color1 = color1;
		this.color2 = color2;
		
		this.font = font;
		
		for (int i = 0; i < w; i++) {
			add(new ArrayList<Cell>());
			
			if (isColor2) {
				tempColor = color2;
			} else {
				tempColor = color1;
			}
			
			isColor2 = !isColor2;
			
			for (int j = 0; j < h; j++) {
				get(i).add(new Cell(x + (colW * i), y + (rowH * j), colW, rowH, tempColor, "", font));
			}
		}
	}
	
	public void draw(GraphicsContext gc) {
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < get(i).size(); j++) {
				get(i).get(j).draw(gc);
			}
		}
	}
	
	public void set(int x, int y, String text) {
		get(x).get(y).setText(text);
	}
	
}
