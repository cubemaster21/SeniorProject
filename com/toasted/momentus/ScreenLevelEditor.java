package com.toasted.momentus;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ScreenLevelEditor extends Screen{
	SpriteBatch batch;
	OrthographicCamera physCam;
	Level level;
	
	ArrayList<PhysObj> drawerIcons = new ArrayList<PhysObj>();
	Texture drawer = new Texture("drawer.png");
	Rectangle drawerRect = new Rectangle(Momentus.cam.viewportWidth - Momentus.cam.viewportWidth / 5, 10, drawer.getWidth(), drawer.getHeight());
	Rectangle drawerExpandedRect = new Rectangle(256, 10, drawer.getWidth(), drawer.getHeight());
	Texture play = new Texture("playArrow.png");
	Texture rotate = new Texture("rotate.png");
	Rectangle playRect = new Rectangle(10, 10, 256, 256);
	boolean drawerExpanded = false;
	
	long touchStartTime = Long.MAX_VALUE;
	PhysObj objectFocus;
	boolean rotateMode = false;
	boolean dragged = false;
	Vector3 touchDownPos;
	Vector2 temp = new Vector2();

	public ScreenLevelEditor(){
		Gdx.input.setInputProcessor(this);
		Box2D.init();
		level = new Level();
		
		physCam = new OrthographicCamera(9, 16);
		physCam.position.set(physCam.viewportWidth / 2, physCam.viewportHeight / 2, 0);
		physCam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(Momentus.cam.combined);
		
		
	}
	public ScreenLevelEditor(Level l){
		Gdx.input.setInputProcessor(this);
		Box2D.init();
		level = l;
		
		physCam = new OrthographicCamera(9, 16);
		physCam.position.set(physCam.viewportWidth / 2, physCam.viewportHeight / 2, 0);
		physCam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(Momentus.cam.combined);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(rotateMode) rotateMode = false;
		Vector3 v = physCam.unproject(new Vector3(screenX, screenY, 0));
		Vector3 v2 = Momentus.cam.unproject(new Vector3(screenX, screenY, 0));
		
		if(drawerExpanded && drawerExpandedRect.contains(v2.x, v2.y)){
			if(objectFocus != null){
				//didn't drag out of drawer
				objectFocus.setPosition(objectFocus.getInitialPosition());
			}
		} else if(drawerExpanded && !drawerExpandedRect.contains(v2.x, v2.y)){
			drawerExpanded = false;
			objectFocus.resetInitialPosition();
			objectFocus.setKeepInHistory(true);
			for(PhysObj obj: drawerIcons){
				if(obj != objectFocus)
					obj.flagForRemoval();
			}
			drawerIcons.clear();
		}
		if(drawerRect.contains(v2.x, v2.y)){
			drawerExpanded = true;
			//basic
			PhysObj icon1 = level.addBox(2.3f, 1.25f, 2, .5f);
			//Ice
			PhysObj icon2 = level.addBox(icon1.getPosition().x + 1.3f, 1.25f, 2, .5f);
			icon2.applyProperties(2);
			//Bouncy
			PhysObj icon3 = level.addBox(icon2.getPosition().x + 1.3f, 1.25f, 2, .5f);
			icon3.applyProperties(3);
			
			icon1.setKeepInHistory(false);
			icon2.setKeepInHistory(false);
			icon3.setKeepInHistory(false);
			
			drawerIcons.add(icon1);
			drawerIcons.add(icon2);
			drawerIcons.add(icon3);
			
		}
		if(!drawerExpanded && playRect.contains(v2.x, v2.y)){
			Momentus.setScreen(new ScreenGame(level));
		} 
		if((System.currentTimeMillis() - touchStartTime) / 1000.0f > Constants.longTouchDuration && !dragged){
			
			//engage rotation mode?
//			rotateMode = true;
			
		} else { 
			if(objectFocus != null) objectFocus.resetInitialPosition();
			objectFocus = null; // TEMP
		}
		
		dragged = false;
		touchStartTime = Long.MAX_VALUE;
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		Vector3 d = Momentus.cam.unproject(new Vector3(screenX, screenY, 0));
		if(d.sub(touchDownPos).len() < Constants.movementError){
			return false;
		}
		
		
		dragged = true;
		Vector3 v = physCam.unproject(new Vector3(screenX, screenY, 0));
		//If an object is in focus
		if(objectFocus != null){
			if(rotateMode){
				float angle = (float)Math.atan2(v.y - objectFocus.getPosition().y, v.x - objectFocus.getPosition().x);
				objectFocus.setAngle(angle);
			} else {
				
				objectFocus.setPosition(v.x, v.y);
			}
		}
		return false;
	}

	@Override
	public void update(float delta) {
		level.syncObjects();
		
		if((System.currentTimeMillis() - touchStartTime) / 1000.0f > Constants.longTouchDuration && !dragged && objectFocus != null){
			rotateMode = true;
		}
	}

	@Override
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		level.draw(batch);
		if(rotateMode){
			batch.draw(rotate, objectFocus.getPosition().x * Constants.scale - 128, objectFocus.getPosition().y * Constants.scale - 128);
		}
		
		batch.draw(drawer, (drawerExpanded ? 256 : Momentus.cam.viewportWidth - Momentus.cam.viewportWidth / 5), 10);
		batch.draw(play, playRect.getX(),playRect.getY());
		for(PhysObj obj: drawerIcons){
			obj.draw(batch);
		}
		
		Momentus.hitFont.draw(batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), 0, Momentus.cam.viewportHeight - 60);
		batch.end();
		
//		debugRenderer.render(world, physCam.combined);
		
	}
	
	
	
	public Vector2 unprojectScreenCoords(int x, int y) {
		Vector3 t = new Vector3(x, y, 0);
		t = Momentus.cam.unproject(t);
		return temp.set(t.x, t.y);
	}
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		if(super.touchDown(screenX, screenY, pointer, button)) return true;
		
		touchStartTime = System.currentTimeMillis();
		
		Vector3 v = physCam.unproject(new Vector3(screenX, screenY, 0));
		touchDownPos = Momentus.cam.unproject(new Vector3(screenX, screenY, 0));
//		if(objectFocus != null) objectFocus = null;
		for(PhysObj obj: level.objects){
			if(obj.getFixture().testPoint(v.x, v.y)){
				objectFocus = obj;
//				obj.flaggedForRemoval = true;
				break;
			}
		}
		return false;
	}
}
