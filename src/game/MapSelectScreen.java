package game;



import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import map.*;


public class MapSelectScreen extends BasicGameState {

	Image SandTile;

	ArrayList<Map> mapList;
	ArrayList<Rectangle> mapButtonList;
	private final int rectangleWidth = 150;
	private final int rectangleHeight = 30;
	private final int sideMenuWidth = 192;
	private final int bottomMenuWidth = 128;
	private final int mapButtonXInitialOffset = 20;
	private final int mapButtonYInitialOffset = 100;
	private final String selectMapString = "Please select a map to play";
	Font font ;
	TrueTypeFont ttf;

	LoadFile loading;
	
	public MapSelectScreen (int state){

	}
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {



		SandTile = new Image("graphics/SandTile.png");
		font = new Font("Verdana", Font.BOLD, 30);
		ttf = new TrueTypeFont(font, true);



	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		drawMapBackground(container);
		drawMapButtons(g);

		//draw the title string
		ttf.drawString(container.getWidth() / 2 - ttf.getWidth(selectMapString) / 2, 30, selectMapString, Color.black);

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

		//listen for mouse input
		if(Mouse.isButtonDown(0))
			mouseClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), sbg, container);
		

	}

	
	
	public void drawMapBackground(GameContainer container){
		for(int x = 0; x <container.getWidth(); x+=SandTile.getWidth()){
			for(int y = 0 ; y< container.getHeight(); y+=SandTile.getHeight()){
				SandTile.draw(x,y);
			}
		}

	}

	public void drawMapButtons(Graphics g){
		for(int count = 0 ; count<mapButtonList.size() ; count++){
			g.setColor(Color.black);
			g.draw(mapButtonList.get(count));
			g.drawString(loading.getListofFiles().get(count) , mapButtonList.get(count).getMinX()+10, mapButtonList.get(count).getMinY()+rectangleHeight/4);	
		}
	}
	
	
	public void initializeAndLoadMaps(){
		mapButtonList = new ArrayList<Rectangle>();
		mapList = new ArrayList<Map>();
		loading = new LoadFile();

		mapList.addAll(loading.getAllMap());

	}
	
	//create rectangle button for each map
	public void createRectangleMapButtons(GameContainer container){
		int mapX = mapButtonXInitialOffset;
		int mapY = mapButtonYInitialOffset;
		for(Map s : mapList){
			Rectangle rectangle = new Rectangle(mapX, mapY, rectangleWidth, rectangleHeight);
			mapX +=(rectangleWidth+30) ;
			if(mapX>container.getWidth()-rectangleWidth){
				mapX=mapButtonXInitialOffset;
				mapY+=rectangleHeight+30;
			}
			mapButtonList.add(rectangle);

		}

	}

	public void mouseClicked(int x, int y, StateBasedGame sbg, GameContainer container) throws SlickException{
		for(int count = 0 ; count < mapButtonList.size() ; count++){
			//compare if the click occurred inside one of the rectangle buttons, 
			//if it did, load that map, change the frame size to match the map size, and switch states to the PlayScreen state
			if(mapButtonList.get(count).contains(x, y)){
				PlayScreen s = (PlayScreen) sbg.getState(Game.playScreen);
				s.setMap(mapList.get(count));
				AppGameContainer gameContainer = (AppGameContainer) container;
				gameContainer.setDisplayMode(mapList.get(count).getWidthOfMap()*32 +sideMenuWidth, mapList.get(count).getHeightOfMap()*32 +bottomMenuWidth, false);
				s.createRectangleButtons(gameContainer);
				sbg.enterState(Game.playScreen);
			}
		}
	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}




}
