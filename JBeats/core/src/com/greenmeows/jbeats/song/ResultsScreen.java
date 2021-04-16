package com.greenmeows.jbeats.song;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.greenmeows.jbeats.Game;
import com.greenmeows.jbeats.constants.Constants;

public class ResultsScreen {
	
	private int[] judgements = new int[4];
	private float accuracy;
	private String grade;
	private BitmapFont titlefont;
	private BitmapFont judgementfont;
	private BitmapFont gradefont;
	private int miss;
	private FreeTypeFontParameter titleparameter = new FreeTypeFontParameter();
	private FreeTypeFontParameter judgementparameter = new FreeTypeFontParameter();
	private FreeTypeFontParameter gradeparameter = new FreeTypeFontParameter();
	private Color gradecolor;
	
	private String getGrade(float accuracy) {
		if(accuracy >= Constants.GRADE_S) {
			gradecolor = Color.GOLD;
			return "S";
		}
		else if(accuracy < Constants.GRADE_S && accuracy >= Constants.GRADE_A) {
			gradecolor = Color.CYAN;
			return "A";
		}
		else if(accuracy < Constants.GRADE_A && accuracy >= Constants.GRADE_B) {
			gradecolor = Color.LIME;
			return "B";
		}
		else if(accuracy < Constants.GRADE_B && accuracy >= Constants.GRADE_C) {
			gradecolor = Color.ORANGE;
			return "C";
		}
		else {
			gradecolor = Color.RED;
			return "F";
		}
	}
	
	private int getMiss(int notecount) {
		int miss = 0;
		int notes = 0;
		for(int i=0; i < judgements.length; i++) {
			notes += judgements[i];
		}
		miss = notecount-notes;
		return miss;
	}
	
	public ResultsScreen(int[] judgements, float accuracy, int notecount) {
		this.judgements = judgements;
		this.accuracy = accuracy;
		this.grade = getGrade(this.accuracy);
		this.titleparameter.size = 40;
		this.titlefont = Game.getGenerator().generateFont(titleparameter);
		this.judgementparameter.size = 30;
		this.judgementfont = Game.getGenerator().generateFont(judgementparameter);
		this.gradeparameter.size = 250;
		this.gradefont = Game.getGenerator().generateFont(gradeparameter);
		this.gradefont.setColor(gradecolor);
		this.titlefont.setColor(Color.WHITE);
		this.judgementfont.setColor(Color.WHITE);
		this.miss = getMiss(notecount);
	}
	
	private void back_to_menu() {
		titlefont.dispose();
		judgementfont.dispose();
		gradefont.dispose();
		Constants.CURRENTSTATE = Constants.MENU;
	}
	
	public void logic() {
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			back_to_menu();
		}
	}
	
	public void draw() {
		titlefont.draw(Game.getBatch(), new StringBuffer("RESULTS"), Constants.WIDTH/2, Constants.HEIGHT/10*9);
		for(int i=0; i < this.judgements.length+1; i++) {
			switch(i) {
			case 0:
				judgementfont.draw(Game.getBatch(), new StringBuffer("MARVELLOUS: "+judgements[i]), Constants.WIDTH/10, Constants.HEIGHT/4*3-(100*i));
				break;
			case 1:
				judgementfont.draw(Game.getBatch(), new StringBuffer("PERFECT: "+judgements[i]), Constants.WIDTH/10, Constants.HEIGHT/4*3-(100*i));
				break;
			case 2:
				judgementfont.draw(Game.getBatch(), new StringBuffer("GREAT: "+judgements[i]), Constants.WIDTH/10, Constants.HEIGHT/4*3-(100*i));
				break;
			case 3:
				judgementfont.draw(Game.getBatch(), new StringBuffer("OKAY: "+judgements[i]), Constants.WIDTH/10, Constants.HEIGHT/4*3-(100*i));
				break;
			case 4:
				judgementfont.draw(Game.getBatch(), new StringBuffer("MISS: "+miss), Constants.WIDTH/10, Constants.HEIGHT/4*3-(100*i));
				break;
			}
		}
		gradefont.draw(Game.getBatch(), new StringBuffer(this.grade), Constants.WIDTH/2, Constants.HEIGHT/3*2);
	}
	
	
}
