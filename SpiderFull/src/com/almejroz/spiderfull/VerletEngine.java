package com.almejroz.spiderfull;
/*
Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/



import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Resources;
import javax.naming.Context;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;


public class VerletEngine {

	
	
    int width, height;
    Vec2 gravity;
    Vec2 gravityNorm;
    double friction;
    double groundFriction;
    ArrayList<Composite> composites;
    Random rand;
    DrawManager mDM;
    
    //Bitmap mBackgroundBitmap;
    //int mBackgroundId;
    
    public static final String TAG = "ART";
    
    //private final Paint mPaintBackground = new Paint();
    
    public VerletEngine(int width, int height){
    
        this.width = width;
        this.height = height;
        
        gravityNorm = new Vec2(0,0.2);
        gravity = new Vec2(gravityNorm);
        friction = 0.99;
        groundFriction = 0.8;

        composites = new ArrayList();

        rand = new Random(System.currentTimeMillis());
        
        setDrawManager(0, 0);
        
        
        
       
        
     }

	
    
    public void setDrawManager(int screenWidth, int screenHeight){
    	mDM = new DrawManager(screenWidth, screenHeight, width, height);
        
    	
    }

    
   
    
    
    
    public void bounds(Particle particle) {
	        if (particle.pos.y > height-1)
		        particle.pos.y = height-1;

	        if (particle.pos.x < 0)
		        particle.pos.x = 0;

	        if (particle.pos.x > width-1)
		        particle.pos.x = width-1;
    }


    public void frame(int step) {

        for(Composite composite : composites){
            
            for(Particle particle : composite.particles){

	        	// calculate velocity
	        	Vec2 velocity = particle.pos.sub(particle.lastPos).scale(friction);

		        // ground friction
		        if (particle.pos.y >= height-1 && velocity.length2() > 0.000001) {
			        double m = velocity.length();
			        velocity.x /= m;
			        velocity.y /= m;
			        velocity.mutableScale(m*groundFriction);
		        }
		        
		        // save last good state
		        particle.lastPos.mutableSet(particle.pos);

		        // gravity
		        particle.pos.mutableAdd(gravity);

		        // inertia  
		        particle.pos.mutableAdd(velocity);

            }

        }

        // relax
        double stepCoef = 1 / (double)step;
        
        for(Composite composite : composites) {
        	ArrayList constraints = composite.constraints;
	        for (int i=0; i<step; ++i)
                for(int j = 0; j < constraints.size(); ++j){
                    
                    if(constraints.get(j) instanceof PinConstraint){
                        ((PinConstraint)constraints.get(j)).relax(stepCoef);
                    }
                    
                    if(constraints.get(j) instanceof DistanceConstraint){
                        ((DistanceConstraint)constraints.get(j)).relax(stepCoef);
                    }
                        
                    if(constraints.get(j) instanceof AngleConstraint){
                        ((AngleConstraint)constraints.get(j)).relax(stepCoef);
                    }

                }
        }

        // bounds checking
        for(Composite composite : composites) {
	        for (Particle particle : composite.particles)
                bounds(particle);
        }
    }

    public void draw(PaintEvent e, Shell shell) {
    	
    	Point pp = shell.getSize();
    	setDrawManager(pp.x, pp.y);
    	
    	e.gc.setBackground(new Color(e.display, 255, 255, 255));
		e.gc.fillRectangle(shell.getClientArea());    	
    	
        for (Composite composite : composites) {
        	composite.drawConstraints(e, mDM);
        	composite.drawParticles(e, mDM);
        }
        
    }


	
}
