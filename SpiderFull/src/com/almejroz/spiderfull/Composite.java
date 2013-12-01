package com.almejroz.spiderfull;


import java.util.ArrayList;

import javax.annotation.Resources;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.internal.gdip.RectF;

public abstract class Composite {
    
	public ArrayList<Particle>   particles;
    public ArrayList<Constraint> constraints;
 
    public Composite() { 
        particles = new ArrayList<Particle>();
        constraints = new ArrayList<Constraint>();
    }

    public abstract void drawParticles(PaintEvent c, DrawManager dm);
    
    public abstract void drawConstraints(PaintEvent c, DrawManager dm);

}
