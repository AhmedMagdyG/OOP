package view;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Sprite;

public class GraphicsDrawer {

	private static final int STAND_THICKNESS = 10;
	private static final int STAND_HEIGHT = 585;
	private static final int STAND_WIDTH = 950;

	private Canvas gameCanvas;
	private Image backGround;

	public GraphicsDrawer(Canvas gameCanvas) {
		this.gameCanvas = gameCanvas;
		this.backGround = new Image(new File("res" + File.separator + "background.jpg").toURI().toString());
	}

	public void draw(ArrayList<Sprite> sprites) {
		GraphicsContext g = gameCanvas.getGraphicsContext2D();
		drawBackground(gameCanvas.getGraphicsContext2D());
		int spriteCount = sprites.size();
		for (int idx = 0; idx < spriteCount; idx++) {
			sprites.get(idx).draw(g);
		}
	}

	private void drawBackground(GraphicsContext g) {
		g.drawImage(backGround, 0, 0);
		g.setFill(Color.BLACK);
		g.fillRect(0, STAND_HEIGHT, STAND_WIDTH, STAND_THICKNESS);
	}
}
