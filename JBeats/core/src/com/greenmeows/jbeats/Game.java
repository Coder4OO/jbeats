package com.greenmeows.jbeats;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenmeows.jbeats.constants.Constants;
import com.greenmeows.jbeats.song.Song;
import com.greenmeows.jbeats.song.Stage;

public class Game extends ApplicationAdapter {
	static SpriteBatch batch;
	Stage stage;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Song song = new Song(Constants.get_song_file()+"BetterDays"+"\\");
		stage = new Stage(song, Constants.WIDTH/7.5F, 250);
	}

	private void sprites() {
		batch.begin();
		stage.draw();
		batch.end();
	}

	private void draw() {
		sprites();
	}
	
	private void logic() {
		stage.logic();
	}
	
	public static SpriteBatch getBatch() {
		return batch;
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
	}
}
