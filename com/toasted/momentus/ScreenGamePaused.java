package com.toasted.momentus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;

import sun.java2d.pipe.ShapeSpanIterator;

public class ScreenGamePaused extends Screen{
	SpriteBatch batch;
	ShapeRenderer shape;
	OrthographicCamera physCam;
	
	ArrayList<Effect> effects = new ArrayList<Effect>();
	Level level;
	
	boolean skipNextUpdate = false;
	boolean returnToLevelEditor;
	
	public ScreenGamePaused(final ScreenGame game){
		super();
		shape = new ShapeRenderer();
		this.level = game.level;
		this.effects = game.effects;
		Gdx.input.setInputProcessor(this);
		Box2D.init();
		physCam = new OrthographicCamera(9, 16);
		physCam.position.set(physCam.viewportWidth / 2, physCam.viewportHeight / 2, 0);
		physCam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(Momentus.cam.combined);
		shape.setProjectionMatrix(Momentus.cam.combined);
		level.setEffectsManager(effects);
	
		
		UIButton gohome= new UIButton(Momentus.cam.viewportWidth / 2 - Art.button.getWidth() / 2, 76);
		gohome.setbuttext("Level Select");
		gohome.setimg(new TextureRegion(Art.button));
		gohome.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenLevelSelect());
			}
		});
		
		UIButton resume = new UIButton(Momentus.cam.viewportWidth / 2 - Art.button.getWidth() / 2, 76 + Art.button.getHeight() * 1.3f);
		resume.setbuttext("Resume");
		resume.setimg(new TextureRegion(Art.button));
		resume.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(game);
			}
		});
		
		uiElements.add(resume);
		uiElements.add(gohome);
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
	public void update(float delta){
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		//draw level
		level.draw(batch);
		
		GlyphLayout scoreLayout = new GlyphLayout();
		
		
		for(Effect e: effects){
			e.draw(batch);
		}
		
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		shape.begin(ShapeType.Filled);
		
//		shape.setColor(0, 0, 0, .1f);
		shape.setColor(0, 0, 0, .7f);
		shape.rect(0, 0, Momentus.cam.viewportWidth, Momentus.cam.viewportHeight);
		shape.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		batch.begin();
		scoreLayout.setText(Momentus.font, "PAUSED");
		Momentus.font.draw(batch, scoreLayout, Momentus.cam.viewportWidth / 2 - scoreLayout.width / 2, Momentus.cam.viewportHeight - 10f * scoreLayout.height);

		
		for (int i = 0; i<uiElements.size(); i++)
		{
			uiElements.get(i).draw(batch);
		}
		
		
		
		batch.end();
		
	}
	
	Vector2 temp = new Vector2();
	
	public Vector2 unprojectScreenCoords(int x, int y) {
		Vector3 v = new Vector3(x,y, 0);
		v = Momentus.cam.unproject(v);
		return new Vector2(v.x, v.y);
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
		skipNextUpdate = true;
	}
}
