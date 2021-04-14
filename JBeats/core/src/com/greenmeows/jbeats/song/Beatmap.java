package com.greenmeows.jbeats.song;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class Beatmap {
	
	private String path;
	private File file;
	private Scanner scanner;
	
	private String name;
	private int duration;
	private float speed;
	private Music music;
	private float difficulty;
	private int notecount;
	
	
	private ArrayList<Float> lane1 = new ArrayList<Float>();
	private ArrayList<Float> lane2 = new ArrayList<Float>();
	private ArrayList<Float> lane3 = new ArrayList<Float>();
	private ArrayList<Float> lane4 = new ArrayList<Float>();
	
	private ArrayList<ArrayList<Float>> lanes = new ArrayList<ArrayList<Float>>();
	
	private void read_data(File file) {
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			File errorlog = new File("errorlog.txt");
			try {
				FileWriter writer = new FileWriter(errorlog);
				writer.write("Map not found, please contact the map author.");
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File songstorage = new File(file.getParentFile().getPath());
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains("/")) {
				// settings line
				String[] split = line.split("/");
				String info = split[0];
				String data = split[1];
				System.out.println(info+": "+data);
				switch(info.toLowerCase()) {
				case "name":
					name = data;
					break;
				case "duration":
					duration = Integer.parseInt(data);
					break;
				case "speed":
					speed = Float.parseFloat(data);
					break;
				case "difficulty":
					difficulty = Float.parseFloat(data);
					break;
				case "path":
					music = Gdx.audio.newMusic(new FileHandle(new File(songstorage.getPath()+"\\"+data)));
					break;
				}
			} else {
				// map line
				String[] lanevalues = line.split(",");
				for(int i=0; i < lanevalues.length; i++) {
					switch(i) {
					case 0:
						lane1.add(Float.parseFloat(lanevalues[i]));
						notecount += Float.parseFloat(lanevalues[i]);
						break;
					case 1:
						lane2.add(Float.parseFloat(lanevalues[i]));
						notecount += Float.parseFloat(lanevalues[i]);
						break;
					case 2:
						lane3.add(Float.parseFloat(lanevalues[i]));
						notecount += Float.parseFloat(lanevalues[i]);
						break;
					case 3:
						lane4.add(Float.parseFloat(lanevalues[i]));
						notecount += Float.parseFloat(lanevalues[i]);
						break;
					}
				}
			}
		}
		lanes.add(lane1);
		lanes.add(lane2);
		lanes.add(lane3);
		lanes.add(lane4);
		scanner.close();
	}
	
	protected String getName() {
		return name;
	}

	protected int getDuration() {
		return duration;
	}

	protected float getSpeed() {
		return speed;
	}

	protected Music getMusic() {
		return music;
	}
	
	protected float getDifficulty() {
		return difficulty;
	}
	
	protected ArrayList<ArrayList<Float>> getLanes(){
		return lanes;
	}
	
	protected int getNotecount() {
		return notecount;
	}

	public Beatmap(String path) {
		this.path = path;
		this.file = new File(path+"\\"+"beatmap.txt");
		read_data(this.file);
	}
	
}
