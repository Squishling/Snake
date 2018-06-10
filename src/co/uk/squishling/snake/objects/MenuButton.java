package co.uk.squishling.snake.objects;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class MenuButton {
	private double x;
	private double y;
	private double w;
	private double h;
	private Paint color;
	private String text;
	private Font font;
	
	public MenuButton(double x, double y, double w, double h, Paint color, String text, Font font) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h= h;
		this.color = color;
		this.text = text;
		this.font = font;
	}
	
	public boolean clicked(double x, double y) {
		if (x > this.x && x < this.x + w && y > this.y && y < this.y + h) {
			return true;
		}
		
		return false;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(Font.font(font.getFamily(), h / 2));
		
		gc.setFill(color);
		gc.fillRect(x, y, w, h);
		gc.setFill(Color.WHITE);
		gc.fillText(text, x + (w / 2), y + (h / 2));
	}
}
