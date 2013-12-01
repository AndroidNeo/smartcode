package com.almejroz.spiderfull;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.internal.gdip.RectF;

public class WebComposite extends Composite {

    private final double MIN_LENGTH_BY_DESTROY = 10;
	
	public WebComposite(Vec2 origin, double radius, int segments, int depth) {
		super();
		
	    double stiffness = 0.6;
	    double tensor = 0.3;
	    double stride = (2*Math.PI)/segments;
	    int n = segments*depth;
	    double radiusStride = radius/n;
        double shiftTheta = rand.nextDouble()*Math.PI/2.5;
	    int i;
        
	    Composite composite = new Composite();
	    composite.setSpiderweb();

	    DistanceConstraint constraint;
        
	    // particles
	    for (i=0;i<n;++i) {
		    double theta = shiftTheta + (i*stride + Math.cos(i*0.4)*0.05 + Math.cos(i*0.05)*0.2);
		    theta *= (1+0.01*(rand.nextDouble()-0.5));
		    double shrinkingRadius = radius - radiusStride*i + Math.cos(i*0.1)*20;
		    shrinkingRadius *= (1+0.32*(rand.nextDouble()-0.5));

		    double offy = Math.cos(theta*2.1)*(radius/depth)*0.2;
		    composite.particles.add(new Particle(new Vec2(origin.x + Math.cos(theta)*shrinkingRadius, origin.y + Math.sin(theta)*shrinkingRadius + offy)));
	    }

	    for (i=0;i<segments;i+=4)
		    composite.pin(i, null);

	    // constraints
	    
	    for (i=0;i<n-1;++i) {
		    
	    	// neighbor
	    	constraint = new DistanceConstraint((Particle)composite.particles.get(i), (Particle)composite.particles.get(i + 1), stiffness);
	    	constraint.isArcThread = true;
            composite.constraints.add(constraint);

		    // span rings
		    int off = i + segments;
		    if (off < n-1){
		    	constraint = new DistanceConstraint((Particle)composite.particles.get(i), (Particle)composite.particles.get(off), stiffness);
		    	if (i%4 == 0)
		    		constraint.isMainRadialThread = true;
		    	else
		    		constraint.isRadialThread = true;
                composite.constraints.add(constraint);
		    }
		    else{
		    	constraint = new DistanceConstraint((Particle)composite.particles.get(i), (Particle)composite.particles.get(n - 1), stiffness);
		    	if (i%4 == 0)
		    		constraint.isMainRadialThread = true;
		    	else
		    		constraint.isRadialThread = true;
                composite.constraints.add(constraint);
		    }
	    }
	    
        composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(0), (Particle)composite.particles.get(segments - 1), stiffness));

        ArrayList constraints = composite.constraints;

        for(int j = 0; j < constraints.size(); ++j){
            if(constraints.get(j) instanceof DistanceConstraint){
                constraint = (DistanceConstraint)constraints.get(j);
                constraint.distance *= tensor;
            }
        }

	    composites.add(composite);
	    return composite;
    }
	
	
	
	
	
    public PinConstraint pin(int index, Vec2 pos) {
        Vec2 _pos = pos;
        if (_pos == null) {
            _pos = ((Particle)particles.get(index)).pos;
        }

        PinConstraint pc = new PinConstraint((Particle)particles.get(index),_pos);
        constraints.add(pc);
        return pc;
    }
	
    public boolean destroyConstraint(DrawManager dm, Vec2 vScreenTouch){
    	
    	boolean result = false;
    	DistanceConstraint distanceConstraint;
    	Vec2 vTouch;
    	double minLength = 0;
    	int jNearestConstraint = -1;
    	
    	double lengthAB, lengthAT, lengthBT, lengthFromTouchToLineConstraint;
    	double Acoeff, Bcoeff, Ccoeff;
    	boolean isTouchInConstraint;
    	Vec2 AB;
    	
    	
		vTouch = new Vec2(dm.getSimX(vScreenTouch.x), dm.getSimY(vScreenTouch.y)); 
    	
        for(int j = 0; j < constraints.size(); ++j){
            if(constraints.get(j) instanceof DistanceConstraint){
            	distanceConstraint = (DistanceConstraint)constraints.get(j);
            	
            	AB  = distanceConstraint.b.pos.sub(distanceConstraint.a.pos);
            	
            	lengthAB = AB.length();
            	lengthAT = (distanceConstraint.a.pos.sub(vTouch)).length();
            	lengthBT = (distanceConstraint.b.pos.sub(vTouch)).length();
            	
            	isTouchInConstraint = (lengthAT < lengthAB && lengthBT < lengthAB);
            	
            	if(isTouchInConstraint){
            		
            		Acoeff = AB.y; 
            		Bcoeff = -AB.x;
            		Ccoeff = distanceConstraint.a.pos.y*AB.x - distanceConstraint.a.pos.x*AB.y;
            		
            		lengthFromTouchToLineConstraint = Math.abs(Acoeff*vTouch.x + Bcoeff*vTouch.y + Ccoeff) / Math.sqrt(Acoeff*Acoeff + Bcoeff*Bcoeff);
            		
            		if(lengthFromTouchToLineConstraint <= MIN_LENGTH_BY_DESTROY) {
	                	if(jNearestConstraint == -1 || minLength > lengthFromTouchToLineConstraint){
	                		minLength = lengthFromTouchToLineConstraint;
	                		jNearestConstraint = j;
	                	}
            			
            		}
            		
            	}
            	
            }
        }
        
        if(jNearestConstraint != -1){
        	constraints.remove(jNearestConstraint);
        	result = true;
        }
		
    	
    	
    	return result;
    }
	
    public void drawConstraints(PaintEvent c, DrawManager dm)
    {
        RectF rect;
        int i,j;
        int radius;
        float legWidth; 

    	c.gc.setForeground(new Color(c.display, 0, 0, 0));

        for (j = 0; j < constraints.size(); ++j)
        {
            if (constraints.get(j) instanceof DistanceConstraint)
            {
                DistanceConstraint constraint = (DistanceConstraint)constraints.get(j);
                if(constraint.isMainRadialThread || constraint.isRadialThread){
                }else if(constraint.isArcThread){
                }
                c.gc.drawLine((int)dm.getX(constraint.a.pos.x), (int)dm.getY(constraint.a.pos.y), (int)dm.getX(constraint.b.pos.x), (int)dm.getY(constraint.b.pos.y));
            }
        }

    }
	
	
}
