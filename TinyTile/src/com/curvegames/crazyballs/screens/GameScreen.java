package com.curvegames.crazyballs.screens;

import com.badlogic.gdx.Screen;
import com.curvegames.crazyballs.worlds.MainRenderer;
import com.curvegames.crazyballs.worlds.MainWorld;

public class GameScreen implements Screen {
	private MainRenderer mainrenderer;
	private MainWorld world;
	public GameScreen() {
		// initialize objects

		world = new MainWorld();
		 
		mainrenderer = new MainRenderer(world );
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		world.update(delta);
		mainrenderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
