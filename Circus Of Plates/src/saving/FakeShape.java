package saving;

public class FakeShape {

	private double xPos, yPos;
	private double xVel, yVel;
	private boolean isCaught;
	private int width, height;
	private int shapeType;
	private double r, g, b;
	

	public FakeShape(double xPos, double yPos, double xVel, double yVel,
			boolean isCaught, int width, int height, int shapeType, double r, double g, double b) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		this.isCaught = isCaught;
		this.width = width;
		this.height = height;
		this.shapeType = shapeType;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	

	
	public double getXPos() {
		return this.xPos;
	}
	
	public double getYPos() {
		return this.yPos;
	}
	
	public double getXVel() {
		return this.xVel;
	}
	
	public double getYVel() {
		return this.yVel;
	}
	
	public boolean getIsCaught() {
		return this.isCaught;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getShape() {
		return this.shapeType;
	}
	
	public double getR() {
		return this.r; 
	}
	
	public double getG() {
		return this.g;
	}
	
	public double getB() {
		return this.b;
	}
	
}
