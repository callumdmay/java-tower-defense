package towers;

import critters.Critter;

public class Projectile {
	public enum projectileType{
		GENERIC,FREEZE,SNIPER
	}
	private double xLoc;
	private double yLoc;
	private double xDest;
	private double yDest;
	private double xInit;
	private double yInit;
	private double power;
	private double speed = 20;
	private projectileType projType;
	private boolean arrivedAtTarget = false;
	private Critter targetCritter;
	private int towerSourceLevel;
	
	public Projectile(double pXInit, double pYInit, double pXDest, double pYDest, double pPower, Critter pTargetCritter, projectileType pType, int level){
		

		xInit = pXInit;		
		xDest = pXDest;
		yInit= pYInit;	
		yDest = pYDest;
		power = pPower;
		
		xLoc = xInit +12*Math.cos(angleOfProjectileInRadians());
		yLoc = yInit +12*Math.sin(angleOfProjectileInRadians());
		arrivedAtTarget = false;
		
		towerSourceLevel = level;
		
		targetCritter = pTargetCritter;
		projType = pType;
		if(projType == projectileType.SNIPER){
			speed=30;
		}
	}
	
	public double angleOfProjectileInDegrees(){
		return (180/Math.PI)*Math.atan2(yDest-yInit, xDest-xInit);
	}

	public double angleOfProjectileInRadians(){
		return Math.atan2(yDest-yInit, xDest-xInit);
	}

	public void move(){
		
		//projectile has hit
		if (Math.abs(xLoc - xDest)< speed/2 || Math.abs(yLoc - yDest)< speed/2){
			arrivedAtTarget = true;
			targetCritter.takeDamage(power);
			if(projType == projectileType.FREEZE){
				targetCritter.setFreezeDuration(4000 +(towerSourceLevel-1)*1000);
				targetCritter.freezeCritter();
				
			}
		}
		else{
			xLoc += speed*Math.cos(angleOfProjectileInRadians());

			yLoc += speed*Math.sin(angleOfProjectileInRadians());
		}
	}
	
	public boolean hasArrived(){
		return arrivedAtTarget;
	}
	
	public double getX(){
		return this.xLoc;
	}
	public double getY(){
		return this.yLoc;
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	public projectileType getType(){
		return this.projType;
	}
	
}
