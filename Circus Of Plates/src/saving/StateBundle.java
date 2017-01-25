package saving;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import shapes.CustomShape;
import shapes.RectangleShape;
import sprite.ShapeSprite;
import sprite.Sprite;
import model.Avatar;
import model.EmptyStack;
import model.FullStack;
import model.Stack;
import model.StackState;

public class StateBundle implements Serializable {


	private static final long serialVersionUID = 1L;
	ArrayList<FakeShape> shapesInUse;
	FakeStack[] firstPlayerStack, secondPlayerStack;
	FakeAvatar firstPlayerAvatar, secondPlayerAvatar;

	public StateBundle(Avatar firstPlayerAvatar, Avatar secondPlayerAvatar, List<CustomShape> shapesInUse) {
		initializeShapesInUse(shapesInUse);
		initializeStack(firstPlayerAvatar.getStack(), secondPlayerAvatar.getStack());
		this.firstPlayerAvatar = new FakeAvatar(firstPlayerAvatar.getX(), firstPlayerAvatar.getY(),
				firstPlayerAvatar.getPlayerIndex());
		this.secondPlayerAvatar = new FakeAvatar(secondPlayerAvatar.getX(), secondPlayerAvatar.getY(),
				secondPlayerAvatar.getPlayerIndex());
	}

	private void initializeShapesInUse(List<CustomShape> shapesInUse) {
		this.shapesInUse = new ArrayList<FakeShape>();
		for (int i = 0; i < shapesInUse.size(); i++) {
			FakeShape fakeShape = new FakeShape(shapesInUse.get(i).getFillColor(), shapesInUse.get(i).getStrokeColor(),
					shapesInUse.get(i).getXPosition(), shapesInUse.get(i).getYPosition(),
					shapesInUse.get(i).getXVelocity(), shapesInUse.get(i).getYVelocity(),
					shapesInUse.get(i).getIsCaught(), shapesInUse.get(i).getWidth(), shapesInUse.get(i).getHeight());
			this.shapesInUse.add(fakeShape);
		}
	}

	private void initializeStack(Stack[] firstPlayerStack, Stack[] secondPlayerStack) {
		this.firstPlayerStack = new FakeStack[2];
		this.secondPlayerStack = new FakeStack[2];
		initOneDPlayerOne(0, firstPlayerStack);
		initOneDPlayerOne(1, firstPlayerStack);
		initOneDPlayerTwo(0, secondPlayerStack);
		initOneDPlayerTwo(1, secondPlayerStack);
	}

	private void initOneDPlayerOne(int index, Stack[] firstPlayerStack) {
		ArrayList<Sprite> shapes = new ArrayList<Sprite>(firstPlayerStack[index].getShapesSprite());
		ArrayList<FakeShape> fake = new ArrayList<FakeShape>();
		for (int i = 0; i < shapes.size(); i++) {
			ShapeSprite shapeSprite = (ShapeSprite) shapes.get(i);
			FakeShape fakeShape = new FakeShape((Color) shapeSprite.getColor(), shapeSprite.getStrokeColor(),
					(double) shapeSprite.getX(), (double) shapeSprite.getY(), 0, 0, true, shapeSprite.getWidth(),
					shapeSprite.getHeight());
			fake.add(fakeShape);
		}
		this.firstPlayerStack[index] = new FakeStack(firstPlayerStack[index].getScore(), firstPlayerStack[index].getX(),
				firstPlayerStack[index].getY(), firstPlayerStack[index].getPlayerIndex(),
				firstPlayerStack[index].getHeightSum(), fake, firstPlayerStack[index].checkStackFull());
	}

	private void initOneDPlayerTwo(int index, Stack[] secondPlayerStack) {
		ArrayList<Sprite> shapes = new ArrayList<Sprite>(secondPlayerStack[index].getShapesSprite());
		ArrayList<FakeShape> fake = new ArrayList<FakeShape>();
		for (int i = 0; i < shapes.size(); i++) {
			ShapeSprite shapeSprite = (ShapeSprite) shapes.get(i);
			FakeShape fakeShape = new FakeShape((Color) shapeSprite.getColor(), shapeSprite.getStrokeColor(),
					(double) shapeSprite.getX(), (double) shapeSprite.getY(), 0, 0, true, shapeSprite.getWidth(),
					shapeSprite.getHeight());
			fake.add(fakeShape);
		}
		this.secondPlayerStack[index] = new FakeStack(secondPlayerStack[index].getScore(),
				secondPlayerStack[index].getX(), secondPlayerStack[index].getY(),
				secondPlayerStack[index].getPlayerIndex(), secondPlayerStack[index].getHeightSum(), fake,
				secondPlayerStack[index].checkStackFull());
	}

