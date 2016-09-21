package grid;

/**
 * PathTiles are the components of the design path
 * 
 * @author		Wei Wang
 * @version		1.0
 * @since		2015-02-28
 */

public class PathTile extends Tile{
	
	private PathTile nextTile;
	
	/**
	 * Create new PathTile based on given coordinates
	 * 
	 * @param x		X-coordinate related to the one of Map
	 * @param y		Y-coordinate related to the one of Map
	 * @param type	Type of Path beside the entry and exit is set as "------"
	 */
	public PathTile(int x, int y) {
		super(x, y);
		
		super.setType(1);

		nextTile = null;
	}
	
	/**
	 * Make a link between the current PathTile and the next one
	 * 
	 * @param nextTile		The next PathTile according to the current one
	 */
	public void setNextTile(PathTile nextTile) {
		this.nextTile = nextTile;
	}
	
	/**
	 * Lead the pointer to the next PathTile
	 * 
	 * @return the next linked PathTile
	 */
	public PathTile getNextTile() {
		return nextTile;
	}
}
