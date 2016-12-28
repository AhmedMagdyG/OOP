package shapes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javafx.scene.paint.Color;

public class ShapeFactory {

	private static final int EQUAL_PARAMETERS = 2, VARIED_PARAMETERS = 3;
	
	private static ArrayList<Constructor<?>[]> loadedShapes = null;
	
	public ShapeFactory() {
		if(loadedShapes == null) {
			loadedShapes = new ArrayList<Constructor<?>[]>();
		}
		ShapeFactory.addNewShape(RectangleShape.class.getConstructors());
	}
	
	public static void addNewShape(Constructor<?>[] newShapeConstructor) { 
		int length = loadedShapes.size();
		for(int i = 0; i < length; i++) {
			if(loadedShapes.get(i).equals(newShapeConstructor)) {
				return;
			}
		}
		loadedShapes.add(newShapeConstructor);
	}
	
	public static int getLoadedShapesCount() {
		return ShapeFactory.loadedShapes.size();
	}
	
	public CustomShape getObject(int requiredShape, int[] dimensions, Color color) {
		if (requiredShape >= loadedShapes.size()) {
			return null;
		}
		try {
			int sz = loadedShapes.get(requiredShape).length;
			Constructor<?> constructor = loadedShapes.get(requiredShape)[sz - 1];
			Object returnedShape = getReqShape(constructor, dimensions, color);
			return (CustomShape) returnedShape;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Object getReqShape(Constructor<?> constructor, int[] dimensions, Color color)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int parametersLength = constructor.getParameterCount();
		int dimensionsLength = dimensions.length;
		if (parametersLength != dimensionsLength + 1) {
			return null;
		}

		switch (parametersLength) {
		case EQUAL_PARAMETERS:
			return constructor.newInstance(dimensions[0], color);
		case VARIED_PARAMETERS:
			return constructor.newInstance(dimensions[0], dimensions[1], color);
		default:
			return null;
		}
	}

}
