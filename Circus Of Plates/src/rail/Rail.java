package rail;

public class Rail {
	
	private Allign direction;
	private int height, length;
	private ShapeContainer shapeContainer;
	
	public Rail(Allign direction, int height, int length){
		this.direction = direction;
		this.height = height;
		this.length = length;
	}
}
