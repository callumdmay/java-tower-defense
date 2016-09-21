package critters;

import critters.Critter.type;

public class GruntCritter extends Critter{

	public GruntCritter(int[][] Locations) {
		
		super(Locations, health, armor, speed, reward, name, type.GRUNT);

	}

	private static String		name		= "Grunt";
	private static double 		health 		= 5;
	private static double 		speed		= 1;
	private static int			reward		= 20;
	private static double		armor		= 1;

}


