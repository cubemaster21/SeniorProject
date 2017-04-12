package com.toasted.momentus;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Audio {
	public static HashMap<String, Music> musicHash = new HashMap<String, Music>();
	
	public static Music menu;
	
	public static boolean enabled =true;
	
	public static void init(){
		try{
			menu = createMusic("menumusic.wav");
			menu.setLooping(true);
		} catch(Exception e){
			System.err.println("Failed to load audio sources.");
			e.printStackTrace();
		}
	}
	private static Music createMusic(String filename){
		Music m = Gdx.audio.newMusic(Gdx.files.internal(filename));
		musicHash.put(filename, m);
		return m;
	}
	public static void setMusicVolume(float value){
		for(Music m: musicHash.values()){
			m.setVolume(value);
		}
	}
}
