package com.curvegames.crazyballs.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.curvegames.crazyballs.worlds.MainWorld;

public class Tile {
	
	public static final int ypos = MainWorld.gameHeight -200;
	public static boolean moveleft = false;
	public static boolean moveright = false;
	private final static int height = 20, width = 100;
	private static int speedCap = 10;
	private final int reduce = MainWorld.gameWidth - getWidth();
	private float position, speed, acceleration;
	
	private Rectangle tile;
	
	public Tile(){
		position = (MainWorld.gameWidth-width)/2;
		speed = 0f;
		acceleration = 1f;
		
		setTile(new Rectangle(position, ypos, getWidth(), getHeight()));
	}

	public Rectangle getTile() {
		return tile;
	}
	
	public void onRestart(){
		tile.x = 200f;
	}

	private void setTile(Rectangle tile) {
		this.tile = tile;
	}
	
	public void update(float delta) {
		if (moveleft) {
			moveleft(delta);
		}
		if (moveright) {
			moveright(delta);
		}
		setTile(new Rectangle(position, ypos, getWidth(), getHeight()));
	}

	private void moveleft(float delta) {
		if (position <= 0) {
			position = 0;
		} else {
			position -= speed * delta * 60;
		}
		if (speed >= speedCap) {
			speed = speedCap;
		} else {
			speed += acceleration * delta * 60;
		}

		// Gdx.app.log("Tile", "moving left");
	}

	private void moveright(float delta) {
		if (position >= reduce) {
			position = reduce;
		} else {
			position += speed * delta * 60;
		}
		if (speed >= speedCap) {
			speed = speedCap;
		} else {
			speed += acceleration * delta * 60;
		}
		// Gdx.app.log("Tile", "moving right");
	}

	public static void setSpeedCap(int cap) {
		speedCap = cap;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	public float getPosition(){
		return position;
	}
	
}
