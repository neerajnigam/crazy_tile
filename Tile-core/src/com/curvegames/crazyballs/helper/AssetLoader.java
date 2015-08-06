package com.curvegames.crazyballs.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	public static Sound dead;
	public static Sound coin;
	public static Sound speedup;
	public static BitmapFont font;
	public static Preferences prefs;
	public static Texture titletexture, logoTexture , icon_texture;
	public static TextureRegion tileLogo, icon;

	public static void load() {
		//loads Logo as a texture
		logoTexture = new Texture(Gdx.files.internal("data/curve_logo.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		speedup = Gdx.audio.newSound(Gdx.files.internal("data/powerup.wav"));
		// loads sound
		font = new BitmapFont(Gdx.files.internal("data/tile2x.fnt"));
		font.getData().setScale(.5f, -.5f);

		// ------------- loads textures ----------------
		titletexture = new Texture(Gdx.files.internal("data/crazyBalls.png"));
		titletexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		icon_texture = new Texture(Gdx.files.internal("data/IconCrazyBalls.png"));
		icon_texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		icon = new TextureRegion(icon_texture,0,0,512,512);
		icon.flip(false, true);

		tileLogo = new TextureRegion(titletexture, 0, 0, 300, 70);
		tileLogo.flip(false, true);

		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("ZombieBird");
		// Provide default high score of 0
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}

	}

	// Receives an integer and maps it to the String highScore in prefs
	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	// Retrieves the current high score
	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}

	public static void dispose() {
		// TODO Auto-generated method stub
		titletexture.dispose();
		icon_texture.dispose();

		dead.dispose();
		coin.dispose();
		speedup.dispose();
		font.dispose();
	}

}
