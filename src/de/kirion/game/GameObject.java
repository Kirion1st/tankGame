package de.kirion.game;

/**
 * The main functionality of the game
 * 
 * @author Kirion1st
 *
 */
public abstract class GameObject {

	private Coordinate objectPosition;
	private double width;
	private double height;
	private double movingAngle;
	private double movingDistance;
	
	public GameObject(Coordinate objectPosition, double width, double height) {
		
		this.objectPosition = objectPosition;
		this.height = height;
		this.width = width;
		this.movingAngle = 0;
		this.movingDistance = 0;
	}
	
	public Coordinate getObjectPosition() {
		
		return objectPosition;
	}
	
	public void setObjectPosition(Coordinate objectPosition) {
		
		this.objectPosition = objectPosition;
	}
	
	public double getWidth() {
		
		return width;
	}
	
	public void setWidth(double width) {
		
		this.width = width;
	}
	
	public double getHeight() {
		
		return height;
	}
	
	public void setHeight(double height) {
		
		this.height = height;
	}
	
	public double getMovingDistance() {
		
		return movingDistance;
	}
	
	public void setMovingDistance(double movingDistance) {
		
		this.movingDistance = movingDistance;
	}
	
	public double getMovingAngle() {
		
		return movingAngle;
	}
	
	public void setMovingAngle(double movingAngle) {
		
		this.movingAngle = movingAngle;
	}
	
	public boolean isLeftOf(GameObject that) {
		
		return this.getObjectPosition().getX() + this.getWidth() < that.getObjectPosition().getX();
	}
	
	public boolean isAbove(GameObject that) {
		
		return this.getObjectPosition().getY() + this.getHeight() < that.getObjectPosition().getY();
	}
	
	public boolean touches(GameObject that) {
		
		if(this.isLeftOf(that)) return false;
		if(that.isLeftOf(this)) return false;
		if(this.isAbove(that)) return false;
		if(that.isAbove(this)) return false;
		
		return true;
	}
	
	public static Coordinate polarToCartesianCoordinates(double angle) {
		
		// X-Axis is heading east, Y-Axis is heading South
		double x = Math.cos(angle);
		double y = Math.sin(angle);
		
		return new Coordinate(x, y);
	}
	
	public void moveGameObject() {
		
		Coordinate direction = polarToCartesianCoordinates(movingAngle);
		
		objectPosition.setX(objectPosition.getX() + direction.getX() * movingDistance);
		objectPosition.setY(objectPosition.getY() + direction.getY() * movingDistance);
	}
	
	public void makeMove() {
		
		moveGameObject();
	}
	
	protected abstract void paintMe(java.awt.Graphics g);
}
