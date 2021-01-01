package com.sekwah.mira4j.math;

public class Vector2 {
    // TODO: Make this immutable
    public static final Vector2 ZERO = new Vector2(0, 0);

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 v) {
        if(v == null) return;
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2 add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public float distance(Vector2 v) {
        float xx = x - v.x;
        float yy = y - v.y;
        return (float)Math.sqrt(xx * xx + yy * yy);
    }

    public float magnitude() {
        return (float)Math.sqrt(x * x + y * y);
    }

    public boolean isFinite() {
        return Float.isFinite(x) && Float.isFinite(y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2 clone() {
        return new Vector2(x, y);
    }

    public String toString() {
        return String.format("{ x=%.4f, y=%.4f }", x, y);
    }

    public Vector2 mul(float v) {
        this.x *= v;
        this.y *= v;
        return this;
    }

    public Vector2 normalize() {
        float mag = magnitude();
        if(mag == 0) return this;
        this.x = x / mag;
        this.y = y / mag;
        return this;
    }
}
