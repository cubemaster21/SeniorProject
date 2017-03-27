package com.toasted.momentus;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class EffectTextShine extends Effect{
	private String text;
	private float x, y, life, fullLife;
	public EffectTextShine(String text, float x, float y, float life){
		this.text = text;
		this.x = x;
		this.y = y;
		this.life = life;
		this.fullLife = life;
	}
	
	public void update(float delta) {
		life -= delta;
		if(life <= 0){
			alive = false;
			life = 0;
		}
		
	}
	public void draw(Batch b){
		Momentus.hitFont.setColor(1, 1, 1, life / fullLife);
		GlyphLayout gl = new GlyphLayout();
		gl.setText(Momentus.hitFont, text);
		
		
		Momentus.hitFont.draw(b, gl, x - gl.width / 2, y - gl.height / 2 + ((1f - (life / fullLife)) * 50f));
		Momentus.hitFont.setColor(1, 1, 1, 1);
	}

}
