package com.curvegames.crazyballs.gameobjects;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;


public class Obstacle {
	private static final int radius = 20;
	private Vector2 pos, velocity;
	public static Vector2 acceleration = new Vector2(0, 150);
	private Circle c = new Circle();
	private Random random = new Random();
	Color color ;
	
	//private final int ypos = MainWorld.screenHeight - 550;

	public Vector2 getPos() {
		return pos;
	}

	public Obstacle(boolean left) {
		float speed = (75 * (random.nextInt(6)+1)) / getTime();
		if (left){
			pos = new Vector2(-radius-2 , 90);
		}else{
			pos = new Vector2( 480, 90);
		}
		
		velocity = new Vector2(left ? speed : -speed, 0);
		color = generateColor();
	}
	
	public Color getColor(){
		return color;
	}

	private float getTime() {
		float t = (float) Math.sqrt(1100 / acceleration.y);
		return t;
	}
	public static void speedUp(){
		if (acceleration.y > 599)
			return;
		else
			acceleration.add(new Vector2(0,100));
	}
	
	public static void setDefaultAcceleration(){
		acceleration = new Vector2(0,150);
	}
	
	public void update(float delta) {
		velocity.add(acceleration.cpy().scl(delta));
		pos.add(velocity.cpy().scl(delta));
		setC(new Circle(pos.x , pos.y,radius));
	}
	
	public void updateFalling(float delta){
		velocity = new Vector2(0 , 450);
		pos.add(velocity.cpy().scl(delta));
		setC(new Circle(pos.x,pos.y,radius));
	}
	
	public Color generateColor(){
		Color color = null;
		switch (random.nextInt(5)){
		case 0:
			color = Color.CYAN;
			break;
		case 1:
			color = Color.GREEN;
			break;
		case 2:
			color = Color.OLIVE;
			break;
		case 3:
			color = Color.ORANGE;
			break;
		case 4:
			color = Color.RED;
			break;
			
		}
		return color;
	}

	public Circle getC() {
		return c;
	}

	private void setC(Circle c) {
		this.c = c;
	}
}
