package com.toasted.momentus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UIButton extends UIObject {

	private static final UIAction NULL = null;
	String buttext;
	UIAction action;
	public UIButton(float x, float y) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(SpriteBatch b) {
		// TODO Auto-generated method stub
		b.draw(img, x, y);
	}
	
	public TextureRegion getimg(){
		return img;
	}
	
	public void setimg(TextureRegion pic){
		img = pic;
		w = img.getRegionWidth();
		h = img.getRegionHeight();
	}
	
	public String getbuttext(){
		return buttext;
	}

	public void setbuttext(String butname){
		buttext = butname;
	}
	
	public UIAction getaction(){
		return action;
	}
	
	public void setaction(UIAction act){
		action = act;
	}
	
	public void onClick(){
		if (action != NULL){
			action.doAction();
		}
	}
}
