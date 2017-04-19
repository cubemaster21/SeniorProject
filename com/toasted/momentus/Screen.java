package com.toasted.momentus;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public abstract class Screen implements InputProcessor{
	public ArrayList<UIObject> uiElements = new ArrayList<UIObject>();
	
	public Screen(){
		
	}
	public abstract void update(float delta);
	public abstract void draw();
	public abstract Vector2 unprojectScreenCoords(int x, int y);
	public boolean touchDown(int x, int y, int pointer, int button){
		Vector2 unproj = unprojectScreenCoords(x, y);
		for(UIObject uio: uiElements){
			if(uio.contains(unproj.x, unproj.y)){
				if(uio instanceof UIButton){
					((UIButton)uio).onClick();
				}
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * These methods are required by the InputProcessor interface, but are not required for a touchScreen only game,
	 * so we're leaving them with empty definitions
	 */
	public boolean keyDown(int keycode){
		return false;
	}
	public boolean keyUp(int keycode){
		return false;
	}
	public boolean keyTyped (char character){
		return false;
	}
	public boolean mouseMoved (int screenX, int screenY){
		return false;
	}
	public boolean scrolled (int amount){
		return false;
	}
	public float getWidth(){
		return Momentus.cam.viewportWidth;
	}
	public float getHeight(){
		return Momentus.cam.viewportHeight;
	}
}
