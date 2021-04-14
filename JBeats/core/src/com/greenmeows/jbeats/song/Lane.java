package com.greenmeows.jbeats.song;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.greenmeows.jbeats.Game;
import com.greenmeows.jbeats.constants.Constants;

public class Lane {
	
	private Song song;
	private int keybind;
	private ArrayList<Float> notes = new ArrayList<Float>();
	private ArrayList<Receptor> renderednotes = new ArrayList<Receptor>();
	private float speed;
	private float x,y,width;
	private float origin;
	private Receptor hitbox;
	private Texture hitTexture = new Texture(Gdx.files.internal("hitnote.png"));
	private Texture defaultTexture = new Texture(Gdx.files.internal("note.png"));
	private int[] judgementstotal = new int[4];
	
	private float calculate_scroll_speed() {
		float bpm = song.getSpeed();
		float factor = bpm/100;
		return factor*Constants.STANDARDSCROLLSPEED;
	}
	
	private float calculate_origin(float speed, float offset) {
		float origin = (float) (Math.ceil(Constants.HEIGHT/speed)*speed);
		return origin+offset;
	}
	
	private void adjust_note_value(float speed, float offset) {
		for(int i=0; i < notes.size(); i++) {
			float pos = i*(speed+offset);
			notes.set(i, notes.get(i)*pos);
		}
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getOrigin() {
		return origin;
	}

	public Lane(Song song, int keybind, ArrayList<Float> notes, float x, float y, float width) {
		this.song = song;
		this.keybind = keybind;
		this.notes = notes;
		this.speed = calculate_scroll_speed();
		this.origin = calculate_origin(this.speed, Constants.HITOFFSET);
		adjust_note_value(this.speed, Constants.HITOFFSET);
		this.x = x;
		this.y = y;
		this.width = width;
		this.hitbox = new Receptor(x, Constants.HITOFFSET, width, 200, Game.getBatch(), this);
	}
	
	public int getKeybind() {
		return keybind;
	}

	public ArrayList<Float> getQueuedNotes() {
		return notes;
	}

	public ArrayList<Receptor> getRenderedNotes(){
		return renderednotes;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public int[] getJudgements() {
		return judgementstotal;
	}

	private void antilag() {
		for(Iterator<Float> it = notes.iterator(); it.hasNext();) {
			try {
				if(it.next() < origin) {
					float value = it.next();
					it.remove();
					if(value > 0){
						Receptor note = new Receptor(x, value, width, 200, Game.getBatch(), this);
						note.setSprite(new Sprite(defaultTexture));
						renderednotes.add(note);
					}
				}
			} catch(Exception e) {
				continue;
			}
		}
		for(Iterator<Receptor> it = renderednotes.iterator(); it.hasNext();) {
			if(it.next().getY() < -speed) {
				it.remove();
			}
		}
	}
	
	private void scroll() {
		for(int i=0; i < notes.size(); i++) {
			notes.set(i, notes.get(i)-speed*Gdx.graphics.getDeltaTime());
		}
		for(int i = 0; i < renderednotes.size(); i++) {
			Receptor note = renderednotes.get(i);
			note.setY(note.getY()-speed*Gdx.graphics.getDeltaTime());
		}
	}
	
	
	private int get_closest_note() {
		int resultindex = 0;
		float smallestY = Float.POSITIVE_INFINITY;
		for(int i=0; i < renderednotes.size(); i++) {
			if(renderednotes.get(i).getY() < smallestY) {
				resultindex = i;
				smallestY = renderednotes.get(i).getY();
			}
		}
		return resultindex;
	}
	
	
	private float getAccuracyScore() {
		if(renderednotes.size() > 0) {
			float hitY = hitbox.getY();
			int closestnote = get_closest_note();
			float time = renderednotes.get(closestnote).calculateMs(hitY);
			if(time <= Constants.TIMING_MARVELLOUS) {
				System.out.println("MARVELLOUS!");
				renderednotes.remove(closestnote);
				judgementstotal[0]++;
				return Constants.SCORE_MARVELLOUS;
			}
			else if(time > Constants.TIMING_MARVELLOUS && time <= Constants.TIMING_PERFECT) {
				System.out.println("PERFECT");
				renderednotes.remove(closestnote);
				judgementstotal[1]++;
				return Constants.SCORE_PERFECT;
			}
			else if(time > Constants.TIMING_PERFECT && time <= Constants.TIMING_GREAT) {
				renderednotes.remove(closestnote);
				System.out.println("GREAT");
				judgementstotal[2]++;
				return Constants.SCORE_GREAT;
			}
			else if(time > Constants.TIMING_GREAT && time <= Constants.TIMING_OKAY) {
				renderednotes.remove(closestnote);
				System.out.println("OKAY");
				judgementstotal[3]++;
				return Constants.SCORE_OKAY;
			}
			else {
				return 0;
			}
		}
		else {
			return 0;
		}
	}
	
	private void handle_hit() {
		if(Gdx.input.isKeyPressed(keybind)) {
			hitbox.setSprite(new Sprite(defaultTexture));
			if(Gdx.input.isKeyJustPressed(keybind)) {
				getAccuracyScore();
			}
		}
		else {
			hitbox.setSprite(new Sprite(hitTexture));
		}
	}
	
	public void logic() {
		handle_hit();
		scroll();
		antilag();
	}
	
	
	public void draw() {
		hitbox.draw();
		for(int i = 0; i < renderednotes.size(); i++) {
			Receptor note = renderednotes.get(i);
			note.draw();
		}
	}
	
}
