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
		UIButton gohome= new UIButton(300,76);
		//levelnum.setimg(new TextureRegion(Art.optionsIcon));
		gohome.setbuttext("Main Menu");
		gohome.setimg(new TextureRegion(Art.plat));
		uiElements.add(gohome);
		//uiElements.add(levelnum);
		gohome.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenMainMenu());
				
			}
		});
		for (int y = 7; y > 0; y--){
			int q = 0;
			int p = 0;
			for(int x = 0; x < 5; x++){
//				levelnum[q][p] = new UIButton(x * 180 + 136, y* 256);
//				levelnum[q][p].setimg(new TextureRegion(Art.optionsIcon));
//				levelnum[q][p].setbuttext(numlev + levnum);
//				levnum++;
//				uiElements.add(levelnum[q][p]);
//				levelnum[q][p].setaction(new UIAction(){
//					public void doAction(){
//						Momentus.setScreen(new ScreenLevelEditor());
//					}
//				});
				UIButton newButton = new UIButton(x * 180 + 136, y* 256);
				newButton.setimg(new TextureRegion(Art.optionsIcon));
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
				q++;
			}
			p++;
		}
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
