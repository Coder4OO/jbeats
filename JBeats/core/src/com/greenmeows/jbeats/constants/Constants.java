package com.greenmeows.jbeats.constants;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.greenmeows.jbeats.song.ResultsScreen;
import com.greenmeows.jbeats.song.Song;

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
	
	//game states
	public static Song CURRENTSONG;
	public static final int MENU = 0;
	public static final int STAGE = 1;
	public static final int RESULTS = 2;
	public static final int QUIT = 3;
	public static int CURRENTSTATE = MENU;
	public static ResultsScreen CURRENTRESULTS;
	//UTIL
	
	public static String get_song_file() {
		String pwd = System.getProperty("user.dir");
		Path folder = Paths.get(pwd);
		String parent = folder.getParent().toString();
		String path = parent+"\\"+"core"+"\\"+"assets"+"\\"+"songs"+"\\";
		return path;
	}
	
	public static ArrayList<Song> getAllSongs(){
		ArrayList<Song> songs = new ArrayList<Song>();
		String songfolder = get_song_file();
		File songfile = new File(songfolder);
		String[] songfolders = songfile.list();
		for(int i=0; i < songfolders.length; i++) {
			String beatmap = songfolders[i];
			Song song = new Song(songfolder + "\\" + beatmap);
			songs.add(song);
		}
		return songs;
	}
	
	//GRADE BOUNDARIES
	public static final float GRADE_C = 50;
	public static final float GRADE_B = GRADE_C+(GRADE_C/2);
	public static final float GRADE_A = 87.5F;
	public static final float GRADE_S = 95.5F;
}
