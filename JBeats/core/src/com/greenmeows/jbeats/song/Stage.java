package com.greenmeows.jbeats.song;

import java.time.Instant;
import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.greenmeows.jbeats.Game;
import com.greenmeows.jbeats.constants.Constants;

public class Stage {
	
	private Song song;
	private int[] keybinds = {Keys.Q, Keys.W, Keys.O, Keys.P};
	private ArrayList<Lane> lanes = new ArrayList<Lane>();
	private float x,width;
	private boolean started = false;
	private long timestarted;
	private long endtime;
	private int[] judgements;
	private FreeTypeFontParameter accuracyparam = new FreeTypeFontParameter();
	private BitmapFont accuracydisplayfont;
	
	public BitmapFont getAccuracydisplayfont() {
		return accuracydisplayfont;
	}

	private void generate_lanes() {
		for(int i=0; i < keybinds.length; i++) {
			Lane lane = new Lane(song, keybinds[i], song.getLanes().get(i), this.x+(this.width*i), Constants.HEIGHT, this.width);
			lanes.add(lane);
		}
	}
	
	private void getJudgements() {
		int[] judgements = new int[4];
		for(Lane lane : lanes) {
			int[] lanejudgements = lane.getJudgements();
			for(int i = 0; i < lanejudgements.length; i++) {
				judgements[i] += lanejudgements[i];
			}
		}
		this.judgements = judgements;
	}
	
	private float getScore() {
		float score = 0;
		getJudgements();
		for(int i = 0; i < this.judgements.length; i++) {
			switch(i) {
			case 0:
				score += judgements[i]*Constants.SCORE_MARVELLOUS;
				break;
			case 1:
				score += judgements[i]*Constants.SCORE_PERFECT;
				break;
			case 2:
				score += judgements[i]*Constants.SCORE_GREAT;
				break;
			case 3:
				score += judgements[i]*Constants.SCORE_OKAY;
				break;
			}
		}
		System.out.println("Score: "+score);
		System.out.println("Note Count:"+song.getNotecount());
		return score;
	}
	private float getAccuracy() {
		float maximumscore = song.getNotecount()*Constants.SCORE_MARVELLOUS;
		float ourscore = getScore()/maximumscore;
		return  Math.round(ourscore*100);
	}
	
	public Stage(Song song, float x, float width) {
		this.song = song;
		this.x = x;
		this.width = width;
		accuracyparam.size = 20;
		this.accuracydisplayfont = Game.getGenerator().generateFont(accuracyparam);
	}
	
	public void load() {
		generate_lanes();
		start();
	}
	
	public void start() {
		if(!started) {
			song.getMusic().play();
			timestarted = Instant.now().toEpochMilli()/1000;
			endtime = timestarted+song.getDuration();
			started = true;
		}
	}
	
	public void stop() {
		Constants.CURRENTSTATE = Constants.MENU;
		song.getMusic().stop();
		System.out.println("Quit map");
	}
	
	public void logic() {
		if(started) {
		for(int i = 0; i < lanes.size(); i++) {
			lanes.get(i).logic();
		}
		if(endtime-(Instant.now().toEpochMilli()/1000) < 0) {
			stop();
		}
		}
	}
	
	public void draw() {
		if(started) {
			accuracydisplayfont.draw(Game.getBatch(), new StringBuffer("Accuracy:\n"+getAccuracy()+"%"), Constants.WIDTH/10*9, Constants.HEIGHT/10*9);
			for(int i = 0; i < judgements.length; i++) {
				switch(i) {
				case 0:
					accuracydisplayfont.draw(Game.getBatch(), new StringBuffer("MARVELLOUS: "+judgements[i]), Constants.WIDTH/10, Constants.HEIGHT/3*2-(75*i));
					break;
				case 1:
					accuracydisplayfont.draw(Game.getBatch(), new StringBuffer("PERFECT: "+judgements[i]), Constants.WIDTH/10, Constants.HEIGHT/3*2-(75*i));
					break;
				case 2:
					accuracydisplayfont.draw(Game.getBatch(), new StringBuffer("GREAT: "+judgements[i]), Constants.WIDTH/10, Constants.HEIGHT/3*2-(75*i));
					break;
				case 3:
					accuracydisplayfont.draw(Game.getBatch(), new StringBuffer("OKAY: "+judgements[i]), Constants.WIDTH/10, Constants.HEIGHT/3*2-(75*i));
					break;
				}
			}
			for(int i = 0; i < lanes.size(); i++) {
				lanes.get(i).draw();
			}
		}
	}
	
	
}
