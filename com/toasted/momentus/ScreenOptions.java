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
		
		Texture mimg = new Texture("musicon.png");
		Texture nmimg = new Texture("musicoff.png");
		Texture simg = new Texture("soundon.png");
		Texture nsimg = new Texture("soundoff.png");
		
		UIButton gohome= new UIButton(getWidth() / 2 - Art.button.getWidth() / 2,76);
		final UIButton sound = new UIButton(getWidth() / 2 - mimg.getWidth() / 2,1200);
		final UIButton music = new UIButton(getWidth() / 2 - mimg.getWidth() / 2, 600);
		
		
		
		
		uiElements.add(gohome);

		uiElements.add(sound);
		uiElements.add(music);
		
		TextureRegion homebut = new TextureRegion(Art.button);
		final TextureRegion soundbut = new TextureRegion(simg);
		final TextureRegion nosoundbut = new TextureRegion(nsimg);
		final TextureRegion musicbut = new TextureRegion(mimg);
		final TextureRegion nomusicbut = new TextureRegion(nmimg);
		sound.setimg(soundbut);
		gohome.setbuttext("Main Menu");
		sound.setbuttext("Sound On");
		if (Audio.enabled){
			music.setbuttext("Music On");
			music.setimg(musicbut);
		}else{
			music.setbuttext("Music Off");
			music.setimg(nomusicbut);
		}
		gohome.setimg(homebut);
		gohome.setbuttext("Main Menu");

		gohome.setaction(new UIAction(){
			public void doAction(){
				Momentus.setScreen(new ScreenMainMenu());
				
			}
		});
		music.setaction(new UIAction(){
			public void doAction(){
				if(Audio.enabled){
					music.setimg(nomusicbut);
					music.setbuttext("Music Off");
					Audio.setMusicVolume(0);
					Audio.enabled = false;
				}else{
					music.setimg(musicbut);
					music.setbuttext("Music On");
					Audio.setMusicVolume(1);
					Audio.enabled = true;
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
		opscreen.begin();
		opscreen.draw(Art.bgMenu, 0, 0, getWidth(), getHeight());
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
