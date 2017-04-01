package com.toasted.momentus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Effect extends Sprite{
	boolean alive = true;
	public Effect(){
		super();
	}
	public Effect(Texture t){
		super(t);
	}
	public abstract void update(float delta);
	public void draw(Batch sb){
		super.draw(sb);
	}
}
