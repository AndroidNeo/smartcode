package com.almejroz.spiderfull;


public class Vec2 {

    public double x;
    public double y;

    public Vec2(double _x, double _y) {
        x = _x;
        y = _y;
    }

    public Vec2(Vec2 v) {
        x = v.x;
        y = v.y;
    }

    public Vec2 add(Vec2 v) {
        return new Vec2(x + v.x, y + v.y);
    }

    public Vec2 sub(Vec2 v) {
        return new Vec2(x - v.x, y - v.y);
    }

    public Vec2 mul(Vec2 v) {
        return new Vec2(x * v.x, y * v.y);
    }

    public Vec2 div(Vec2 v) {
        return new Vec2(x / v.x, y / v.y);
    }

    public Vec2 scale(double coef) {
        return new Vec2(x*coef, y*coef);
    }

    public Vec2 mutableSet(Vec2 v)
    {
        x = v.x;
        y = v.y;
        return this;
    }

    public Vec2 mutableAdd(Vec2 v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vec2 mutableSub(Vec2 v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vec2 mutableMul(Vec2 v) {
        x *= v.x;
        y *= v.y;
        return this;
    }

    public Vec2 mutableDiv(Vec2 v) {
        x /= v.x;
        y /= v.y;
        return this;
    }

    public Vec2 mutableScale(double coef) {
        x *= coef;
        y *= coef;
        return this;
    }

    public boolean equals(Vec2 v) {
        return x == v.x && y == v.y;
    }

    public boolean epsilonEquals(Vec2 v, double epsilon) {
        return Math.abs(x - v.x) <= epsilon && Math.abs(y - v.y) <= epsilon;
    }

    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    public double length2() {
        return x*x + y*y;
    }

    public double dist(Vec2 v) {
        return Math.sqrt(this.dist2(v));
    }

    public double dist2(Vec2 v) {
        double _x = v.x - x;
        double _y = v.y - y;
        return _x*_x + _y*_y;
    }

    public Vec2 normal() {
        double m = Math.sqrt(x*x + y*y);
        return new Vec2(x/m, y/m);
    }

    public double dot(Vec2 v) {
        return x*v.x + y*v.y;
    }

    public double angle(Vec2 v) {
        return Math.atan2(x*v.y-y*v.x, x*v.x+y*v.y);
    }

    public double angle2(Vec2 vLeft, Vec2 vRight) {
        return vLeft.sub(this).angle(vRight.sub(this));
    }

    public Vec2 rotate(Vec2 origin, double theta) {
        double _x = x - origin.x;
        double _y = y - origin.y;
        return new Vec2(_x*Math.cos(theta) - _y*Math.sin(theta) + origin.x, _x*Math.sin(theta) + _y*Math.cos(theta) + origin.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
	
}
