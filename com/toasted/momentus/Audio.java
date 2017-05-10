package com.toasted.momentus;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {
	public static HashMap<String, Music> musicHash = new HashMap<String, Music>();
	
	public static Music menu;
	public static Music bgBamboo;
	public static Music bgCircus;
	public static Music bgHalloween;
	public static Music bgTraining;
	public static Music bgArctic;
	public static Music bgSkyscraper;
	public static Music bgVegas;
	
	public static Sound hit;
	public static Sound click;
	
	public static boolean enabled =true;
	public static boolean effectsEnabled = true;
	
	public static Music playing;
	
	public static void init(){
		try{
			menu = createMusic("menumusic.wav");
			menu.setLooping(true);
			bgBamboo = 		createMusic("bamboomusic.wav");
			bgCircus = 		createMusic("circusmusic.wav");
			bgHalloween = 	createMusic("halloweenmusic.wav");
			bgTraining = 	createMusic("trainingmusic.wav");
			bgArctic = 		createMusic("arcticmusic.wav");
			bgSkyscraper = 	createMusic("skyscrapermusic.wav");
			bgVegas = 		createMusic("vegasmusic.wav");
			
			hit = Gdx.audio.newSound(Gdx.files.internal("bounce.wav"));
			click = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
			
			
		} catch(Exception e){
			System.err.println("Failed to load audio sources.");
			e.printStackTrace();
		}
	}
	private static Music createMusic(String filename){
		Music m = Gdx.audio.newMusic(Gdx.files.internal(filename));
		m.setLooping(true);
		musicHash.put(filename, m);
		return m;
	}
	public static void setMusicVolume(float value){
		for(Music m: musicHash.values()){
			m.setVolume(value);
		}
	}
	public static long play(Sound s){
		if(effectsEnabled) return s.play();
		return -1;
	}
	public static void play(Music m){
		if(playing != null)
			playing.pause();
		m.play();
		playing = m;
	}
}
