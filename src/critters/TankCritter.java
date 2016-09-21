package critters;

import critters.Critter.type;

public class TankCritter extends Critter {
	public TankCritter(int[][] Locations) {
		
		super(Locations, health, armor, speed, reward, name, type.TANK);
		
	}

	private static String		name		= "Tank";
	private static double 		health 		= 15;
	private static double 		speed		= 0.6;
	private static int			reward		= 80;
	private static double		armor		= 4;


}
