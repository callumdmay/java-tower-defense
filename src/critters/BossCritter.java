package critters;

import critters.Critter.type;

public class BossCritter extends Critter {

	public BossCritter(int[][] Locations) {
		
		super(Locations, health, armor, speed, reward, name, type.BOSS);

	}

	private static String		name		= "Boss";
	private static double 		health 		= 15;
	private static double 		speed		= 0.5;
	private static int			reward		= 100;
	private static double		armor		= 8;


}

