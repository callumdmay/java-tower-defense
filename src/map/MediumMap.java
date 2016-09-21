package map;

import grid.PathTile;

import java.util.Queue;

public class MediumMap extends Map {

	private static final int width = 15;
	private static final int height = 12;
	private static final String userInput = "(1, 11) (1,9) (12,9) (12,7) (4,7) (4,1) (14,1)";
	
	public MediumMap() {
		super();
		super.setMapSize(width, height);
		super.setInputCorner(userInput);

		super.initializeMap();

		Queue<PathTile> path = super.multipleCoordinatesSplit(userInput);
		super.buildPath(path);
		
		Queue<PathTile> corner = super.multipleCoordinatesSplit(userInput);
		super.cornerArray(corner);
		
		super.convertToBinaryMap(this);
	}
}