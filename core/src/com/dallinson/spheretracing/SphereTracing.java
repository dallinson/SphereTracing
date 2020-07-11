package com.dallinson.spheretracing;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.dallinson.spheretracing.shapes.Circle;
import com.dallinson.spheretracing.shapes.Shape;

public class SphereTracing extends ApplicationAdapter {
	ShapeRenderer sr;
	Vector2 position;
	ArrayList<Shape> shapes;
	Random r;
	
	@Override
	public void create () {
		sr = new ShapeRenderer();
		position = new Vector2(480,540);
		shapes = new ArrayList<Shape>();
		shapes.add(new Circle(1440,340,50));
		r = new Random();
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			System.exit(0);
		}
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			shapes.add(new Circle(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()), r.nextInt(10) * 10));
		}
		Vector2 motion = new Vector2();
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			motion.add(new Vector2(0,100));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			motion.add(new Vector2(-100,0));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			motion.add(new Vector2(0,-100));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			motion.add(new Vector2(100,0));
		}
		position.mulAdd(motion, Gdx.graphics.getDeltaTime());

		ArrayList<Vector2> points = new ArrayList<Vector2>();
		ArrayList<Float> distances = new ArrayList<Float>();
		Vector2 currPos = position;
		Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

		Vector2 mouseAngle = new Vector2(mousePos).sub(currPos).nor();
		while (true) {
			float minDist = Float.MAX_VALUE;
			for (Shape s : shapes) {
				if (s.get_distance(currPos) < minDist) {
					minDist = s.get_distance(currPos);
				}
			}
			points.add(currPos);
			distances.add(minDist);
			currPos = new Vector2().set(currPos).mulAdd(mouseAngle, minDist);
			if (minDist < 0.0001) {
				break;
			}
			if (currPos.x < 0 || currPos.y < 0) {
				break;
			}
			if (currPos.x > Gdx.graphics.getWidth() || currPos.y > Gdx.graphics.getHeight()) {
				break;
			}
		}
		float minDist = Float.MAX_VALUE;
		for (Shape s : shapes) {
			if (s.get_distance(currPos) < minDist) {
				minDist = s.get_distance(currPos);
			}
		}
		points.add(currPos);
		distances.add(minDist);



		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		sr.rect(0,0,1920,1080);
		sr.setColor(Color.WHITE);
		sr.circle(position.x, position.y, 5);
		for (Shape s : shapes) {
			s.draw(sr);
		}
		sr.rectLine(position,points.get(points.size() - 1), 1);
		sr.end();

		sr.begin(ShapeType.Line);
		sr.setColor(Color.GRAY);
		for (int i = 0; i < points.size(); i++) {
			sr.circle(points.get(i).x,points.get(i).y, distances.get(i));
		}
		sr.end();
	}
	
	@Override
	public void dispose () {
	}
}
