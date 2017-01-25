package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import shapes.CustomShape;
import shapes.ShapeGenerator;

public class ShapesPool {

	public static final int POOL_SIZE = 50;

	private List<CustomShape> available;
	private List<CustomShape> inUse;
	private ShapeGenerator shapeGenerator;

	private static ShapesPool shapesPool;
	
	private ShapesPool() {
		available = new ArrayList<CustomShape>();
		inUse = new ArrayList<CustomShape>();
		shapeGenerator = new ShapeGenerator();
	}

	public static ShapesPool getInstance(){
		if(shapesPool == null){
			shapesPool = new ShapesPool();
		}
		return shapesPool;
	}
	
	public CustomShape getObject() {
		CustomShape generatedShape = null;
		if (available.size() + inUse.size() < POOL_SIZE) {
			generatedShape = shapeGenerator.getShape();
			inUse.add(generatedShape);
		} else if (!available.isEmpty()) {
			generatedShape = available.get(new Random().nextInt(available.size()));
			useShape(generatedShape);
		}
		return generatedShape;
	}

	public boolean releaseShape(CustomShape expiredShape) {
		for (int index = 0; index < inUse.size(); index++) {
			if (inUse.get(index) == expiredShape) {
				available.add(inUse.get(index));
				inUse.remove(index--);
				expiredShape = null;
				return true;
			}
		}
		return false;
	}

	private boolean useShape(CustomShape availableShape) {
		for (int index = 0; index < available.size(); index++) {
			if (availableShape == available.get(index)) {
				inUse.add(available.get(index));
				available.remove(index--);
				availableShape = null;
				return true;
			}
		}
		return false;
	}

	public List<CustomShape> getInUse() {
		return inUse;
	}

	public void removeInUse(CustomShape shape) {
		for(int i = 0; i < inUse.size(); i++){
			if(inUse.get(i) == shape){
				inUse.remove(i);
			}
		}
	}

	public void releaseAll() {
		while(inUse.size() > 0){
			releaseShape(inUse.get(0));
		}
	}
}
