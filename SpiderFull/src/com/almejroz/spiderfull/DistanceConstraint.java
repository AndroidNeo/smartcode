package com.almejroz.spiderfull;


public class DistanceConstraint extends Constraint {
	public Particle a;
    public Particle b;
    public double distance;
    public double stiffness;
    public boolean isRadialThread;
    public boolean isArcThread;
    public boolean isMainRadialThread;
    
    public DistanceConstraint(Particle a, Particle b, double stiffness) {
    	setConstraintParameters(a, b, stiffness, Double.NaN);
    }

    public DistanceConstraint(Particle a, Particle b, double stiffness, double distance) {
    	setConstraintParameters(a, b, stiffness, distance);
    }

    public void relax(double stepCoef) {
        Vec2 normal = a.pos.sub(b.pos);
        double m = normal.length2();
        normal.mutableScale(((distance*distance - m)/m)*stiffness*stepCoef);
        a.pos.mutableAdd(normal);
        b.pos.mutableSub(normal);
    }
    
    private void setConstraintParameters(Particle a, Particle b, double stiffness, double distance){
    	
    	this.a = a;
    	this.b = b;
    	this.stiffness = stiffness;
    	this.distance  = distance;
    	
    	if(Double.isNaN(this.distance))
    		this.distance  = a.pos.sub(b.pos).length();
    	
    	this.isRadialThread = false;
    	this.isArcThread    = false;
    	this.isMainRadialThread = false;
    	
    }


}
