package critters;

import critters.Critter.type;

public class ArmoredCritter extends Critter {


	public ArmoredCritter(int[][] Locations) {
		
		super(Locations, health, armor, speed, reward, name, type.ARMORED);
		
	}

	private static String		name		= "Armored";
	private static double 		health 		= 2;
	private static double 		speed		= 0.8;
	private static int			reward		= 60;
	private static double		armor		= 8;

}

