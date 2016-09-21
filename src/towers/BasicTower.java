package towers;


public class BasicTower extends Tower{

	public BasicTower(double xPos, double yPos) {
		super(xPos, yPos);
		this.buyingCost =newBuyingCost;
		this.refundValue = newRefundValue;
		this.upgradeCost = newUpgradeCost;
		this.reloadTime = newReloadTime;
		this.range = newRange;
		this.power = newPower;
		this.towerType = type.GENERIC;
	}
	
	private static int newBuyingCost = 100;
	private static int newRefundValue = 90;
	private static int newUpgradeCost = 100;
	private static double newReloadTime = 0.3;
	private static double newRange = 100;
	private static double newPower = 0.6;

}
