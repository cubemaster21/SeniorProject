package com.toasted.momentus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class UIButton extends UIObject {

	String buttext;
	public GlyphLayout layout = new GlyphLayout();
//	final float fontX = x + (w - layout.width)/2;
//	final float fontY = x + (h - layout.height)/2;
	UIAction action;
	public UIButton(float x, float y) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(SpriteBatch b) {
		b.draw(img, x, y);
		if (buttext != null){
			layout.setText(Momentus.opfont, buttext);
			Momentus.opfont.draw(b, buttext, x + w / 2 - layout.width / 2, y + h / 2 - layout.height / 2);
		}
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
		if (action != null){
			action.doAction();
		}
	}
}
