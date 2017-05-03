package com.toasted.momentus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ScreenLevelSelect extends Screen{
	
	SpriteBatch levscreen;
	int levnum = 1;
	public ScreenLevelSelect(){
		super();
		levscreen= new SpriteBatch();
		levscreen.setProjectionMatrix(Momentus.cam.combined);
		UIButton gohome= new UIButton(getWidth() / 2- Art.button.getWidth() / 2,10);
		//levelnum.setimg(new TextureRegion(Art.optionsIcon));
		gohome.setbuttext("Main Menu");
		gohome.setimg(new TextureRegion(Art.button));
		uiElements.add(gohome);
		//uiElements.add(levelnum);
		gohome.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenMainMenu());
				
			}
		});
		for (int y = 6; y > -1; y--){
			for(int x = 0; x < 5; x++){
				UIButton newButton = new UIButton(x * 180 + 150, y* 200 + Art.button.getHeight() * 1.5f);
				newButton.setimg(new TextureRegion(Art.themeIcons[6-y]));
				newButton.setbuttext("" + levnum);
				final int levelFile = levnum++;
				uiElements.add(newButton);
				newButton.setaction(new UIAction(){
					public void doAction(){
//						Momentus.setScreen(new ScreenLevelEditor());
						Level l = new Level();
						
						l.build(Gdx.files.internal("levels/" + levelFile + ".lvl"));
						Momentus.setScreen(new ScreenGame(l, false));
					}
				});
			}
		}
		Audio.play(Audio.menu);
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
		levscreen.begin();
		levscreen.draw(Art.bgMenu, 0, 0, getWidth(), getHeight());
		for (int i = 0; i<uiElements.size(); i++)
		{
			uiElements.get(i).draw(levscreen);
		}
		levscreen.end();
	}

	@Override
	public Vector2 unprojectScreenCoords(int x, int y) {
		// TODO Auto-generated method stub
		Vector3 v = new Vector3(x, y, 0);
		v = Momentus.cam.unproject(v);
		return new Vector2(v.x, v.y);
	}

}
