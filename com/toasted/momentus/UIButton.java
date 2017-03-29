package com.toasted.momentus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class UIButton extends UIObject {

	private static final UIAction NULL = null;
	//public static final Color WHITE = new Color();
	//public static final int center = 1 << 0;
	String buttext;
	//public GlyphLayout layout = new GlyphLayout(Momentus.opfont, buttext, WHITE, 128, center, false);
	//final float fontX = x + (w - layout.width)/2;
	//final float fontY = x + (h - layout.height)/2;
	UIAction action;
	public UIButton(float x, float y) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(SpriteBatch b) {
		b.draw(img, x, y);
		if (buttext != null){
		Momentus.opfont.draw(b, buttext, x, y);
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
		if (action != NULL){
			action.doAction();
		}
	}
}
