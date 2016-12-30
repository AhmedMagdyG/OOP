package view;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GraphicsDrawer {

	private Canvas gameCanvas;
	private Image backGround;
	
	int y = 600;
	
	public GraphicsDrawer(Canvas gameCanvas) {
		this.gameCanvas = gameCanvas;
		this.backGround = new Image(new File("res" + File.separator +"background.jpg").toURI().toString());
	}

	public void draw() {
		GraphicsContext g = gameCanvas.getGraphicsContext2D();
		g.drawImage(backGround, 0, 0);
		/**
		 * draw shapes
		 */
		g.fillRect(500, y, 100, 100);
		y--;
		if(y < -100) y = 600;
	}
}
