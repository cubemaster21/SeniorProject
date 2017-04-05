package com.toasted.momentus;

import com.badlogic.gdx.Gdx;
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
		final UIButton sound = new UIButton(240,1000);
		final UIButton music = new UIButton(240, 400);
		uiElements.add(gohome);

		uiElements.add(sound);
		uiElements.add(music);
		Texture himg = new Texture("plat.png");//add this file
		Texture mimg = new Texture("musicon.png");
		Texture nmimg = new Texture("musicoff.png");
		Texture simg = new Texture("soundon.png");
		Texture nsimg = new Texture("soundoff.png");
		TextureRegion homebut = new TextureRegion(himg);
		final TextureRegion soundbut = new TextureRegion(simg);
		final TextureRegion nosoundbut = new TextureRegion(nsimg);
		final TextureRegion musicbut = new TextureRegion(mimg);
		final TextureRegion nomusicbut = new TextureRegion(nmimg);
		sound.setimg(soundbut);
		music.setimg(musicbut);
		gohome.setbuttext("Main Menu");
		sound.setbuttext("Sound On");
		music.setbuttext("Music On");
		gohome.setimg(homebut);
		gohome.setbuttext("Main Menu");
		gohome.setimg(new TextureRegion(Art.plat));

		gohome.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenMainMenu());
				
			}
		});
		music.setaction(new UIAction(){
			public void doAction(){
				if(music.getimg()== musicbut){
				music.setimg(nomusicbut);
				music.setbuttext("Music Off");
				}
				else{
					music.setimg(musicbut);
					music.setbuttext("Music On");
				}
			}});;
		sound.setaction(new UIAction(){
			public void doAction(){
				if(sound.getimg()== soundbut){
					sound.setimg(nosoundbut);
					sound.setbuttext("Sound Off");
				}
				else{
					sound.setimg(soundbut);
					sound.setbuttext("Sound On");
				}
				}});;
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
