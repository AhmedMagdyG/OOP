package codes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class UndoRedo {

	private Stack<Struct> undo;
	private Stack<Struct> redo;
	private Pane canvas;
	
	public UndoRedo(List<IShape> shapes, String operation,Pane canvas) {
		undo = new Stack<>();
		addEntry(shapes,operation);
		redo = new Stack<>();
		this.canvas = canvas;
	}

	public void resetRedo() {
		redo = new Stack<>();
	}

	public void resetUndo() {
		undo = new Stack<>();
	}

	public void addEntry(List<IShape> shapes, List<Integer> ind, String operation) {
		Struct temp = new Struct(operation, ind, shapes);
		redo = new Stack<>();
		if (undo.isEmpty() || undo.peek().equals(temp) == false)
			undo.push(temp);
		System.out.println(undo.size() + " " + redo.size());
		System.out.println(temp.getObjects().size());
		System.out.println(temp.getIndices().size());
		System.out.println(temp.getOperation());
	}
	
	public void addEntry(List<IShape> shapes, String operation) {
		List<Integer> ind = new ArrayList<>();
		for (int i = 0; i < shapes.size(); i++) {
			ind.add(i);
		}
		Struct temp = new Struct(operation, ind, shapes);
		redo = new Stack<>();
		undo.push(temp);
		System.out.println(undo.size() + " " + redo.size());
		System.out.println(temp.getObjects().size());
		System.out.println(temp.getIndices().size());
		System.out.println(temp.getOperation());
	}
	
	public void addEntry(IShape shape,Integer ind,String operation) {
		List <Integer> indices = new ArrayList<>();
		List <IShape> shapes = new ArrayList<>();
		shapes.add(shape);
		indices.add(ind);
		Struct temp = new Struct(operation,indices,shapes);
		redo = new Stack<>();
		undo.push(temp);
		System.out.println(undo.size() + " " + redo.size());
		System.out.println(temp.getObjects().size());
		System.out.println(temp.getIndices().size());
		System.out.println(temp.getOperation());
	}

	public void undo( List<IShape> shapesList) {
		if (undo.size() == 0) {
			return;
		}
		Struct tempUndo = undo.pop();
		if (tempUndo.getOperation() == "Create") {
			System.out.println("Shapes size + " + " " + shapesList.size());
			IShape object = tempUndo.getObjects().get(0);
			Shape obj = object.getShape();
			canvas.getChildren().remove(obj);
			System.out.println(tempUndo.getIndices().get(0));
			shapesList.remove(tempUndo.getIndices().get(0).intValue());
			System.out.println(shapesList.size());
			redo.push(tempUndo);
		} else if (tempUndo.getOperation() == "Delete") {
			for (int i = 0; i < tempUndo.getObjects().size(); i++) {
				canvas.getChildren().add(tempUndo.getObjects().get(i).getShape());
				shapesList.add(tempUndo.getIndices().get(i), tempUndo.getObjects().get(i));
			}
			redo.push(tempUndo);
		} else {
			Struct tempRedo = new Struct(tempUndo.getOperation());
			IShape newObject, oldObject;
			Integer tempIndex;
			for (int i = 0; i < tempUndo.getObjects().size(); i++) {
				newObject = tempUndo.getObjects().get(i);
				tempIndex = tempUndo.getIndices().get(i);
				oldObject = shapesList.get(tempIndex);
				canvas.getChildren().remove(oldObject.getShape());
				tempRedo.addShape(oldObject, tempIndex);
				shapesList.set(tempIndex, newObject);
				canvas.getChildren().add(newObject.getShape());
			}
			redo.push(tempRedo);
		}

	}

	public void redo(List<IShape> shapesList) {
		if (redo.size() == 0) {
			return;
		}
		Struct tempRedo = redo.pop();
		if (tempRedo.getOperation() == "Create") {
			IShape object = tempRedo.getObjects().get(0);
			canvas.getChildren().add(object.getShape());
			shapesList.add(object);
			undo.push(tempRedo);
		} else if (tempRedo.getOperation() == "Delete") {
			for (int i = 0; i < tempRedo.getObjects().size(); i++) {
				canvas.getChildren().remove(tempRedo.getObjects().get(i).getShape());
				shapesList.remove(tempRedo.getIndices().get(i));
			}
			undo.push(tempRedo);
		} else {
			Struct tempUndo = new Struct(tempRedo.getOperation());
			IShape newObject, oldObject;
			Integer tempIndex;
			for (int i = 0; i < tempUndo.getObjects().size(); i++) {
				newObject = tempUndo.getObjects().get(i);
				tempIndex = tempUndo.getIndices().get(i);
				oldObject = shapesList.get(tempIndex);
				canvas.getChildren().remove(oldObject.getShape());
				tempUndo.addShape(oldObject, tempIndex);
				shapesList.set(tempIndex, newObject);
				canvas.getChildren().add(newObject.getShape());
			}
			undo.push(tempUndo);
		}
	}
	
	private class Struct {
		private List<IShape> objects;
		private List<Integer> indices;
		private String operation;

		public Struct(String op, List<Integer> ind, List<IShape> shapes) {
			objects = shapes;
			indices = ind;
			operation = op;
		}

		public Struct(String op) {
			objects = new ArrayList<>();
			indices = new ArrayList<>();
			operation = op;
		}

		public String getOperation() {
			return operation;
		}

		public List<IShape> getObjects() {
			return objects;
		}

		public List<Integer> getIndices() {
			return indices;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public void addShape(IShape shape, Integer ind) {
			objects.add(shape);
			indices.add(ind);
		}

	}
}
