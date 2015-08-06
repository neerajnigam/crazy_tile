package com.guts.tile;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.curvegames.crazyballs.game.MyGdxGame;


public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Tile";
		cfg.width = 480;
		cfg.height = 800;
		
		new LwjglApplication(new MyGdxGame(null), cfg);
	}
}
