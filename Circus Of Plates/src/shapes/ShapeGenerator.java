package shapes;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import model.Util;

public class ShapeGenerator {

	private ArrayList<Color> colors;

	private ShapeFactory shapeFactory;

	public ShapeGenerator() {
		shapeFactory = new ShapeFactory();
		initializeColors();
	}

	private void initializeColors() {
		colors = new ArrayList<Color>();
		colors.add(Color.RED);
		colors.add(Color.BLUEVIOLET);
		colors.add(Color.GREEN);
	}

	public void addColor(Color newColor) {
		for (int i = 0; i < colors.size(); i++) {
			if (colors.get(i).equals(newColor)) {
				return;
			}
		}
		colors.add(newColor);
	}

	public CustomShape getShape() {
		int[] dimensions;
		int shapeType = Util.RANDOM_GENERATOR.nextInt(ShapeFactory.getLoadedShapesCount());
		int colorIndex = Util.RANDOM_GENERATOR.nextInt(colors.size());
		if (shapeType == 0) {
			dimensions = new int[] { 40, 15 };
		} else {
			dimensions = new int[] { 40 };
		}
		CustomShape reqShape = shapeFactory.getObject(shapeType, dimensions, colors.get(colorIndex));
		return reqShape;
	}

}
