package com.toasted.momentus;

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
	private int hits;
	private int maxHits = -1;
	private DestroyAction destroyAction;
	private int propertiesID = 0;
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
	public void sync(){
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
		initialPosition.set(getPosition());
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
	
	public void destroy() {
		if(destroyAction != null){
			destroyAction.onDestroy();
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
		public void onDestroy();
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
			break;
		case 3:
			//bouncy
			sprite.setColor(Color.PINK);
			fixture.setRestitution(1.3f);
			break;
		}
	}
	public int getPropertiesID() {
		return propertiesID;
	}
	public void setPropertiesID(int propertiesID) {
		this.propertiesID = propertiesID;
	}
}
