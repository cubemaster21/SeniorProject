package com.toasted.momentus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
	static Texture ball, plat;
	PhysObj ballObj;
	ArrayList<PhysObj> objects = new ArrayList<PhysObj>();
	ArrayList<Effect> effects; // Just a reference to the ScreenGame variable's arrayList. Set by screengame
	ArrayList<PhysObj> deletedObjects = new ArrayList<PhysObj>();
	
	public Level(){
		ball = new Texture("circle.png");
		plat = new Texture("plat.png");
		
		world = new World(gravity, true);
		world.setContactListener(new ContactListener(){

			@Override
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
				
				if(effects != null)
					effects.add(new EffectTextShine("+1", platform.getPosition().x * Constants.scale, platform.getPosition().y * Constants.scale, 1f));
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		spawnBall();
		
		final PhysObj ground = addBox(0, -1, 9, 1);
		final PhysObj leftSide = addBox(-1, 0, 1, 16);
		final PhysObj rightSide = addBox(9, 0, 1, 16);
		final PhysObj top = addBox(0, 16, 9, 1);
		
		Sprite NO_RENDER = new Sprite(new TextureRegion(ball, 0, 0, 0 ,0));
		
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
			o.setAngle(del.getAngle());
			o.applyProperties(del.getPropertiesID());
			//set other properties
		}
		for(PhysObj obj: objects){
			obj.resetHits();
			obj.setPosition(obj.getInitialPosition());
			obj.getBody().setLinearVelocity(0, 0);
		}
		
		
	}
	public void spawnBall(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(4.5f, 8f);
		
		Body body = world.createBody(bodyDef);
		body.setSleepingAllowed(false);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(.5f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1f;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = .1f;
		
		Fixture fixture = body.createFixture(fixtureDef);
		
		circle.dispose();
		ballObj = new PhysObj(body, bodyDef, fixture);
		ballObj.setSprite(new Sprite(ball));
		objects.add(ballObj);
	}
	
	public void update(float delta){
		gravity.rotateRad(-Gdx.input.getGyroscopeZ() * Gdx.graphics.getDeltaTime());
		world.setGravity(gravity);
		world.step(delta, 6, 2);
		syncObjects();
		
	}
	public void syncObjects(){
		for(int i = 0;i < objects.size();i++){
			if(objects.get(i).isFlaggedForRemoval()){
				world.destroyBody(objects.get(i).getBody());
				objects.get(i).destroy();
				if(objects.get(i).keepInHistory())
					deletedObjects.add(objects.get(i));
				objects.remove(i--);
				
				continue;
			}
			PhysObj obj = objects.get(i);
			obj.sync();
		}
		ballObj.sync();	
	}
	public void draw(SpriteBatch batch){
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
		obj.setSprite(new Sprite(plat));
		
		objects.add(obj);
		
		return obj;
	}
	public void setEffectsManager(ArrayList<Effect> effects){
		this.effects = effects;
	}
	public void resetEffectsManager(){
		effects = null;
	}
}
