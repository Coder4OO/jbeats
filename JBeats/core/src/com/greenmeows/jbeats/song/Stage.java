package com.greenmeows.jbeats.song;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.greenmeows.jbeats.constants.Constants;

public class Stage {
	
	private Song song;
	private int[] keybinds = {Keys.Q, Keys.W, Keys.O, Keys.P};
	private ArrayList<Lane> lanes = new ArrayList<Lane>();
	private float x,width;
	private boolean started = false;
	
	
	private void generate_lanes() {
		for(int i=0; i < keybinds.length; i++) {
			Lane lane = new Lane(song, keybinds[i], song.getLanes().get(i), this.x+(this.width*i), Constants.HEIGHT, this.width);
			lanes.add(lane);
		}
	}
	
	public Stage(Song song, float x, float width) {
		this.song = song;
		this.x = x;
		this.width = width;
		start();
	}
	
	private void start() {
		generate_lanes();
		song.getMusic().play();
		started = true;
	}
	
	public void logic() {
		if(started) {
		for(int i = 0; i < lanes.size(); i++) {
			lanes.get(i).logic();
		}
		}
	}
	
	public void draw() {
		if(started) {
		for(int i = 0; i < lanes.size(); i++) {
			lanes.get(i).draw();
		}
		}
	}
	
	
}
