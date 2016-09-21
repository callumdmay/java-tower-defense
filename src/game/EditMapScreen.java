package game;


import grid.MapTile;
import grid.PathTile;
import grid.Tile;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import map.Map;
import map.MapEditor;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EditMapScreen extends BasicGameState {
	//test test
	Image SandTile;
	Image ExitButtonGraphic;
	Image CreateMapButtonGraphic;
	Image BlackTileBoundaryGraphic;
	Image StartingPointGraphic;
	Image ExitPointGraphic;
	Image BluePathTileGraphic;
	Image SandTileGraphic;
	Image GravelTileGraphic;
	Image SaveMapButtonGraphic;

	
	private final int mouseClickDelay = 200;
	private long lastClick=(-1*mouseClickDelay);

//	ArrayList<int[]> mapPoints = new ArrayList<int[]>(); 
	ArrayList<Integer> mapPoints = new ArrayList<Integer>();
	Rectangle ExitGameButton;
	Rectangle CreateMapButton;
	Rectangle SaveMapButton;
	ArrayList<Rectangle> buttonList = new ArrayList<Rectangle>();
	
	
	TextField mapWidthTextField;
	TextField mapHeightTextField;
	TextField mapNameTextField;

	Map userCreatedMap;
	MapEditor saveMap;
	
	Font font ;
	TrueTypeFont ttf;

	public final String widthString = "Enter Map Width:";
	public final String heightString = "Enter Map Height:";
	public final String nameString = "Enter Map Name: ";
	public static String statusString= "";

	private final int minimumMapDimension = 12;
	private final int maximumMapDimension = 20;

	public final int mapDrawOffsetX = 96;
	public final int mapDrawOffsetY = 128;

	private final int mapNameMaximumLength = 15;

	private static int mapWidthInput=0;
	private static int mapHeightInput=0;
	private static String mapNameInput ="";
	
	private int selectedTileX=-1;
	private int selectedTileY=-1;
	private int[] exitPoint;
	

	boolean mapInputAccepted = false;
	boolean startingPointAccepted=false;
	boolean exitPointAccepted = false;
	boolean mapCreated = false;

	public EditMapScreen (int state){

	}

	@Override
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		
		loadImages();


		font = new Font("Verdana", Font.PLAIN, 12);
		ttf = new TrueTypeFont(font, true);

		mapWidthTextField = new TextField(container, ttf , 40,60,60,20);
		mapHeightTextField = new TextField(container, ttf, 40 +ttf.getWidth(widthString)+10, 60, 60, 20);
		mapNameTextField = new TextField (container, ttf, 40 +ttf.getWidth(widthString)+10 , 10,100,20);


	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

		if(mapInputAccepted){
			mapWidthTextField.setAcceptingInput(false);
			mapHeightTextField.setAcceptingInput(false);
			mapNameTextField.setAcceptingInput(false);
		}



		if(Mouse.isButtonDown(0)){
			if(mouseOnMap(Mouse.getX(),container.getHeight()-Mouse.getY())){
				mapGridClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), sbg, container);
			}
			else{
				try {
					mouseClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), sbg, container);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		drawBackGroundAndButtons(container);
	

		ttf.drawString(mapWidthTextField.getX(), mapWidthTextField.getY()+mapWidthTextField.getHeight()+10, statusString, Color.black);

		g.setColor(Color.white);

		if(mapInputAccepted){
			drawMap(container);
			ttf.drawString(30, 0, ""+userCreatedMap.ValidityOfMap());
			if(mouseOnMap(Mouse.getX(),container.getHeight()-Mouse.getY())){
				if(!startingPointAccepted)	
					StartingPointGraphic.drawCentered(getClosestTileCenter(Mouse.getX()), container.getHeight() - getClosestTileCenter(Mouse.getY()));
				else if(!exitPointAccepted )
					ExitPointGraphic.drawCentered(getClosestTileCenter(Mouse.getX()), container.getHeight() - getClosestTileCenter(Mouse.getY()));		
			}

			if(startingPointAccepted){
//				int[] startingpoint =mapPoints.get(0);
				int[] startingpoint = new int[] {mapPoints.get(0), mapPoints.get(1)};
				StartingPointGraphic.drawCentered(startingpoint[0]*32 +mapDrawOffsetX +16, startingpoint[1]*32 +mapDrawOffsetY +16);
			}
			if(exitPointAccepted){
				
				ExitPointGraphic.drawCentered(exitPoint[0]*32 +mapDrawOffsetX +16, exitPoint[1]*32 +mapDrawOffsetY +16);
			}
			if(!mapCreated){
			//create the mapgrid
			generateMapGrid();
			}
			if (!mapCreated)
				SaveMapButtonGraphic.drawCentered(container.getWidth()/2 , container.getHeight() - SaveMapButtonGraphic.getHeight()/2 -10 );
		}
		else{
			ttf.drawString(40, 40, widthString, Color.black);
			ttf.drawString(40 +ttf.getWidth(widthString)+10, 40, heightString, Color.black);
			ttf.drawString(40, 10, nameString, Color.black);
			CreateMapButtonGraphic.draw(mapHeightTextField.getX() + ttf.getWidth(widthString)+10, 40 + mapWidthTextField.getHeight()/2 );

		}


		mapWidthTextField.render(container, g);
		mapHeightTextField.render(container, g);
		mapNameTextField.render(container, g);
	}





	public void drawBackGroundAndButtons(GameContainer container){
		for(int x = 0; x <container.getWidth(); x+=SandTile.getWidth()){
			for(int y = 0 ; y< container.getHeight(); y+=SandTile.getHeight()){
				SandTile.draw(x,y);
			}
		}

		ExitButtonGraphic.draw(container.getWidth()-ExitButtonGraphic.getWidth(), container.getHeight()-ExitButtonGraphic.getHeight()-2);
	}
	
		
	public void drawMap(GameContainer container){
		for(int i = 0 ; i < userCreatedMap.getWidthOfMap(); i++){
			for(int j = 0 ; j < userCreatedMap.getHeightOfMap() ; j++){
				
					if (userCreatedMap.getTile(i, j) instanceof PathTile){	
						GravelTileGraphic.draw(mapDrawOffsetX +i * userCreatedMap.getPixelSize(), mapDrawOffsetY + j * userCreatedMap.getPixelSize());
						continue;
					}
					if (userCreatedMap.getTile(i, j) instanceof MapTile){		
						SandTileGraphic.draw(mapDrawOffsetX +i * userCreatedMap.getPixelSize(), mapDrawOffsetY + j * userCreatedMap.getPixelSize());
						continue;
					}

				}
			}
	
		}
	

	public void loadImages() throws SlickException{
		SandTile = new Image("graphics/SandTile.png");
		ExitButtonGraphic = new Image ("graphics/ExitButton.png");
		CreateMapButtonGraphic = new Image("graphics/CreateMapButtonGraphic.png");
		BlackTileBoundaryGraphic = new Image("graphics/BlackTileBoundaryGraphic.png");
		StartingPointGraphic = new Image("graphics/StartingPointGraphic.png");
		ExitPointGraphic = new Image("graphics/ExitPointGraphic.png");
		BluePathTileGraphic = new Image("graphics/BluePathTileGraphic.png");
		SandTileGraphic = new Image("graphics/SandTile.png");
		GravelTileGraphic = new Image("graphics/GravelTile.png");
		SaveMapButtonGraphic = new Image("graphics/SaveMapButtonGraphic.png");
		
	}

	public float getClosestTileCenter(float X){

		return (float) (Math.floor(X / 32) * 32 + 32 / 2);
	}

	public boolean mouseOnMap(int x, int y){
		if(x>mapDrawOffsetX && x < mapDrawOffsetX +32*mapWidthInput && y> mapDrawOffsetY && y < mapDrawOffsetY +32*mapHeightInput){
			return true;
		}
		else
			return false;
	}


	public void mapGridClicked(int x, int y, StateBasedGame sbg, GameContainer container){

		if(lastClick + mouseClickDelay > System.currentTimeMillis())
			return;
		lastClick = System.currentTimeMillis();

		if(mapCreated)
			return;

		
		int xLoc = (int) Math.floor((x-mapDrawOffsetX) / 32);
		int yLoc = (int) Math.floor((y-mapDrawOffsetY) / 32);

		if(!startingPointAccepted){
			if(xLoc == 0 || xLoc == mapWidthInput-1 ||yLoc == 0 || yLoc == mapHeightInput-1){

				userCreatedMap.placeEntry(xLoc, yLoc);

				mapPoints.add(xLoc);
				mapPoints.add(yLoc);
				selectedTileX = xLoc;
				selectedTileY = yLoc;
				startingPointAccepted = true;
				statusString = "Select Exit Point";
				return;
			}
			else
				statusString = "Invalid Starting Point. Starting points can only be placed on the edges of the map";
			return;

		}
		if(!exitPointAccepted){

			int[] startingPoint = new int[] {mapPoints.get(0), mapPoints.get(1)};
			if(xLoc == startingPoint[0] &&yLoc ==startingPoint[1]){
				statusString = "Cannot place Exit point on starting point";
				return;
			}
			if(xLoc == 0 || xLoc == mapWidthInput-1 ||yLoc == 0 || yLoc == mapHeightInput-1 ){
				userCreatedMap.placeExit(xLoc, yLoc);
				
				exitPoint = new int[] {xLoc, yLoc};
				exitPointAccepted = true;
				statusString = "Select any point on a blue line";
				return;
			}
			else
				statusString = "Invalid Exit Point. Exit points can only be placed on the edges of the map";
			return;
		}
		
		if(exitPointAccepted&&startingPointAccepted){
			if(xLoc==selectedTileX ||yLoc == selectedTileY){

				userCreatedMap.linkTwoPoints(new PathTile(selectedTileX, selectedTileY), new PathTile(xLoc, yLoc));

				mapPoints.add(xLoc);
				mapPoints.add(yLoc);
				selectedTileX = xLoc;
				selectedTileY = yLoc;
			}
		}

		
	}



	public void mouseClicked(int x, int y, StateBasedGame sbg, GameContainer container) throws SlickException, IOException{

		//protection against multiple click registration
				if(lastClick + mouseClickDelay > System.currentTimeMillis())
					return;
				lastClick = System.currentTimeMillis();
		
		
		if(ExitGameButton.contains(x, y)){
			Mouse.getDY();
			AppGameContainer gameContainer = (AppGameContainer) container;
			gameContainer.setDisplayMode(640, 480, false);
			reInitialize();
			sbg.enterState(Game.menuScreen);
		}

		if(CreateMapButton.contains(x, y)){
			statusString ="";
			try{
				mapWidthInput = Integer.parseInt(mapWidthTextField.getText());
				mapHeightInput = Integer.parseInt(mapHeightTextField.getText());
				mapNameInput = mapNameTextField.getText();
			}
			catch(NumberFormatException e){
				statusString = "Illegal input format";
			}
			if(mapWidthInput <minimumMapDimension || mapHeightInput <minimumMapDimension)
			{
				statusString = "Map to small. Minimum dimensions are "+minimumMapDimension+"x"+minimumMapDimension;
				mapInputAccepted = false;
			}
			else if (mapWidthInput > maximumMapDimension || mapHeightInput > maximumMapDimension){
				statusString = "Map to large. Maximum dimensions are "+maximumMapDimension+"x"+maximumMapDimension;
				mapInputAccepted = false;
			}
			else if(mapNameInput.equals("")){
				statusString = "Map name cannot be null";
				mapInputAccepted = false;
			}
			else if (mapNameInput.length()>mapNameMaximumLength){
				statusString = "Map name is too long";
				mapInputAccepted = false;
			}
			else{
				userCreatedMap = new Map();
				userCreatedMap.setMapSize(mapWidthInput, mapHeightInput);
				userCreatedMap.initializeMap();
				mapInputAccepted = true;
				statusString = "Select a starting location";
			}

		}

		if(SaveMapButton.contains(x,y) && !mapCreated ){
			if(mapInputAccepted && exitPointAccepted && startingPointAccepted && mapPoints.get(mapPoints.size()-2)== exitPoint[0] && mapPoints.get(mapPoints.size()-1)==exitPoint[1]){
				saveMap = new MapEditor(mapWidthInput, mapHeightInput, userCreatedMap.arrangePathPoint(mapPoints));
				saveMap.writeFile(mapNameInput);
				mapCreated = true;
				statusString = "Map successfully created";

			}
			else{
				statusString = "Invalid Map. Please try again";
				reInitializeMapPathCreation();
			}
		}


	}

	public void reInitializeMapPathCreation(){
		mapPoints = new ArrayList<Integer>();
		startingPointAccepted=false;
		exitPointAccepted = false;
		mapCreated = false;
		userCreatedMap = new Map();
		userCreatedMap.setMapSize(mapWidthInput, mapHeightInput);
		userCreatedMap.initializeMap();
	}

	public void generateMapGrid(){

		int currentX = mapDrawOffsetX;
		int currentY = mapDrawOffsetY;
		for(int y = 0 ; y < mapHeightInput; y++){
			for(int x = 0 ; x < mapWidthInput; x++ ){
				BlackTileBoundaryGraphic.draw(currentX, currentY);
				if(mapInputAccepted&&startingPointAccepted&&exitPointAccepted)
					if(x ==selectedTileX ||y == selectedTileY)
						BluePathTileGraphic.draw(currentX, currentY);
				currentX+=32;
			}
			currentY+=32;
			currentX=mapDrawOffsetX;
		}

	}


	public void reInitialize(){
		mapWidthTextField.setText("");
		mapWidthTextField.setAcceptingInput(true);

		mapHeightTextField.setText("");
		mapHeightTextField.setAcceptingInput(true);
		
		mapNameTextField.setText("");
		mapNameTextField.setAcceptingInput(true);

		mapWidthInput = 0;
		mapHeightInput = 0;
		mapNameInput ="";

//		mapPoints = new ArrayList<int[]>();
		mapPoints = new ArrayList<Integer>();
		mapInputAccepted = false;
		startingPointAccepted=false;
		exitPointAccepted = false;
		mapCreated = false;
		statusString ="";
	}

	public void createRectangleButtons(GameContainer container){
		CreateMapButton = new Rectangle(mapHeightTextField.getX() + ttf.getWidth(widthString)+10, 40 + mapWidthTextField.getHeight()/2 ,CreateMapButtonGraphic.getWidth(), CreateMapButtonGraphic.getHeight());
		ExitGameButton = new Rectangle(container.getWidth()-ExitButtonGraphic.getWidth(), container.getHeight()-ExitButtonGraphic.getHeight()-2, ExitButtonGraphic.getWidth(),ExitButtonGraphic.getHeight());
		SaveMapButton = new Rectangle(container.getWidth()/2 - SaveMapButtonGraphic.getWidth()/2, container.getHeight() - SaveMapButtonGraphic.getHeight() -10, SaveMapButtonGraphic.getWidth(), SaveMapButtonGraphic.getHeight());
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
