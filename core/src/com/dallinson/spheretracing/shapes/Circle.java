package com.dallinson.spheretracing.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Circle implements Shape {
    private Vector2 position;
    private float radius;

    public Circle(float x, float y, float radius) {
        this(new Vector2(x,y), radius);
    }

    public Circle(Vector2 pos, float radius) {
        this.position = pos;
        this.radius = radius;
    }

    public float get_distance(Vector2 inputPosition) {
        Vector2 adjustedPosition = new Vector2().set(position).sub(inputPosition);
        return adjustedPosition.len() - radius;
    }

    public void draw(ShapeRenderer sr) {
        sr.circle(position.x, position.y, radius);
    }
}