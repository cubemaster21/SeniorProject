package com.toasted.momentus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ScreenOptions extends Screen {

	
	SpriteBatch opscreen;
	public ScreenOptions(){
		super();
		opscreen = new SpriteBatch();
		opscreen.setProjectionMatrix(Momentus.cam.combined); // Needed to keep scale with the rest of the game
		UIButton gohome= new UIButton(300,76);
		uiElements.add(gohome);
		Texture himg = new Texture("plat.png");//add this file
		TextureRegion paintedmeat = new TextureRegion(himg);
		gohome.setbuttext("Main Menu");
		gohome.setimg(paintedmeat);
		gohome.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenMainMenu());
				
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
		opscreen.begin();
		for (int i = 0; i<uiElements.size(); i++)
		{
			uiElements.get(i).draw(opscreen);
		}
		opscreen.end();
	}

	@Override
	public Vector2 unprojectScreenCoords(int x, int y) {
		Vector3 v = new Vector3(x, y, 0);
		v = Momentus.cam.unproject(v);
		return new Vector2(v.x, v.y);
	}

}
