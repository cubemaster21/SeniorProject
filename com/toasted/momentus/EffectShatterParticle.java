package com.toasted.momentus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class EffectShatterParticle extends Effect{
	Vector2 velocity;
	float fullLife, life;
	float spin;
	public EffectShatterParticle(Texture sprite, Vector2 position, Vector2 iVel, float spin, float life){
		super(sprite);
//		this.setTexture(sprite);
		this.setCenter(position.x, position.y);
		fullLife = this.life = life;
		velocity = iVel.cpy();
		this.spin = spin;
	}
	@Override
	public void update(float delta) {
		
		this.rotate(spin * delta);
		this.translate(velocity.x * delta, velocity.y * delta);
		life -= delta;
		if(life <= 0){
			life = 0;
			alive = false;
		}
		this.setAlpha(life / fullLife);
	}
	public void draw(Batch b){
		super.draw(b);
	}

}
