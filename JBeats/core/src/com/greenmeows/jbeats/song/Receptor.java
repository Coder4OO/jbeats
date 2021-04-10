package com.greenmeows.jbeats.song;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenmeows.jbeats.constants.Constants;

public class Receptor {
	
	private Sprite sprite;
	private SpriteBatch batch;
	private float x,y,width,height;
	private Lane lane;
	public Receptor(float x, float y, float width, float height, SpriteBatch batch, Lane lane) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		Texture texture = new Texture(Gdx.files.internal("note.png"));
		this.sprite = new Sprite(texture);
		this.sprite.setSize(width, height);
		this.sprite.setPosition(x, y);
		this.batch = batch;
		this.lane = lane;
	}
	
	
	
	public void draw() {
		sprite.setPosition(x, y);
		sprite.setSize(width, height);
		sprite.draw(batch);
	}

	public Sprite getSprite() {
		return sprite;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public float calculateMs(float hitY) {
		float factor = lane.getSpeed()/Constants.STANDARDSCROLLSPEED;
		float scrolltimeinseconds = (float) (Math.pow(Constants.STANDARDSCROLLSPEED/lane.getOrigin(), -1)/factor);
		float distance = Math.abs(hitY-this.y);
		float time = distance/lane.getOrigin()*scrolltimeinseconds;
		float ms = time*1000;
		return ms;
	}
}
