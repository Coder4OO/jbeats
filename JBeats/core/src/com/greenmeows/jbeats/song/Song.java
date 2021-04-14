package com.greenmeows.jbeats.song;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Music;

public class Song {
	
	private String name;
	private int duration;
	private float speed;
	private String path;
	private Music music;
	private Beatmap beatmap;
	private float difficulty;
	
	public Song(String beatmappath) {
		this.beatmap = new Beatmap(beatmappath);
		this.duration = beatmap.getDuration();
		this.speed = beatmap.getSpeed();
		this.name = beatmap.getName();
		this.music = beatmap.getMusic();
		this.difficulty = beatmap.getDifficulty();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getDuration() {
		return duration;
	}

	public String getPath() {
		return path;
	}
	
	public Music getMusic() {
		return music;
	}
	
	public Beatmap getBeatmap() {
		return beatmap;
	}
	
	public ArrayList<ArrayList<Float>> getLanes(){
		return beatmap.getLanes();
	}
	
	public float getDifficulty() {
		return difficulty;
	}
	
	public int getNotecount() {
		return beatmap.getNotecount();
	}
	
}
