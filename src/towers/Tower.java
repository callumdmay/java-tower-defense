package towers;

import critters.Critter;
import game.Player;

public abstract class Tower {	
	
	public enum type{
		FREEZE, SNIPER, GENERIC
	}
	protected int buyingCost;
	private double modifierBase = 1.0;		//base multiplier for upgrades
	private double modifierIncrease = 1.2;	//how much range and attack increase by when upgraded
	protected int refundValue;
	protected double range;
	protected double power ;
	private int level =1;
	private static int maxLevel = 3;
	protected int upgradeCost;
	private double xPos;
	private double yPos;
	protected boolean freezeTower = false;
	private Critter targetCritter;
	protected double reloadTime;
	private long lastAttackTime;
	private double angleOfRotation;
	protected type towerType;
	private double critterTravelDistanceMaximum =0;
	//system time of last attack

	
	
	public Tower(double xPos, double yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.lastAttackTime = 0;
		angleOfRotation = 0;
		lastAttackTime = 0;
	}
	
	
	public boolean canAttack(){
		if( (System.currentTimeMillis()-lastAttackTime)/1000.0 >= reloadTime){

			return true;
		}
		else
			return false;
	}

	public void refundTower() { // credit of this tower's salvage value
											// is added to the user's account.
		Player.getPlayer().addCredits(refundValue);
	}

	public int getBuyingCost() { // getters and setters for the private
									// attributes of this object.
		return buyingCost;
	}

	public void setBuyingCost(int pBuyingCost) {
		buyingCost = pBuyingCost;
	}

	public double getRefundValue() {
		return refundValue;
	}

	public void setRefundValue(int pRefundValue) {
		refundValue = pRefundValue;
	}

	public void setTargetCritter(Critter c){
		targetCritter = c;
	}
	public Critter getTargetCritter(){
		return targetCritter;
	}
	
	
	
	public double getCritterTravelDistanceMaximum() {
		return critterTravelDistanceMaximum;
	}


	public void setCritterTravelDistanceMaximum(double critterTravelDistanceMaximum) {
		this.critterTravelDistanceMaximum = critterTravelDistanceMaximum;
	}


	public double getRotationAngleInDegrees(){
		if(targetCritter !=null)
			angleOfRotation = (180/Math.PI)*Math.atan2(targetCritter.getYLoc()-yPos, targetCritter.getXLoc()-xPos);
		return angleOfRotation;
	}
	
	public double getRange() {
		return (range*getModifier());
	}

	public void setRange(double pRange) {
		range = pRange;
	}

	public double getPower() {
		return power*getModifier();
	}

	public void setPower(int pPower) {
		power = pPower;
	}

	public int getLevel() {
		return level;
	}
//returns if upgrade was successful
	public boolean upgrade() {
		if(level < maxLevel && Player.getPlayer().getCredits()>=upgradeCost){
			level++;
			Player.getPlayer().addCredits(-1*upgradeCost);
			return true;
		}
		return false;
	}

	public double getUpgradeCost() {
		return upgradeCost;
	}

	public void setUpgradeCost(int pUpgradeCost) {
		upgradeCost = pUpgradeCost;
	}
	
	public double getXLoc(){
		return xPos;
	}
	
	public double getYLoc(){
		return yPos;
	}
	
	public boolean isFreezeTower(){
		return freezeTower;
	}
	

	public void setTimeOfLastAttack(long time){

		lastAttackTime = time;
	}
	
	public type getType(){
		return this.towerType;
	}
	
	public double getModifier(){
		return (modifierBase + ((level-1)*modifierIncrease));
	}
}
