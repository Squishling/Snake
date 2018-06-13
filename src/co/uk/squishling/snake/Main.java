package co.uk.squishling.snake;

import co.uk.squishling.snake.objects.Grid;
import co.uk.squishling.snake.objects.MenuButton;
import co.uk.squishling.snake.objects.Snake;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {

	private Pane root;
	private static Scene scene;
	private Canvas canvas;
	
	private Grid grid;
	private Snake snake;
	private AppleManager appleManager;
	
	private MenuButton playButton;
	private MenuButton exitButton;
	
	private MenuButton menuButton;
	private MenuButton exitButton2;
	
	private Font font;
	
	private double WIDTH;
	private double HEIGHT;
	
	private int direction = 0;
	
	public static boolean menu = true;
	private static boolean menuDrawn = false;
	
	private static boolean dead = false;
	private static boolean deadDrawn = false;
	
	public void start(Stage stage) {
		try {
			root = new Pane();
			
			scene = new Scene(root, 1280, 720);
			
			stage.setScene(scene);
			stage.setTitle("Snake");
			stage.setFullScreen(true);
			stage.setResizable(false);
			stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			
			stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent e) -> {
		        if (KeyCode.ESCAPE == e.getCode()) {
		            stage.close();
		        }
		    });
			
//			stage.getIcons().add(new Image("/icon.png"));
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		WIDTH = root.getWidth();
		HEIGHT = root.getHeight();
		
		canvas = new Canvas(scene.getWidth(), scene.getHeight());
		canvas.setFocusTraversable(true);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		font = Font.loadFont("file:res/DTM-Sans.otf", 50);
		
		if (font == null) {
			font = Font.font(50);
		}
		 
	    new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {	
	        	
	        	update();
		        draw(gc);
	        }
	    }.start();
	    
	    canvas.setOnMouseClicked((MouseEvent e) -> {
	    	if (menu && playButton.clicked(e.getSceneX(), e.getSceneY())) {
	    		menu = false;
	    		menuDrawn = false;
	    		
	    		scene.setCursor(Cursor.NONE);
	    		
	    		grid = new Grid(30, 30, WIDTH, HEIGHT);
	    		appleManager = new AppleManager(grid, WIDTH, HEIGHT);
	    		
	    		appleManager.create(false);
	    		
	    		snake = new Snake(grid, appleManager, 3);
	    		
	    		direction = 0;
	    	}
	    	
	    	if (menu && exitButton.clicked(e.getSceneX(), e.getSceneY())) {
	    		Platform.exit();
	    	}
	    	
	    	if (dead && menuButton.clicked(e.getSceneX(), e.getSceneY())) {
	    		dead = false;
	    		deadDrawn = false;
	    		menu = true;
	    	}
	    });
	    
	    canvas.setOnKeyReleased((KeyEvent e) -> {
	    	if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
	    		if (!(grid.get(snake.findTile(Direction.UP).getGridX(), snake.findTile(Direction.UP).getGridY()).getID() == Id.SNAKE)) {
	    			direction = Direction.UP;
	    		}
	    	} else if (e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT) {
	    		if (!(grid.get(snake.findTile(Direction.LEFT).getGridX(), snake.findTile(Direction.LEFT).getGridY()).getID() == Id.SNAKE)) {
	    			direction = Direction.LEFT;
	    		}
	    	} else if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
	    		if (!(grid.get(snake.findTile(Direction.DOWN).getGridX(), snake.findTile(Direction.DOWN).getGridY()).getID() == Id.SNAKE)) {
	    			direction = Direction.DOWN;
	    		}
	    	} else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
	    		if (!(grid.get(snake.findTile(Direction.RIGHT).getGridX(), snake.findTile(Direction.RIGHT).getGridY()).getID() == Id.SNAKE)) {
	    			direction = Direction.RIGHT;
	    		}
	    	}
	    });
	    
	    root.getChildren().add(canvas);
	}
	
	public void draw(GraphicsContext gc) {
		if (menu) {
			if (!menuDrawn) {
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, WIDTH, HEIGHT);
				
				gc.setFill(Color.WHITE);
				gc.setTextAlign(TextAlignment.CENTER);
				gc.setTextBaseline(VPos.BASELINE);
				gc.setFont(Font.font(font.getFamily(), HEIGHT / 5));
				gc.fillText("Snake", WIDTH / 2, HEIGHT / 4);
				
				playButton = new MenuButton(WIDTH / 4, HEIGHT / 3, WIDTH / 2, HEIGHT / 5, Color.RED, "PLAY", font);
				exitButton = new MenuButton(WIDTH / 4, (HEIGHT / 5) * 3, WIDTH / 2, HEIGHT / 5, Color.RED, "EXIT", font);
				
				playButton.draw(gc);
				exitButton.draw(gc);
				
	    		menuDrawn = true;
			}
    	} else if (dead) {
    		if (!deadDrawn) {
    			gc.setGlobalAlpha(0.5);
    			gc.setFill(Color.BLACK);
    			gc.fillRect(0, 0, WIDTH, HEIGHT);
    			gc.setGlobalAlpha(1.0);
    			
    			gc.setFill(Color.WHITE);
    			gc.setFont(Font.font(font.getFamily(), HEIGHT / 20));
    			gc.setTextAlign(TextAlignment.CENTER);
    			gc.fillText("You Died!", WIDTH / 2, (HEIGHT / 5) * 2);
    			
    			menuButton = new MenuButton((WIDTH / 2) - (WIDTH / 8), HEIGHT / 2, WIDTH / 4, HEIGHT / 15, Color.RED, "MENU", font);
    			menuButton.draw(gc);
    			
    			deadDrawn = true;
    		}
    	} else {
    		gc.setFill(new Color(0.03, 0.05, 0.14, 1));
    		gc.fillRect(0, 0, WIDTH, HEIGHT);
    		
    		gc.setFill(Color.WHITE);
    		gc.fillRect(grid.getX() - 5, 0, (grid.getWidth() * grid.getSize()) + 10, HEIGHT);
    		
    		gc.setFill(new Color(0.04, 0.04, 0.04, 1));
    		gc.fillRect(grid.getX(), grid.getY() + (grid.getHeight() * grid.getSize()), grid.getWidth() * grid.getSize(), (HEIGHT / 15));
    		
    		gc.setFill(Color.WHITE);
    		gc.fillRect(grid.getX(), grid.getY() + (grid.getHeight() * grid.getSize()), grid.getWidth() * grid.getSize(), 5);
    		
    		grid.draw(gc);
    		appleManager.draw(gc);
    		
    		gc.setFill(Color.WHITE);
    		gc.setFont(Font.font(font.getFamily(), HEIGHT / 25));
    		gc.setTextBaseline(VPos.CENTER);
    		gc.setTextAlign(TextAlignment.LEFT);
    		gc.fillText("Score: " + snake.getScore(), grid.getX() + 10, (grid.getY() + (grid.getHeight() * grid.getSize())) + (HEIGHT / 30));	
    	}
	}
	
	public void update() {
		if (!dead && !menu) {
			snake.update(direction);
		}
	}
	
	public static void die() {
		dead = true;
		scene.setCursor(Cursor.DEFAULT);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
