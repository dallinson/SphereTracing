package com.dallinson.spheretracing.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public interface Shape {
    public float get_distance(Vector2 pos);
    public void draw(ShapeRenderer sr);
}