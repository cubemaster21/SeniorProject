package com.toasted.momentus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ScreenMainMenu extends Screen{

	SpriteBatch sure;
	public ScreenMainMenu(){
		super();
		sure = new SpriteBatch();
		UIButton whatever= new UIButton(20,76);
		uiElements.add(whatever);
		Texture butimg = new Texture("prettypatties.png");//add this file
		TextureRegion paintedmeat = new TextureRegion(butimg);
		whatever.setimg(paintedmeat);
		whatever.setaction(new UIAction(){
			public void doAction(){
				System.out.println("Derp");
				
			}
		});
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		sure.begin();
		for (int i = 0; i<uiElements.size(); i++)
		{
			uiElements.get(i).draw(sure);
		}
		sure.end();
	}

	@Override
	public Vector2 unprojectScreenCoords(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
