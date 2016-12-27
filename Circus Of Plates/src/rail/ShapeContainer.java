package rail;

import java.util.Iterator;
import java.util.LinkedList;

import shapes.Shape;

public class ShapeContainer {

	private LinkedList<Shape> shapeList;
	
	public ShapeContainer(){
		shapeList = new LinkedList<Shape>();
	}
	
	public Iterator<Shape> getIterator(){
		return shapeList.iterator();
	}
}