	public ArrayList<CustomShape> getInUse() {
		ArrayList<CustomShape> retInUse = new ArrayList<CustomShape>();
		for (int i = 0; i < this.shapesInUse.size(); i++) {
			CustomShape customShape = new RectangleShape((int) shapesInUse.get(i).getYPos(),
					(int) shapesInUse.get(i).getYPos(), shapesInUse.get(i).getWidth(), shapesInUse.get(i).getHeight(),
					shapesInUse.get(i).getFillColor(), shapesInUse.get(i).getStrokeColor());
			retInUse.add(customShape);
		}
		return retInUse;
	}
	
	public ArrayList<Avatar> getAvatar() {
		Avatar first = new Avatar();
		first.setX(this.firstPlayerAvatar.getXPos());
		first.setY(this.firstPlayerAvatar.getYPos());
		first.setIndex(firstPlayerAvatar.getPlayerIndex());
		Stack[] firstStack = new Stack[2];
		firstStack[0] = getOneDP1(0);
		firstStack[1] = getOneDP1(1);
		first.setStack(firstStack);
		Avatar second = new Avatar();
		second.setX(this.secondPlayerAvatar.getXPos());
		second.setY(this.secondPlayerAvatar.getYPos());
		second.setIndex(secondPlayerAvatar.getPlayerIndex());
		Stack[] secondStack = new Stack[2];
		secondStack[0] = getOneDP1(0);
		secondStack[1] = getOneDP1(1);
		second.setStack(secondStack);
		ArrayList<Avatar> avatars = new ArrayList<>();
		avatars.add(first);
		avatars.add(second);
		return avatars;
	}
	
	private Stack getOneDP1(int ind) {
		ArrayList<Sprite> retShapes = new ArrayList<Sprite>();
		ArrayList<FakeShape> loaded = new ArrayList<FakeShape>(firstPlayerStack[ind].getShapes());
		for(int i = 0; i < loaded.size(); i++) {
			CustomShape customShape = new RectangleShape((int)loaded.get(i).getXPos(), (int)loaded.get(i).getYPos(), loaded.get(i).getWidth(), loaded.get(i).getHeight(), 
					loaded.get(i).getFillColor(), loaded.get(i).getStrokeColor());
			retShapes.add(new ShapeSprite(customShape));
		}
		StackState state;
		if(firstPlayerStack[ind].getState())
			state = new FullStack();
		else
			state = new EmptyStack();
		return new Stack(firstPlayerStack[ind].getScore(), firstPlayerStack[ind].getXPos(), firstPlayerStack[ind].getYPos(), firstPlayerStack[ind].getPlayerIndex(),
				firstPlayerStack[ind].getHeightSum(), retShapes, state);
	}
	
	private Stack getOneDP2(int ind) {
		ArrayList<Sprite> retShapes = new ArrayList<Sprite>();
		ArrayList<FakeShape> loaded = new ArrayList<FakeShape>(secondPlayerStack[ind].getShapes());
		for(int i = 0; i < loaded.size(); i++) {
			CustomShape customShape = new RectangleShape((int)loaded.get(i).getXPos(), (int)loaded.get(i).getYPos(), loaded.get(i).getWidth(), loaded.get(i).getHeight(), 
					loaded.get(i).getFillColor(), loaded.get(i).getStrokeColor());
			retShapes.add(new ShapeSprite(customShape));
		}
		StackState state;
		if(secondPlayerStack[ind].getState())
			state = new FullStack();
		else
			state = new EmptyStack();
		return new Stack(secondPlayerStack[ind].getScore(), secondPlayerStack[ind].getXPos(), secondPlayerStack[ind].getYPos(), secondPlayerStack[ind].getPlayerIndex(),
				secondPlayerStack[ind].getHeightSum(), retShapes, state);
	}

}