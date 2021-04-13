package com.greenmeows.jbeats.song;

import java.time.Instant;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.greenmeows.jbeats.Game;
import com.greenmeows.jbeats.constants.Constants;

public class SongMenu {
	private ArrayList<Song> songs = new ArrayList<Song>();
	private BitmapFont titlefont;
	private BitmapFont optionfont;
	private int currentoption;
	private long lasttime;
	private float debounce = 0.1F;
	private CharSequence title = new StringBuffer("Select A Song");
	
	
	public SongMenu(BitmapFont titlefont, BitmapFont optionfont, ArrayList<Song> songs) {
		this.titlefont = titlefont;
		this.optionfont = optionfont;
		this.songs = songs;
		titlefont.setColor(Color.WHITE);
	}
	
	public void logic() {
		long deltatime = Instant.now().toEpochMilli()-lasttime;
		if(deltatime/1000 > debounce) {
			if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
				if(currentoption <= 0) {
					currentoption = songs.size()-1;
				}
				else {
					currentoption--;
				}
			}
			if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
				if(currentoption >= songs.size()-1) {
					currentoption = 0;
				}
				else {
					currentoption++;
				}
			}
			if(Gdx.input.isKeyPressed(Keys.ENTER)) {
				Constants.CURRENTSONG = songs.get(currentoption);
				Game.create_new_stage();
			}
			lasttime = Instant.now().toEpochMilli();
		}
	}
	
	public void draw() {
		titlefont.draw(Game.getBatch(), title, Constants.WIDTH/2-100, Constants.HEIGHT/10*9);
		for(int i = 0; i < songs.size(); i++) {
			if(i == currentoption) {
				optionfont.setColor(Color.YELLOW);
			}
			else {
				optionfont.setColor(Color.WHITE);
			}
			String name = "["+songs.get(i).getDifficulty()+"] "+songs.get(i).getName();
			optionfont.draw(Game.getBatch(), name, Constants.WIDTH/2-50, Constants.HEIGHT/10*(8-i));
		}
	}
}
