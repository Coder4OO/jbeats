package com.greenmeows.jbeats;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.greenmeows.jbeats.constants.Constants;
import com.greenmeows.jbeats.song.Song;
import com.greenmeows.jbeats.song.SongMenu;
import com.greenmeows.jbeats.song.Stage;

public class Game extends ApplicationAdapter {
	static SpriteBatch batch;
	static Stage stage;
	FreeTypeFontParameter titleparameter;
	FreeTypeFontParameter optionparameter;
	BitmapFont titlefont;
	BitmapFont optionfont;
	Song currentsong;
	SongMenu menu;
	
	static FreeTypeFontGenerator generator;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts\\CaviarDreams.ttf"));
		titleparameter = new FreeTypeFontParameter();
		optionparameter = new FreeTypeFontParameter();
		titlefont = generator.generateFont(titleparameter);
		optionfont = generator.generateFont(optionparameter);
		menu = new SongMenu(titlefont, optionfont, Constants.getAllSongs());
		Song currentsong = Constants.getAllSongs().get(0);
		stage = new Stage(currentsong, Constants.WIDTH/7.5F, 250);
	}

	public static void create_new_stage() {
		stage = new Stage(Constants.CURRENTSONG, Constants.WIDTH/7.5F, 250);
		Constants.CURRENTSTATE = Constants.STAGE;
		stage.load();
	}
	
	private void sprites() {
		batch.begin();
		switch(Constants.CURRENTSTATE) {
		case Constants.MENU:
			menu.draw();
			break;
		case Constants.STAGE:
			stage.draw();
			break;
		}
		batch.end();
	}

	private void draw() {
		sprites();
	}
	
	private void logic() {
		switch(Constants.CURRENTSTATE) {
		case Constants.MENU:
			menu.logic();
			break;
		case Constants.STAGE:
			stage.logic();
			break;
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		draw();
		logic();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		generator.dispose();
		titlefont.dispose();
		optionfont.dispose();
	}

	public static SpriteBatch getBatch() {
		return batch;
	}
}
