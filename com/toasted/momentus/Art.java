package com.toasted.momentus;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class Art {
	public static void init(){}
	public static HashMap<String, Texture> backgrounds = new HashMap<String, Texture>();
	public static Texture snowflake = new Texture("snowflake.png");
	public static Texture ball =  new Texture("circle.png");
	public static Texture plat = new Texture("plat.png");
	public static Texture play = new Texture("playArrow.png");
	public static Texture rotate = new Texture("rotate.png");
	public static Texture drawer = new Texture("drawer.png");
	public static Texture optionsIcon = new Texture("options.png");
	
	public static Texture bgTrainingRoom = loadBg("TrainingRoom.png");
	public static Texture bgHalloween = loadBg("Halloween.png");
	public static Texture bgArctic = loadBg("Arctic.png");
	public static Texture bgCircus = loadBg("Circus.png");
	public static Texture bgVegas = loadBg("Vegas.png");
	public static Texture bgBamboo = loadBg("Bamboo.png");
	
	
	
	private static Texture loadBg(String filename){
		Texture t = new Texture(filename);
		backgrounds.put(filename, t);
		return t;
	}
}
