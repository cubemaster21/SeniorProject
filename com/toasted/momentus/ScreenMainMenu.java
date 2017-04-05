package com.toasted.momentus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ScreenMainMenu extends Screen{

	SpriteBatch mscreen;
	public ScreenMainMenu(){
		super();
		mscreen = new SpriteBatch();
		mscreen.setProjectionMatrix(Momentus.cam.combined); // Needed to keep scale with the rest of the game
		UIButton opbutt= new UIButton(976,1872);
		uiElements.add(opbutt);
		UIButton selectlev= new UIButton(400,1200);
		uiElements.add(selectlev);
		opbutt.setbuttext("Options");
		opbutt.setimg(new TextureRegion(Art.optionsIcon));
		opbutt.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenOptions());;
				
			}
		});
		selectlev.setbuttext("Level Select");
		selectlev.setimg(new TextureRegion(Art.optionsIcon));
		selectlev.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenLevelSelect());
			}
		});
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

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
		mscreen.begin();
		for (int i = 0; i<uiElements.size(); i++)
		{
			uiElements.get(i).draw(mscreen);
		}
		mscreen.end();
	}

	@Override
	public Vector2 unprojectScreenCoords(int x, int y) {
		Vector3 v = new Vector3(x, y, 0);
		v = Momentus.cam.unproject(v);
		return new Vector2(v.x, v.y);
	}

}
