package map;

import grid.PathTile;

import java.util.Queue;

public class SmallMap extends Map {
	
	private static final int width = 12;
	private static final int height = 12;
	private static final String userInput = "(0,2) (3,2) (3,9) (8,9) (8,3) (11,3)";

	public SmallMap() {
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
