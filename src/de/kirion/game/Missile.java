package de.kirion.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class Missile extends GameObject {

	private int range = 100;
	
	public Missile(Coordinate position, double size, double movingAngle, double movingDistance) {
		super(position, size, size / 3);
		
		setMovingAngle(movingAngle);
		setMovingDistance(movingDistance);
	}

	public int getRange() {
		
		return range;
	}
	
	public void setRange(int range) {
		
		this.range = range;
	}
	
	@Override
	public void makeMove() {
		
		if (range > 0) super.makeMove();
		range--;
	}

	@Override
	public void paintMe(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		
		AffineTransform transform = new AffineTransform();
		RoundRectangle2D missileShape = new RoundRectangle2D.Double(getObjectPosition().getX(),getObjectPosition().getY(), getWidth(), getHeight(), 3, 3);
		
		transform.rotate(getMovingAngle(), missileShape.getCenterX(), missileShape.getCenterY());
		Shape transformedMissileShape = transform.createTransformedShape(missileShape);
		
		g2d.fill(transformedMissileShape);
	}
}
