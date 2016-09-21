package map;

import grid.PathTile;

import java.util.Queue;

public class LargeMap extends Map {

	private static final int width = 20;
	private static final int height = 18;
	private static final String userInput = "(0, 3) (16,3) (16,9) (5,9) (5,15) (19,15)";
	
	public LargeMap() {
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