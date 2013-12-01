package com.almejroz.spiderfull;



public class AngleConstraint extends Constraint {
    
	public Particle a;
    public Particle b;
    public Particle c;
    public double angle;
    public double stiffness;
    
    public AngleConstraint(Particle a, Particle b, Particle c, double stiffness) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.angle = b.pos.angle2(a.pos, c.pos);
        this.stiffness = stiffness;
    }

    public void relax(double stepCoef) {
        double newAngle = b.pos.angle2(a.pos, c.pos);
        double diff = newAngle - angle;

        if (diff <= -Math.PI)
            diff += 2 * Math.PI;
        else if (diff >= Math.PI)
            diff -= 2 * Math.PI;

        diff *= stepCoef*stiffness;

        a.pos = a.pos.rotate(b.pos, diff);
        c.pos = c.pos.rotate(b.pos, -diff);
        b.pos = b.pos.rotate(a.pos, diff);
        b.pos = b.pos.rotate(c.pos, -diff);
    }

 	
}
