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
		
		UIButton selectlev= new UIButton(getWidth() / 2 - Art.button.getWidth() / 2,1200);
		uiElements.add(selectlev);
		
		selectlev.setbuttext("Level Select");
		selectlev.setimg(new TextureRegion(Art.button));
		selectlev.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenLevelSelect());
			}
		});
		
		UIButton levelCreator = new UIButton(getWidth() / 2 - Art.button.getWidth() / 2, selectlev.y - Art.button.getHeight() * 1.2f);
		levelCreator.setbuttext("Level Editor");
		levelCreator.setimg(new TextureRegion(Art.button));
		levelCreator.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenLevelEditor());
			}
		});
		uiElements.add(levelCreator);
		UIButton opbutt= new UIButton(getWidth() / 2 - Art.button.getWidth() / 2, levelCreator.y - Art.button.getHeight() * 1.2f);
		uiElements.add(opbutt);
		opbutt.setbuttext("Options");
		opbutt.setimg(new TextureRegion(Art.button));
		opbutt.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenOptions());;
				
			}
		});
		
		
		Audio.play(Audio.menu);
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
		
		mscreen.draw(Art.bgMenu, 0, 0, getWidth(), getHeight());
		for (int i = 0; i<uiElements.size(); i++)
		{
			uiElements.get(i).draw(mscreen);
		}
		mscreen.draw(Art.logo, getWidth() / 2 - Art.logo.getWidth() / 2, getHeight() * 0.75f);
		mscreen.end();
	}

	@Override
	public Vector2 unprojectScreenCoords(int x, int y) {
		Vector3 v = new Vector3(x, y, 0);
		v = Momentus.cam.unproject(v);
		return new Vector2(v.x, v.y);
	}

}
