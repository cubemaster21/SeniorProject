package com.toasted.momentus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Momentus extends ApplicationAdapter{
	public static BitmapFont font;
	public static BitmapFont hitFont;
	private static Screen currentScreen, nextScreen;
	public static OrthographicCamera cam;
	float scale = 128;
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		font = new BitmapFont(Gdx.files.internal("boxy.fnt"), Gdx.files.internal("boxy.png"), false);
		font.getData().setScale(scale / 10);
		
		hitFont = new BitmapFont(Gdx.files.internal("boxy.fnt"), Gdx.files.internal("boxy.png"), false);
		hitFont.getData().setScale(scale / 18);
		
		cam = new OrthographicCamera(9 * scale, 16 * scale);
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		currentScreen = new ScreenLevelEditor();
		
	}
	public static void setScreen(Screen newScreen){
		nextScreen = newScreen;
	}
	@Override
	public void render () {
		currentScreen.update(Gdx.graphics.getDeltaTime());
		
		currentScreen.draw();
		
		if(nextScreen != null){
			currentScreen = nextScreen;
			nextScreen = null;
			Gdx.input.setInputProcessor(currentScreen);
		}
	}
	public void dispose () {
	}

}
