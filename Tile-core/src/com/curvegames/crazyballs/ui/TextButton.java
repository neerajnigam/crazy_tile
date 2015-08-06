package com.curvegames.crazyballs.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.curvegames.crazyballs.helper.AssetLoader;

public class TextButton {

	private float x, y, scale;
	private BitmapFont font;
	private String fontText;
	private String fontText2;
	private GlyphLayout g;
	private Rectangle bounds;
	private boolean toggle = true;

	public TextButton(String fontText, String fontText2, float y, float scale) {

		// font text 2 : it displays when toggle is true like sound off.
		this.y = y;
		this.scale = scale;

		this.font = AssetLoader.font;
		font.getData().setScale(scale, -scale);
		this.fontText = fontText;
		this.fontText2 = fontText2;

		g = new GlyphLayout(this.font, this.fontText);
		this.x = (480 - g.width) / 2;
		bounds = new Rectangle(x, y, g.width, -g.height);

	}

	public void drawToggle(SpriteBatch batch) {
		font.getData().setScale(scale, -scale);
		if (toggle) {
			font.draw(batch, fontText, x, y);
		} else {
			font.draw(batch, fontText2, x, y);
		}
	}

	public void drawClickable(SpriteBatch batch) {
		// Method for click able buttons
		// Or say Buttons which do not toggle
		font.getData().setScale(scale, -scale);
		font.draw(batch, fontText, x, y);
	}

	public void toggleTouch(int screenX, int screenY) {
		if (bounds.contains(screenX, screenY)) {
			if (!toggle) {
				toggle = true;
			} else {
				toggle = false;
			}
		}
	}

	public boolean isClicked(int screenX, int screenY) {

		return bounds.contains(screenX, screenY);
	}

	public boolean getToggleValue() {
		return toggle;
	}

}
