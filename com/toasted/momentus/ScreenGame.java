package com.toasted.momentus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;

public class ScreenGame extends Screen{
	SpriteBatch batch;
	
//	Box2DDebugRenderer debugRenderer;
//	OrthographicCamera cam;
	OrthographicCamera physCam;
	
//	Sprite ballSprite;
	
	ArrayList<Effect> effects = new ArrayList<Effect>();
	Level level;
	
	
	
	public ScreenGame(Level l){
		this.level = l;
		Gdx.input.setInputProcessor(this);
		Box2D.init();
		physCam = new OrthographicCamera(9, 16);
		physCam.position.set(physCam.viewportWidth / 2, physCam.viewportHeight / 2, 0);
		physCam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(Momentus.cam.combined);
		
		level.setEffectsManager(effects);
	
		
		
		
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		//update level
		level.update(delta);
		for(int i = 0;i < effects.size();i++){
			if(!effects.get(i).alive){
				effects.remove(i);
				i--;
				continue;
			}
			effects.get(i).update(delta);
		}
		if(level.timeLeft <= 0){
			returnToLevelEditor();
		}
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		//draw level
		level.draw(batch);
		
		GlyphLayout scoreLayout = new GlyphLayout();
		scoreLayout.setText(Momentus.font, "" + level.score);
		Momentus.font.draw(batch, scoreLayout, Momentus.cam.viewportWidth / 2 - scoreLayout.width / 2, Momentus.cam.viewportHeight - 1.2f * scoreLayout.height);
		
		scoreLayout.setText(Momentus.font, "" + (int)level.timeLeft);
		Momentus.font.draw(batch, scoreLayout, Momentus.cam.viewportWidth / 2 - scoreLayout.width / 2, 0.2f * scoreLayout.height);
		for(Effect e: effects){
			e.draw(batch);
		}
		
		batch.end();
	}
	
	Vector2 temp = new Vector2();
	
	public Vector2 unprojectScreenCoords(int x, int y) {
		Vector3 t = Momentus.cam.unproject(new Vector3(x, y, 0));
		t = Momentus.cam.unproject(t);
		return temp.set(t.x, t.y);
	}
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		if(super.touchDown(screenX, screenY, pointer, button)) return true;
		return false;
	}
	public boolean keyUp(int keycode){
		if(keycode == Keys.BACK || keycode == Keys.BACKSPACE){
			returnToLevelEditor();
		}
		return false;
	}
	public void returnToLevelEditor(){
		level.reset();
		effects.clear();
		level.resetEffectsManager();
		Momentus.setScreen(new ScreenLevelEditor(level));
	}
}
