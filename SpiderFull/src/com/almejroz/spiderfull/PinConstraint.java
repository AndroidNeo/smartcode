package com.almejroz.spiderfull;


public class PinConstraint extends Constraint {
    public Particle a;
    public Vec2 pos;

    public PinConstraint(Particle a, Vec2 pos) {
        this.a = a;
        this.pos = new Vec2(pos);
    }

    public void relax(double stepCoef) {
        a.pos.mutableSet(pos);
    }

}
