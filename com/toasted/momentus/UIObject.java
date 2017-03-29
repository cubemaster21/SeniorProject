package com.toasted.momentus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class UIObject {
	public float x, y, w, h;
	public TextureRegion img;
	public float cntr = (float) 2.5;
	public UIObject(float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public boolean contains(float xc, float yc){
		return xc > x && xc < x + w && yc > y && yc < y + h;
	}
	public abstract void onClick();
	public void draw(SpriteBatch b){
		b.draw(img, x, y);
		
	}
}
