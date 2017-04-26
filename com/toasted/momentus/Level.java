package com.toasted.momentus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Level {
	World world;
	Vector2 gravity = new Vector2(0, -20f);
	
	PhysObj ballObj;
	ArrayList<PhysObj> objects = new ArrayList<PhysObj>();
	ArrayList<Effect> effects; // Just a reference to the ScreenGame variable's arrayList. Set by screengame
	ArrayList<PhysObj> deletedObjects = new ArrayList<PhysObj>();
	
	int score;
	float timeLeft = 30;
	private Texture bg = Art.bgTrainingRoom;
	private Music music = Audio.bgTraining;
	
	public Level(){
		
		world = new World(gravity, true);
		world.setContactListener(new ContactListener(){

			public void beginContact(Contact contact) {
				Fixture fa = contact.getFixtureA();
				Fixture fb = contact.getFixtureB();
				PhysObj platform;
				if(fa.getBody() == ballObj.getBody()){
					platform = getPhysObjForBody(fb.getBody());
				} else if(fb.getBody() == ballObj.getBody()){
					platform = getPhysObjForBody(fa.getBody());
				} else {
					return;
				}
				if(platform == null){
					//if it's still null at this point, the PhysObj is not in the objects ArrayList
					//ignore hits on sides
					return;
				}
//				platform.getSprite().setColor(Color.GOLD);
				platform.hit();
				int newPoints = (platform.getPropertiesID() == 6 ? 5 : 1);
				score += newPoints;
				if(effects != null)
					effects.add(new EffectTextShine("+" + newPoints, platform.getPosition().x * Constants.scale, platform.getPosition().y * Constants.scale, 1f));
			}
			public void endContact(Contact contact) {
				
			}
			public void preSolve(Contact contact, Manifold oldManifold) {
				
			}
			public void postSolve(Contact contact, ContactImpulse impulse) {
				
			}
			
		});
		
		spawnBall();
		
		final PhysObj ground = addBox(0, -1, 9, 1);
		final PhysObj leftSide = addBox(-1, 0, 1, 16);
		final PhysObj rightSide = addBox(9, 0, 1, 16);
		final PhysObj top = addBox(0, 16, 9, 1);
		
		Sprite NO_RENDER = new Sprite(new TextureRegion(Art.ball, 0, 0, 0 ,0));
		
		ground.setSprite(NO_RENDER);
		leftSide.setSprite(NO_RENDER);
		rightSide.setSprite(NO_RENDER);
		top.setSprite(NO_RENDER);
		
		objects.remove(ground);
		objects.remove(rightSide);
		objects.remove(leftSide);
		objects.remove(top);
		
		
	}
	public void reset(){
		for(PhysObj del: deletedObjects){
			del.resetRemovalFlag();
			
			del.updateDefinition();
			PhysObj o = addBox(del.getPosition().x - 1, del.getPosition().y - .25f, 2, .5f);
			o.setAngle(del.getInitialRotation());
			o.resetInitalRotation();
			o.applyProperties(del.getPropertiesID());
			//set other properties
		}
		deletedObjects.clear();
		for(PhysObj obj: objects){
			obj.resetHits();
			obj.setPosition(obj.getInitialPosition());
			obj.setAngle(obj.getInitialRotation());
			obj.getBody().setLinearVelocity(0, 0);
		}
		score = 0;
		timeLeft = 30;
		gravity.set(0, -20f);
	}
	public void spawnBall(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(4.5f, 8f);
		
		Body body = world.createBody(bodyDef);
		body.setSleepingAllowed(false);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(.5f);
		
//		PolygonShape polygon = new PolygonShape();
//		polygon.setAsBox(.5f, .5f);
		
		FixtureDef fixtureDef = new FixtureDef();
//		fixtureDef.shape = polygon;
		fixtureDef.shape = circle;
		fixtureDef.density = 1f;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = .1f;
		
		Fixture fixture = body.createFixture(fixtureDef);
		
		circle.dispose();
//		polygon.dispose();
		ballObj = new PhysObj(body, bodyDef, fixture);
		ballObj.setSprite(new Sprite(Art.ball));
		objects.add(ballObj);
	}
	
	public void update(float delta){
		gravity.rotateRad(-Gdx.input.getGyroscopeZ() * Gdx.graphics.getDeltaTime());
		world.setGravity(gravity);
		world.step(delta, 6, 2);
		syncObjects(delta);
		timeLeft -= delta;
	}
	public void syncObjects(float delta){
		for(int i = 0;i < objects.size();i++){
			if(objects.get(i).isFlaggedForRemoval()){
				world.destroyBody(objects.get(i).getBody());
				objects.get(i).destroy(this);
				if(objects.get(i).keepInHistory())
					deletedObjects.add(objects.get(i));
				objects.remove(i--);
				
				continue;
			}
			PhysObj obj = objects.get(i);
			obj.sync(delta);
		}
		ballObj.sync(delta);	
	}
	public void draw(SpriteBatch batch){
		batch.draw(bg, 0,0,Momentus.cam.viewportWidth, Momentus.cam.viewportHeight);
		ballObj.draw(batch);
		for(PhysObj obj: objects){
			obj.draw(batch);
		}
		ballObj.draw(batch);
	}
	public PhysObj getPhysObjForBody(Body b){
		for(PhysObj o: objects){
			if(o.getBody() == b) return o;
		}
		return null;
	}
	protected PhysObj addBox(float x, float y, float w, float h){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x + w / 2, y + h / 2);
		Body body = world.createBody(bodyDef);
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(w / 2, h / 2);
		Fixture f = body.createFixture(polygon, 0);
		polygon.dispose();
		PhysObj obj = new PhysObj(body, bodyDef, f);
		obj.setSprite(new Sprite(Art.plat));
		
		objects.add(obj);
		
		return obj;
	}
	public void setEffectsManager(ArrayList<Effect> effects){
		this.effects = effects;
	}
	public void resetEffectsManager(){
		effects = null;
	}
	public void compile(FileHandle file){
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < objects.size();i++){
			PhysObj po = objects.get(i);
			if(po.equals(ballObj)){
				sb.append("ball|");
			}
			sb.append(po.toString());
			if(i < objects.size() - 1){
				sb.append("/");
			}
		}
		file.writeString(sb.toString(), false);
	}
	public void build(FileHandle file){
		if(!file.exists()){
			System.err.println("Level file does not exist!");
			return;
		}
		String contents = file.readString();
		String[] lines = contents.split("/");
		for(String line: lines){
			String[] fSplit = line.split("\\|");
			if(fSplit[0].equals("ball")){
				ballObj.setFromString(fSplit[1]);
			} else if(fSplit[0].equals("bg")){
				
				bg = Art.backgrounds.get(fSplit[1]);
			} else if(fSplit[0].equals("music")){
				System.out.println("Loading bgmusic: " + fSplit[1]);
				music = Audio.musicHash.get(fSplit[1]);
			}else {
				
				addBox(0,0,2, .5f).setFromString(fSplit[0]);
			}
		}
		Audio.play(music);
	}
}
