package de.kirion.game;

import java.awt.Color;

public class EnemyTank extends Tank {
	
	private Tank playersTank;
	private boolean targetLocked = false;
	
	
	public EnemyTank(Coordinate position, double width, double height, double movingAngle, double movingDistance, Tank playersTank) {
		super(position, width, height, movingAngle, movingDistance);
		this.playersTank = playersTank;
		
		setTurningVelocity(TURNING_VELOCITY / 10 + Math.random() * 0.01);
		setDrivingVelocity(DRIVING_VELOCITY / 10 + Math.random() * 1.0);
		setLoadingTime(AMMO_LOADING_TIME * 2);
		setTurretColor(new Color(190, 124, 68));
		
		int enemyTankEnergy = (int) (width / 10 + Math.random() * 5);
		setEnergy(enemyTankEnergy);
		setEnergyStart(enemyTankEnergy);
		
		accelerateTank();
	}


	public Tank getPlayersTank() {
		return playersTank;
	}
	
	public void setPlayersTank(Tank playersTank) {
		this.playersTank = playersTank;
	}

	public boolean isTargetLocked() {
		return targetLocked;
	}

	public void setTargetLocked(boolean targetLocked) {
		this.targetLocked = targetLocked;
	}
	
	
	@Override
	public void makeMove() {
		
		if (getObjectPosition().getX() > 800 || getObjectPosition().getY() < 200) {
			stopTank();
		}
		aimAtPlayer();
		
		super.makeMove();
	}
	
	public void aimAtPlayer() {

		double playersTankCenterX = playersTank.getObjectPosition().getX() + playersTank.getWidth() / 2;
		double playersTankCenterY = playersTank.getObjectPosition().getY() + playersTank.getHeight() / 2;
		
		double enemyTankCenterX = getObjectPosition().getX() + getWidth() / 2;
		double enemyTankCenterY = getObjectPosition().getY() + getHeight() / 2;
		
		double x = playersTankCenterX - enemyTankCenterX;
		double y = playersTankCenterY - enemyTankCenterY;
		double angleToPlayer = Math.atan2(y, x);
		if (angleToPlayer < 0) angleToPlayer = angleToPlayer + 2 * Math.PI;
		
		double absolutCannonAngle = getAngleCannon() + getMovingAngle();
		if (absolutCannonAngle > 2 * Math.PI) absolutCannonAngle = absolutCannonAngle - 2 * Math.PI;
		
		double deltaAngle = angleToPlayer - absolutCannonAngle;
		
		if (Math.abs(deltaAngle) <= 0.01) {
			setTargetLocked(true);
			stopTurningCannon();
		}
		
		else {
			setTargetLocked(false);
			
			if (deltaAngle < 0.01) {
				turnCannonLeft();
				
				if (Math.abs(deltaAngle) > Math.toRadians(180)) {
					turnCannonRight();
				}	
			}
			
			if (deltaAngle > 0.01) {
				turnCannonRight();
				
				if (Math.abs(deltaAngle) > Math.toRadians(180)) {
					turnCannonLeft();
				}
			}
		}
	}
}
