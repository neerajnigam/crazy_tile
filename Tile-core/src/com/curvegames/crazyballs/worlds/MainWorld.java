package com.curvegames.crazyballs.worlds;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.curvegames.crazyballs.gameobjects.Obstacle;
import com.curvegames.crazyballs.gameobjects.Tile;
import com.curvegames.crazyballs.helper.AssetLoader;
import com.curvegames.crazyballs.ui.TextButton;

public class MainWorld implements InputProcessor {

	private Tile tile;
	private LinkedList<Obstacle> obstacles = new LinkedList<Obstacle>();
	private int score = 0;
	public static final int gameWidth = 480;
	public static final int gameHeight = 800;
	public float screenWidth = Gdx.graphics.getWidth();
	public float screenHeight = Gdx.graphics.getHeight();

	private TextButton play, retry, soundButton, mainMenu;
	private GameState currentState;

	// ---------- Game State and Constructor ----------------------------
	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
	}

	public MainWorld() {
		currentState = GameState.MENU;
		tile = new Tile();
		inibuttons();
		Gdx.input.setInputProcessor(this);
		// --------- uncomment for setting high score --------
		// AssetLoader.setHighScore(0);
	}

	private void inibuttons() {

		play = new TextButton("play!", "play!", 460, .7f);
		retry = new TextButton("Retry", "Retry", 520, .7f);
		soundButton = new TextButton("Sound On", "Sound Off", 550, .7f);
		mainMenu = new TextButton("Main Menu", "Main Menu", 600, .7f);
	}
	// --------------------------------------------------

	// ---------- Checks for Current state --------------
	public void update(float delta) {
		switch (currentState) {
		case RUNNING:
			updateRunning(delta);
			break;
			
		case HIGHSCORE:
			updateGameover(delta);

		case GAMEOVER:
			updateGameover(delta);
		default:
			break;
		}
	}

	
	// --------------- Public Void Start Method -----------------------
	public void start() {
		Tile.moveleft = false;
		Tile.moveright = false;
		Obstacle.setDefaultAcceleration();
		createObstacle();
		currentState = GameState.RUNNING;
	}

	// ----------- Restart Method ---------------
	public void restart() {
		score = 0;
		obstacles = new LinkedList<Obstacle>();
		currentState = GameState.READY;
	}

	// ------------- Ready Method ----------------
	public void ready() {
		currentState = GameState.MENU;
	}

	public void updateRunning(float delta) {

		// ========== update tile ==============
		tile.update(delta);
		// ------------------------------------------

		// ========= update obstacles ============
		if (soundButton.getToggleValue()){
			updateObstacleSoundOn(delta);
		}else{
			updateObstacleSoundOff(delta);
		}
		
		// ------------------------------------------
	}	

	public void updateGameover(float delta) {
		Tile.moveleft = false;
		Tile.moveright = false;
		for (int i = 0; i < obstacles.size(); i++) {
			obstacles.get(i).updateFalling(delta);
			if (obstacles.get(i).getPos().y > gameHeight) {
				obstacles.remove(i);
			}
		}
	}

	private void createObstacle() {
		Obstacle o = new Obstacle(true);
		Obstacle o2 = new Obstacle(false);
		obstacles.add(o);
		obstacles.add(o2);
	}

	private void updateObstacleSoundOn(float delta) {
		for (int i = 0; i < obstacles.size(); i++) {
			if (currentState == GameState.RUNNING) {
				obstacles.get(i).update(delta);

				// ==============================================
				// =========== Method For Creating ==============
				// ================ Obstacles ===================
				if (obstacles.get(obstacles.size() - 1).getPos().y > 270) {
					createObstacle();
				}
				// -------------------------------------------------

				// ========================================
				// ========= removes obstacles ============
				// ========================================
				if (obstacles.get(i).getPos().y > gameHeight) {
					obstacles.remove(i);
					setScore(1);

					// ======= Plays Sound ==========
					if (score % 2 > 0)
						AssetLoader.coin.play();

					// ====== Speed Up Obstacle ====
					if (score % 15 == 14) {
						Obstacle.speedUp();
						AssetLoader.speedup.play();

					}
				}
			}

			// =====================================
			// ============= Game Over =============
			// =====================================
			if (Intersector.overlaps(obstacles.get(i).getC(), tile.getTile())) {
				if (!(currentState == GameState.GAMEOVER)) {
					AssetLoader.dead.play();
				}
				currentState = GameState.GAMEOVER;

				if (score > AssetLoader.getHighScore()) {
					currentState = GameState.HIGHSCORE;
					AssetLoader.setHighScore(score);

				}
			}
		}
	}

	// -----------------------------------------------
	
	private void updateObstacleSoundOff(float delta) {
		for (int i = 0; i < obstacles.size(); i++) {
			if (currentState == GameState.RUNNING) {

				obstacles.get(i).update(delta);

				// ==============================================
				// =========== Method For Creating ==============
				// ================ Obstacles ===================
				if (obstacles.get(obstacles.size() - 1).getPos().y > 270) {
					createObstacle();
				}
				// -------------------------------------------------

				// ========================================
				// ========= removes obstacles ============
				// ========================================
				if (obstacles.get(i).getPos().y > gameHeight) {
					obstacles.remove(i);
					setScore(1);

					// ====== Speed Up Obstacle ====
					if (score % 15 == 14) {
						Obstacle.speedUp();
					}
				}
			}

			// =====================================
			// ============= Game Over =============
			// =====================================
			if (Intersector.overlaps(obstacles.get(i).getC(), tile.getTile())) {
				
				currentState = GameState.GAMEOVER;

				if (score > AssetLoader.getHighScore()) {
					currentState = GameState.HIGHSCORE;
					AssetLoader.setHighScore(score);

				}
			}
		}

		
	}

	public void drawButtons(SpriteBatch batcher) {
		batcher.begin();
		if (isMenu()) {
			play.drawClickable(batcher);
			soundButton.drawToggle(batcher);
		} else if (isGameover()) {
			if (getObstacles().size() <= 0) {
				retry.drawClickable(batcher);
				mainMenu.drawClickable(batcher);
			}
		}
		batcher.end();
	}

	// =====================================================
	// --------- Game state return Method ------------------
	// =====================================================
	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public boolean isGameover() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isHighscore() {
		return currentState == GameState.HIGHSCORE;
	}

	// ===================================================
	// -------------- Getter And Setter ------------------
	// ===================================================
	private void setScore(int increment) {
		score += increment;
	}

	public Tile getTile() {
		return tile;
	}

	int getScore() {
		return score;
	}

	public LinkedList<Obstacle> getObstacles() {
		return obstacles;

	}

	// =====================================================
	// ---------- INPUT METHODS --------------------
	// =====================================================

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (isRunning()) {
			if (screenX < 230) {
				Tile.moveleft = true;
			} else if (screenX > 230) {
				Tile.moveright = true;
			}
		} else if (isMenu()) {
			if (play.isClicked(screenX, screenY)) {
				currentState = GameState.READY;
			}

			soundButton.toggleTouch(screenX, screenY);

		} else if (isReady()) {
			start();
		} else if (isGameover()) {

			if (retry.isClicked(screenX, screenY)) {
				restart();
			}

			if (mainMenu.isClicked(screenX, screenY)) {
				currentState = GameState.MENU;
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		if (isRunning()) {
			if (screenX < 230) {
				Tile.moveleft = false;
			} else if (screenX > 230) {
				Tile.moveright = false;
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	// scale touches

	private int scaleX(int screenX) {
		return (int) (screenX * gameWidth / screenWidth);
	}

	private int scaleY(int screenY) {
		return (int) (screenY * gameHeight / screenHeight);
	}
	// ---------------------------------------------------------------

}
