package com.toasted.momentus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Momentus extends ApplicationAdapter{
	public static BitmapFont font;
	public static BitmapFont hitFont;
	public static BitmapFont opfont;
	private static Screen currentScreen, nextScreen;
	public static OrthographicCamera cam;
	float scale = 128;
	boolean gameTest = false;
	
	public Momentus(boolean gameTest){
		//only called by Eddie's implementation
		this.gameTest = gameTest;
		
	}
	public Momentus(){}
	
	
	@Override
	public void create () {
		Art.init();
		Audio.init();
		Gdx.input.setCatchBackKey(true);
		
		FileHandle fntFile = Gdx.files.internal("boxy.fnt");
		FileHandle pngFile = Gdx.files.internal("boxy.png");
		
		font = new BitmapFont(fntFile, pngFile, false);
		font.getData().setScale(scale / 10);
		
		hitFont = new BitmapFont(fntFile, pngFile, false);
		hitFont.getData().setScale(scale / 18);
		
		opfont = new BitmapFont(fntFile, pngFile, false);
		opfont.getData().setScale(scale / 25);
		
		cam = new OrthographicCamera(9 * scale, 16 * scale);
		cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
		cam.update();
		if(gameTest)
			currentScreen = new ScreenLevelEditor();
		else 
			currentScreen = new ScreenMainMenu();
		Gdx.input.setInputProcessor(currentScreen);
	}
	public static void setScreen(Screen newScreen){
		nextScreen = newScreen;
	}
	@Override
	public void render () {
		currentScreen.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		currentScreen.draw();
//		System.out.println("FPS:" + Gdx.graphics.getFramesPerSecond());
		
		if(nextScreen != null){
			currentScreen = nextScreen;
			nextScreen = null;
			Gdx.input.setInputProcessor(currentScreen);
		}
	}
	public void dispose () {
	}

}
