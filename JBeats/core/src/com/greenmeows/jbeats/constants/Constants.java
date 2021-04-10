package com.greenmeows.jbeats.constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = WIDTH/16*9;
	public static final String TITLE = "JBeats";
	public static final float STANDARDSCROLLSPEED = 0.25F*HEIGHT;
	public static final float GAPINSECONDS = 0.25F;
	public static final float HITOFFSET = HEIGHT/20;
	
	//judgements
	public static final float TIMING_MARVELLOUS = 15;
	public static final float TIMING_PERFECT = 30;
	public static final float TIMING_GREAT = 90;
	public static final float TIMING_OKAY = 150;
	
	//score
	public static final float SCORE_MARVELLOUS = 5;
	public static final float SCORE_PERFECT = 4;
	public static final float SCORE_GREAT = 3;
	public static final float SCORE_OKAY = 1;
	
	public static String get_song_file() {
		String pwd = System.getProperty("user.dir");
		Path folder = Paths.get(pwd);
		String parent = folder.getParent().toString();
		String path = parent+"\\"+"core"+"\\"+"assets"+"\\"+"songs"+"\\";
		return path;
	}
}
