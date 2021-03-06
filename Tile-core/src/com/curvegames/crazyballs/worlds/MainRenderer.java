package com.curvegames.crazyballs.worlds;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.curvegames.crazyballs.gameobjects.Obstacle;
import com.curvegames.crazyballs.gameobjects.Tile;
import com.curvegames.crazyballs.helper.AssetLoader;
import com.curvegames.crazyballs.ui.TextButton;

public class MainRenderer {

	// ======= Contants ==========
	private ShapeRenderer renderer;
	private SpriteBatch batcher;
	private MainWorld world;
	private TextureRegion tileLogo , icon;
	private TextButton play;

	private LinkedList<Obstacle> obstacles;
	private Tile tile = new Tile();
	private OrthographicCamera cam;
	private GlyphLayout glayout;

	public MainRenderer(MainWorld world) {
		this.world = world;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, MainWorld.gameWidth, MainWorld.gameHeight);
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(cam.combined);
		setTile(world.getTile());
		setObstacles(world.getObstacles());
		initButtons();

	}

	public GlyphLayout getGlayout() {
		return glayout;
	}

	public void setGlayout(GlyphLayout glayout) {
		this.glayout = glayout;
	}

	public void initButtons() {
		tileLogo = AssetLoader.tileLogo;
		icon = AssetLoader.icon;
	}

	public void render(float delta) {
		world.updateRunning(delta);

		// Here on drawing Texture
		draw();

		if (world.isRunning()) {
			drawRunning();
		} else if (world.isMenu()) {
			drawMenu();
		} else if (world.isReady()) {
			drawReady();
		} else if (world.isGameover()) {
			drawGameover();
		}
		world.drawButtons(batcher);
	}

	public void draw() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	public void drawMenu() {
		batcher.begin();
		batcher.draw(tileLogo, (480 - tileLogo.getRegionWidth()) / 2, 280, 300, 70);
		batcher.draw(icon, 180, 140, 120,120 );
		batcher.end();
	}

	public void drawReady() {
		batcher.begin();
		AssetLoader.font.getData().setScale(.5f, -.5f);
		AssetLoader.font.draw(batcher, "tap anywherer to start", 58, 250);
		batcher.end();
		// Gdx.app.log("ready","called");
		// ============ Draw Tile ==============
		//drawTile();
	}

	public void drawGameover() {

		for (int i = 0; i < world.getObstacles().size(); i++) {
			drawObstacle(renderer, i);
			drawTile();
			drawScoreTop(batcher);
		}
		if(world.getObstacles().size()<=0){
			batcher.begin();
			String fontText;
			AssetLoader.font.getData().setScale(1f, -1f);
			fontText = "* game over *";
			setGlayout(new GlyphLayout(AssetLoader.font, fontText));
			AssetLoader.font.draw(batcher, fontText, (480 - getGlayout().width) / 2, 140);
			AssetLoader.font.getData().setScale(.6f, -.6f);
			fontText = "- score -"; // ----- Score ---------
			setGlayout(new GlyphLayout(AssetLoader.font, fontText));
			AssetLoader.font.draw(batcher, fontText, (480 - getGlayout().width) / 2, 250);
			fontText = "" + world.getScore();
			setGlayout(new GlyphLayout(AssetLoader.font, fontText));
			AssetLoader.font.draw(batcher, fontText, (480 - getGlayout().width) / 2, 295);
			AssetLoader.font.getData().setScale(.5f, -.5f);
			fontText = "- BEST -"; // ------- BEST -------------
			setGlayout(new GlyphLayout(AssetLoader.font, fontText));
			AssetLoader.font.draw(batcher, fontText, (480 - getGlayout().width) / 2, 370);
			fontText = "" + AssetLoader.getHighScore();
			setGlayout(new GlyphLayout(AssetLoader.font, fontText));
			AssetLoader.font.draw(batcher, fontText, (480 - getGlayout().width) / 2, 415);
			batcher.end();
		}
		
	}

	public void drawRunning() {
		// draw Obstacle
		for (int i = 0; i < world.getObstacles().size(); i++) {
			drawObstacle(renderer, i);
		}

		// ============ Draw Tile ==============
		drawTile();

		drawScoreTop(batcher);

	}

	private void drawObstacle(ShapeRenderer renderer, int i) {
		Circle c = world.getObstacles().get(i).getC();
		renderer.begin(ShapeType.Filled);
		renderer.setColor(world.getObstacles().get(i).getColor());
		renderer.circle(c.x, c.y, c.radius);
		renderer.end();
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLACK);
		renderer.circle(c.x, c.y, c.radius);
		renderer.end();
	}

	private void drawTile() {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(Color.BLACK);
		renderer.rect(tile.getPosition(), Tile.ypos, Tile.getWidth(), Tile.getHeight());
		renderer.end();

	}

	private void drawScoreTop(SpriteBatch Batcher) {
		// =====================================
		// ========= Displays Score =========
		// =====================================
		batcher.begin();
		int length = ("" + world.getScore()).length();
		AssetLoader.font.getData().setScale(.5f, -.5f);
		AssetLoader.font.draw(batcher, "" + world.getScore(), 480 - (27 * length), 10);
		batcher.end();
		// ------------------------------------------------
	}

	public Tile getTile() {
		return tile;
	}

	public MainWorld getWorld() {
		return world;
	}

	public TextButton getPlay() {
		return play;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public LinkedList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(LinkedList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}
}
