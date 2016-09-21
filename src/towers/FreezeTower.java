package towers; import towers.Tower.type;
import critters.Critter;

public class FreezeTower extends Tower
{
	

	public FreezeTower(double xPos, double yPos) {
		super(xPos, yPos);
		this.freezeTower = true;
		this.buyingCost =newBuyingCost;
		this.refundValue = newRefundValue;
		this.upgradeCost = newUpgradeCost;
		this.reloadTime = newReloadTime;
		this.range = newRange;
		this.power = newPower;
		this.towerType = type.FREEZE;
	}

	
	private static int newBuyingCost = 200;
	private static int newRefundValue = 180;
	private static int newUpgradeCost = 180;
	private static int newReloadTime = 4;
	private static int newPower = 0;
	private static double newRange = 80;
}
