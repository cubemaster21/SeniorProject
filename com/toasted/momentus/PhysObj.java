package com.toasted.momentus;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;

public class PhysObj {
	private Sprite sprite;
	private Body body;
	private BodyDef def;
	private Fixture fixture;
	private boolean flaggedForRemoval = false;
	private boolean keepInHistory = true;
	private Vector2 initialPosition;
	private float initialRotation = 0;
	private int hits;
	private int maxHits = -1;
	private DestroyAction destroyAction;
	private int propertiesID = 0;
	private float rotationSpeed = 0; // 0 is no rotation, 1 is 1 full rotation/second
	
	
	public PhysObj(Body body, BodyDef def, Fixture fixture){
		this.body = body;
		this.def = def;
		this.fixture = fixture;
		initialPosition =  new Vector2(body.getPosition().x, body.getPosition().y);
		sprite = new Sprite();
	}
	public void updateDefinition(){
		def.position.set(getPosition());
		def.angle = getAngle();
	}
	public void sync(float delta){
		this.setAngle(getAngle() + delta * rotationSpeed * Constants.PI * 2);
		sprite.setCenter(body.getPosition().x * Constants.scale, body.getPosition().y * Constants.scale);
		sprite.setRotation((float)Math.toDegrees(body.getAngle()));
	}
	public void setPosition(float x, float y){
		body.setTransform(x, y, body.getAngle());
	}
	public void setPosition(Vector2 v){
		setPosition(v.x, v.y);
	}
	public Vector2 getPosition(){
		return body.getPosition();
	}
	public float getAngle(){
		return body.getAngle();
	}
	public void setAngle(float angle){
		body.setTransform(body.getPosition(), angle);
	}
	
	public void hit(){
		hits++;
		if(hits >= maxHits && maxHits != -1){
			flagForRemoval();
		}
	}
	public void resetHits(){
		hits = 0;
	}
	public int getHits(){
		return hits;
	}
	public Vector2 getInitialPosition(){
		return initialPosition;
	}
	public void resetInitialPosition(){
		if(initialPosition != null)
			initialPosition.set(getPosition());
		else
			initialPosition = new Vector2(getPosition());
	}
	public float getInitialRotation(){
		return initialRotation;
	}
	//only works while the world is loaded, getAngle() will return 0 unless the world has been inited
	public void resetInitalRotation(){
		initialRotation = getAngle();
	}
	public void flagForRemoval(){
		flaggedForRemoval = true;
	}
	public boolean isFlaggedForRemoval(){
		return flaggedForRemoval;
	}
	public void resetRemovalFlag(){
		flaggedForRemoval = false;
	}
	public Fixture getFixture(){
		return fixture;
	}
	public BodyDef getBodyDef(){
		return def;
	}
	public Sprite getSprite(){
		return sprite;
	}
	public void setSprite(Sprite sp){
		this.sprite = sp;
	}
	public Body getBody(){
		return body;
	}
	public void draw(SpriteBatch b){
		sprite.draw(b);
	}
	public int getMaxHits() {
		return maxHits;
	}
	public void setMaxHits(int maxHits) {
		this.maxHits = maxHits;
	}
	
	public void destroy(Level l) {
		if(destroyAction != null){
			destroyAction.onDestroy(l, this);
		}
	}
	public void setDestroyAction(DestroyAction destroyAction) {
		this.destroyAction = destroyAction;
	}

	public boolean keepInHistory() {
		return keepInHistory;
	}
	public void setKeepInHistory(boolean keepInHistory) {
		this.keepInHistory = keepInHistory;
	}

	public static interface DestroyAction{
		public void onDestroy(Level l, PhysObj obj);
	}
	public void applyProperties(int id){
		propertiesID = id;
		switch(id){
		case 1: 
			//standard block
			break;
		case 2: 
			//Ice
			sprite.setColor(Color.BLUE);
			setMaxHits(4);
			destroyAction = new DestroyAction(){
				public void onDestroy(Level l, PhysObj obj){
					if(l.effects == null) return;
					for(int i = 0;i < 8;i++){
						
						Random r = new Random();
						EffectShatterParticle newParticle = new EffectShatterParticle(Art.snowflake, 
										new Vector2(obj.getPosition().x * Constants.scale + (r.nextFloat() * 256) - 128, obj.getPosition().y * Constants.scale + (r.nextFloat() * 256) - 128),
										new Vector2(r.nextFloat() * Constants.scale * 2, r.nextFloat() * Constants.scale * 2),
										Constants.PI * r.nextFloat(),
										r.nextFloat() * 1.5f);
						l.effects.add(newParticle);
					}
				}
			};
			break;
		case 3:
			//bouncy
			sprite.setColor(Color.PINK);
			fixture.setRestitution(1.3f);
			break;
		case 4:
			//rotate cc
			sprite.setColor(Color.GREEN);
			rotationSpeed = .5f;
			break;
		case 5: 
			sprite.setColor(Color.PURPLE);
			rotationSpeed = -.5f;
		}
	}
	public int getPropertiesID() {
		return propertiesID;
	}
	public void setPropertiesID(int propertiesID) {
		this.propertiesID = propertiesID;
	}
	public String toString(){
		//ix, iy, ir, maxHits, rotSpeed, propertiesID
		return initialPosition.x + ":" +
				initialPosition.y + ":" + 
				initialRotation + ":" + 
				maxHits + ":" +
				rotationSpeed + ":" + 
				propertiesID;
	}
	public void setFromString(String data){
		String[] splitData = data.split(":");
		this.setPosition(Float.parseFloat(splitData[0]), Float.parseFloat(splitData[1]));
//		this.resetInitalRotation();
		this.initialPosition = new Vector2(Float.parseFloat(splitData[0]), Float.parseFloat(splitData[1]));
		
		this.setAngle(Float.parseFloat(splitData[2]));
//		this.resetInitalRotation();
		this.initialRotation = Float.parseFloat(splitData[2]);
		
		this.setMaxHits(Integer.parseInt(splitData[3]));
		
		this.rotationSpeed = Float.parseFloat(splitData[4]);
		
		this.setPropertiesID(Integer.parseInt(splitData[5]));
		this.applyProperties(propertiesID);
	}

}
