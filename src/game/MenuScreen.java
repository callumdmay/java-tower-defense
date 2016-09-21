package game;



import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import critters.Critter;
public class MenuScreen extends BasicGameState{

	Image SandTileGraphic;
	Image StartGameButtonGraphic;
	Image EditMapButtonGraphic;
	Image TowerDefenseTitleGraphic;
	Image ExitButtonGraphic;
	Rectangle StartGameButton;
	Rectangle ExitGameButton;
	Rectangle EditMapButton;
	
	private final String authors = "A Java Game by Callum May, Wei Wang,\nCharles Liu and Robert Zhao";

	private final int mouseClickDelay = 200;
	private long lastClick=(-1*mouseClickDelay);

	public MenuScreen (int state){

	}


	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {

		loadImagesAndAnimations();
		createRectangleButtons(container);

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {


		if(Mouse.isButtonDown(0)){
			mouseClicked(Mouse.getX(), container.getHeight()- Mouse.getY(), sbg, container);
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {

		drawMapAndOverlay(container);
		g.setColor(Color.black);
		g.drawString(authors, 5, container.getHeight()-40);
	}


	public void drawMapAndOverlay(GameContainer container){
		for(int x = 0; x <container.getWidth(); x+=SandTileGraphic.getWidth()){
			for(int y = 0 ; y< container.getHeight(); y+=SandTileGraphic.getHeight()){
				SandTileGraphic.draw(x,y);
			}
		}

		StartGameButtonGraphic.draw(container.getWidth()/2 -StartGameButtonGraphic.getWidth()/2, container.getHeight()/3 -StartGameButtonGraphic.getHeight()/2);
		EditMapButtonGraphic.draw(container.getWidth()/2 -EditMapButtonGraphic.getWidth()/2, container.getHeight()/2 -EditMapButtonGraphic.getHeight()/2);
		TowerDefenseTitleGraphic.draw(container.getWidth()/2 - TowerDefenseTitleGraphic.getWidth()/2, TowerDefenseTitleGraphic.getHeight()/2);
		//ExitButtonGraphic.draw(container.getWidth()-ExitButtonGraphic.getWidth(), container.getHeight()-ExitButtonGraphic.getHeight()-2);

	}

	public void createRectangleButtons(GameContainer container){
		StartGameButton = new Rectangle(container.getWidth()/2 -StartGameButtonGraphic.getWidth()/2, container.getHeight()/3 -StartGameButtonGraphic.getHeight()/2, StartGameButtonGraphic.getWidth(), StartGameButtonGraphic.getHeight());
		ExitGameButton = new Rectangle(container.getWidth()-ExitButtonGraphic.getWidth(), container.getHeight()-ExitButtonGraphic.getHeight()-2, ExitButtonGraphic.getWidth(), ExitButtonGraphic.getHeight());
		EditMapButton = new Rectangle(container.getWidth()/2 -EditMapButtonGraphic.getWidth()/2, container.getHeight()/2 -EditMapButtonGraphic.getHeight()/2, EditMapButtonGraphic.getWidth(), EditMapButtonGraphic.getHeight()); 

	}

	public void loadImagesAndAnimations() throws SlickException{
		SandTileGraphic = new Image("graphics/SandTile.png");
		StartGameButtonGraphic = new Image("graphics/StartGameButton.png");
		EditMapButtonGraphic = new Image("graphics/EditMapButton.png");
		TowerDefenseTitleGraphic = new Image("graphics/TowerDefenseTitle.png");
		ExitButtonGraphic = new Image ("graphics/ExitButton.png");
	}

	public void mouseClicked( float x, float y, StateBasedGame sbg, GameContainer container) throws SlickException{

		//protection against multiple click registration
		if(lastClick + mouseClickDelay > System.currentTimeMillis())
			return;
		lastClick = System.currentTimeMillis();

		if(StartGameButton.contains(x, y)){
			MapSelectScreen s = (MapSelectScreen) sbg.getState(Game.mapSelectScreen);
			s.initializeAndLoadMaps();
			s.createRectangleMapButtons(container);
			
			try {
			    Thread.sleep(300);                
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}

			sbg.enterState(Game.mapSelectScreen);
		}
		if(EditMapButton.contains(x, y)){
			EditMapScreen s = (EditMapScreen) sbg.getState(Game.editMapScreen);
			AppGameContainer gameContainer = (AppGameContainer) container;
			gameContainer.setDisplayMode(832, 832, false);
			s.createRectangleButtons(container);

			sbg.enterState(Game.editMapScreen);

		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}


}
