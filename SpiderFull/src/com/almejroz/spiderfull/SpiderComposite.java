package com.almejroz.spiderfull;

import java.util.ArrayList;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.internal.gdip.RectF;

public class SpiderComposite extends Composite {

    public ArrayList legs;
    public Particle  head;
	public Particle  thorax;
    public Particle  abdomen;
	
	
	public SpiderComposite(Vec2 origin) {
		super();
        
		legs = new ArrayList();
		
        int i;
	    double legSeg1Stiffness = 0.99;
	    double legSeg2Stiffness = 0.99;
	    double legSeg3Stiffness = 0.99;
	    double legSeg4Stiffness = 0.99;

	    double joint1Stiffness = 1;
	    double joint2Stiffness = 0.4;
	    double joint3Stiffness = 0.9;

	    double bodyStiffness = 1;
	    double bodyJointStiffness = 1;

        int len;

        Particle particle;

	    Composite composite = new Composite();
	    composite.setSpider(mDM);

	    composite.head    = new Particle(origin.add(new Vec2(0,-5)));
	    composite.thorax  = new Particle(origin);
	    composite.abdomen = new Particle(origin.add(new Vec2(0,10)));

	    composite.particles.add(composite.thorax);
	    composite.particles.add(composite.head);
	    composite.particles.add(composite.abdomen);

	    composite.constraints.add(new DistanceConstraint(composite.head, composite.thorax, bodyStiffness));

	    composite.constraints.add(new DistanceConstraint(composite.abdomen, composite.thorax, bodyStiffness));
	    composite.constraints.add(new AngleConstraint(composite.abdomen, composite.thorax, composite.head, 0.4));

	    // legs
	    for (i=0;i<4;++i) {

            particle = (Particle)composite.particles.get(0);

		    composite.particles.add(new Particle(particle.pos.add(new Vec2(3,(i-1.5)*3))));
		    composite.particles.add(new Particle(particle.pos.add(new Vec2(-3,(i-1.5)*3))));

		    len = composite.particles.size();

		    composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(len-2), composite.thorax, legSeg1Stiffness));
		    composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(len-1), composite.thorax, legSeg1Stiffness));
		    
		    double lenCoef = 1;
		    if (i == 1 || i == 2)
			    lenCoef = 0.7;
		    else if (i == 3)
			    lenCoef = 0.9;

            particle = (Particle)composite.particles.get(len-2);
		    composite.particles.add(new Particle(particle.pos.add((new Vec2(20,(i-1.5)*30)).normal().mutableScale(20*lenCoef))));

            particle = (Particle)composite.particles.get(len-1);
		    composite.particles.add(new Particle(particle.pos.add((new Vec2(-20,(i-1.5)*30)).normal().mutableScale(20*lenCoef))));

		    len = composite.particles.size();

		    composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(len-4), (Particle)composite.particles.get(len-2), legSeg2Stiffness));
		    composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(len-3), (Particle)composite.particles.get(len-1), legSeg2Stiffness));

            particle = (Particle)composite.particles.get(len - 2);
            composite.particles.add(new Particle(particle.pos.add((new Vec2(20, (i - 1.5) * 50)).normal().mutableScale(20 * lenCoef))));

            particle = (Particle)composite.particles.get(len - 1);
            composite.particles.add(new Particle(particle.pos.add((new Vec2(-20, (i - 1.5) * 50)).normal().mutableScale(20 * lenCoef))));

		    len = composite.particles.size();
		    composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(len-4), (Particle)composite.particles.get(len-2), legSeg3Stiffness));
		    composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(len-3), (Particle)composite.particles.get(len-1), legSeg3Stiffness));

            particle = (Particle)composite.particles.get(len - 2);
            Particle rightFoot = new Particle(particle.pos.add((new Vec2(20, (i - 1.5) * 100)).normal().mutableScale(10 * lenCoef)));

            particle = (Particle)composite.particles.get(len - 1);
            Particle leftFoot = new Particle(particle.pos.add((new Vec2(-20, (i - 1.5) * 100)).normal().mutableScale(10 * lenCoef)));
		    
            composite.particles.add(rightFoot);
		    composite.particles.add(leftFoot);

		    composite.legs.add(rightFoot);
		    composite.legs.add(leftFoot);

		    len = composite.particles.size();
            composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(len - 4), (Particle)composite.particles.get(len - 2), legSeg4Stiffness));
            composite.constraints.add(new DistanceConstraint((Particle)composite.particles.get(len - 3), (Particle)composite.particles.get(len - 1), legSeg4Stiffness));

            composite.constraints.add(new AngleConstraint((Particle)composite.particles.get(len - 6), (Particle)composite.particles.get(len - 4), (Particle)composite.particles.get(len - 2), joint3Stiffness));
            composite.constraints.add(new AngleConstraint((Particle)composite.particles.get(len - 6 + 1), (Particle)composite.particles.get(len - 4 + 1), (Particle)composite.particles.get(len - 2 + 1), joint3Stiffness));

            composite.constraints.add(new AngleConstraint((Particle)composite.particles.get(len - 8), (Particle)composite.particles.get(len - 6), (Particle)composite.particles.get(len - 4), joint2Stiffness));
            composite.constraints.add(new AngleConstraint((Particle)composite.particles.get(len - 8 + 1), (Particle)composite.particles.get(len - 6 + 1), (Particle)composite.particles.get(len - 4 + 1), joint2Stiffness));

            composite.constraints.add(new AngleConstraint((Particle)composite.particles.get(0), (Particle)composite.particles.get(len - 8), (Particle)composite.particles.get(len - 6), joint1Stiffness));
            composite.constraints.add(new AngleConstraint((Particle)composite.particles.get(0), (Particle)composite.particles.get(len - 8 + 1), (Particle)composite.particles.get(len - 6 + 1), joint1Stiffness));

            composite.constraints.add(new AngleConstraint((Particle)composite.particles.get(1), (Particle)composite.particles.get(0), (Particle)composite.particles.get(len - 8), bodyJointStiffness));
            composite.constraints.add(new AngleConstraint((Particle)composite.particles.get(1), (Particle)composite.particles.get(0), (Particle)composite.particles.get(len - 8 + 1), bodyJointStiffness));
	    }

        this.composites.add(composite);
	    return composite;        
    }


    public void crawl(int leg) {

	    double stepRadius = 100;
	    double minStepRadius = 35;

	    Composite spiderweb = (Composite)composites.get(0);
	    Composite spider    = (Composite)composites.get(1);

        Particle particle0 = (Particle)spider.particles.get(0);
        Particle particle1 = (Particle)spider.particles.get(1);
	    double theta = particle0.pos.angle2(particle0.pos.add(new Vec2(1,0)), particle1.pos);

	    Vec2 boundry1 = new Vec2(Math.cos(theta), Math.sin(theta));
	    Vec2 boundry2 = new Vec2(Math.cos(theta+Math.PI/2), Math.sin(theta+Math.PI/2));

	    double flag1 = leg < 4 ? 1 : -1;
	    double flag2 = leg%2 == 0 ? 1 : 0;

	    ArrayList paths = new ArrayList();

	    int i,j;
        boolean leftFoot = false;

	    for (Particle particle : spiderweb.particles) {
		    if (
			    particle.pos.sub(((Particle)spider.particles.get(0)).pos).dot(boundry1)*flag1 >= 0
			    && particle.pos.sub(((Particle)spider.particles.get(0)).pos).dot(boundry2)*flag2 >= 0
		    ) {
			    double d2 = particle.pos.dist2(((Particle)spider.particles.get(0)).pos);

			    if (!(d2 >= minStepRadius*minStepRadius && d2 <= stepRadius*stepRadius))
				    continue;

			    leftFoot = false;
                for (j=0;j<spider.constraints.size();++j) {
				    for (int k=0;k<8;++k) {
                        
					    if ( spider.constraints.get(j) instanceof DistanceConstraint
                            && ((DistanceConstraint)spider.constraints.get(j)).a == spider.legs.get(k)
                            && ((DistanceConstraint)spider.constraints.get(j)).b == particle ) 
                        {

						    leftFoot = true;
					    }
				    }
			    }

			    if (!leftFoot)
				    paths.add(particle);
		    }
	    }

	   for (i=0;i<spider.constraints.size();++i) {
           if (spider.constraints.get(i) instanceof DistanceConstraint && ((DistanceConstraint)spider.constraints.get(i)).a == spider.legs.get(leg))
           {
			    spider.constraints.remove(i);
			    break;
		    }
	    }

	    if (paths.size() > 0) {
		    shuffle(paths);
		    spider.constraints.add(new DistanceConstraint((Particle)spider.legs.get(leg), (Particle)paths.get(0), 1, 0));
	    }
    }

    
    
	public ArrayList shuffle(ArrayList o) {
	    int i, j;
	    Particle x;
	    
	    for (i = o.size(); i > 0; j = (int)Math.floor(rand.nextDouble() * i), x = (Particle)o.get(--i), o.set(i,o.get(j)), o.set(j,x)) ;
		return o;
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    public void drawConstraints(PaintEvent c, DrawManager dm)
    {
        RectF rect;
        int i,j;
        int radius;
        float legWidth; 

        	c.gc.setForeground(new Color(c.display, 255, 0, 0));
        	
            for (i=3;i<constraints.size();++i) {
			    if (constraints.get(i) instanceof DistanceConstraint) {

                    DistanceConstraint constraint = (DistanceConstraint)constraints.get(i);

				    // draw legs
				    if (
					    (i >= 2 && i <= 4)
					    || (i >= (2*9)+1 && i <= (2*9)+2)
					    || (i >= (2*17)+1 && i <= (2*17)+2)
					    || (i >= (2*25)+1 && i <= (2*25)+2)
				    ) {

				    	legWidth = 10.0F;//5.0F;

				    } else if (
					    (i >= 4 && i <= 6)
					    || (i >= (2*9)+3 && i <= (2*9)+4)
					    || (i >= (2*17)+3 && i <= (2*17)+4)
					    || (i >= (2*25)+3 && i <= (2*25)+4)
				    ) {
				    	legWidth = 7.6F;//3.5F;
                    }
                    else if (
					    (i >= 6 && i <= 8)
					    || (i >= (2*9)+5 && i <= (2*9)+6)
					    || (i >= (2*17)+5 && i <= (2*17)+6)
					    || (i >= (2*25)+5 && i <= (2*25)+6)
				    ) {
                    	legWidth = 5.5F; //2.5F;
                    }
                    else
                    {
                    	legWidth = 3.5F;//2.0F;
                    }
                    
				    legWidth /= 2.5F;
				    
				    c.gc.drawLine((int)dm.getX(constraint.a.pos.x), (int)dm.getY(constraint.a.pos.y), (int)dm.getX(constraint.b.pos.x), (int)dm.getY(constraint.b.pos.y));
			    }
		    }

    }
    
    public void drawParticles(PaintEvent c, DrawManager dm)
    {
        
		RectF rect;
		int radius;
		float degrees;
		double shift;
        
		  radius = 6;
		  c.gc.drawOval((int)dm.getX(head.pos.x - radius), (int)dm.getY(head.pos.y - radius), 2*radius, 2*radius);
		
		  radius = 5;
		  c.gc.drawOval((int)dm.getX(thorax.pos.x - radius), (int)dm.getY(thorax.pos.y - radius), 2*radius, 2*radius);
		
		  radius = 11;
		  c.gc.drawOval((int)dm.getX(abdomen.pos.x - radius), (int)dm.getY(abdomen.pos.y - radius), 2*radius, 2*radius);
        	
    }    
}
