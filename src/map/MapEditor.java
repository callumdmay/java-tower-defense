package map;

import grid.PathTile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

/**
 * @author		Wei Wang
 * @version		1.0
 * @since		2015-03-23
 */

public class MapEditor {

	private Map map;
	private int[][] mapArray;

	private int width;
	private int height;
	private String userInput = "";
	
	private static final String folderName = "mapSaves";

	public MapEditor(int width, int height, String userInput){
		map = new Map();
		map.setMapSize(width, height);
		map.setInputCorner(userInput);

		map.initializeMap();

		Queue<PathTile> path = map.multipleCoordinatesSplit(userInput);
		map.buildPath(path);
		
		Queue<PathTile> corner = map.multipleCoordinatesSplit(userInput);
		map.cornerArray(corner);
		
		mapArray = map.convertToBinaryMap(map);
	}
	
	/**
	 * Retrieve the Map
	 * 
	 * @return	Map
	 */
	public Map getMap(){
		return map;
	}
	
	/**
	 * Write the Map information into a text file
	 * 
	 * @param map
	 * @throws IOException
	 */
	public void writeFile(String name) throws IOException{
		File file = new File(folderName + "/" + name + ".txt");
		FileOutputStream fout = new FileOutputStream(file);

		StringBuffer results = new StringBuffer();
		String data = "";
		String nextLine = System.getProperty("line.separator");

		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		data+= map.getWidthOfMap() + nextLine;
		data+= map.getHeightOfMap() + nextLine;
		data+= map.getInputCorner() + nextLine;

		for (int i = 0; i < mapArray.length; ++i){
			for (int j = 0; j < mapArray[i].length; ++j){
				results.append(mapArray[i][j]).append(" ");
			}
			results.append(nextLine);
		}
		data+= results;

		try {
			fout.write(data.getBytes());
			fout.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return width of customized map
	 */
	public int getWidthFromFile(){
		return width;
	}
	
	/**
	 * 
	 * @return height of customized map
	 */
	public int getHeightFromFile(){
		return height;
	}
	
	public String getUserInput(){
		return userInput;
	}
	/**
	 * 
	 * @return user's input 
	 */
	public int[][] getUserInputFromFile(){
		return map.getCornersList();
	}
	
	/**
	 * Print
	 */
	public String toString(){
		return map.toString();
	}
}