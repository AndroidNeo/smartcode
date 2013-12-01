package com.almejroz.spiderfull;



import java.util.Random;

import javax.annotation.Resources;
import javax.naming.Context;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;


public class SpiderEngine {

	public static final String SHARED_PREFS_NAME="spiderwp_settings";
	
    public static final String TAG = "ART";

	private int mFPS;
	private int mBackgroundId;
	private boolean mTouchEnabled; 
	private boolean mSoundEnabled;
	
    int step;
    int legIndex;
    VerletEngine sim;
    Composite mSpiderweb;
    Composite mSpider;
    Random rand;
    
    private float mTouchX = -1;
    private float mTouchY = -1;
    private float mOffset;
    
    
//    java.util.Timer timer = new java.util.Timer();
//    TimerTask task = new TimerTask() {
//      public void run()
//      {
//    	  drawFrame();
//      }
//    };
    
    
    private boolean mVisible;

	final int MAX_STREAMS = 5;
  
    SpiderEngine() {
        
    	mFPS                       = 30;        	
    	mTouchEnabled              = true;
    	mSoundEnabled              = true;
    	
    	
		int sceneLength = 600; 
        
        step = 16;
        sim = new VerletEngine(sceneLength, sceneLength);
        
        rand = new Random(System.currentTimeMillis());
        legIndex = 0;
        
        mSpiderweb = sim.spiderweb(new Vec2(sceneLength / 2, sceneLength / 2), sceneLength / 2, 20, 7);
        mSpider    = sim.spider(new Vec2(sceneLength / 2, 0));
        
        /*
        Point p = shell.getSize();
        int width = p.x;
        int height = p.y;
        
        sim.setDrawManager(width, height);
        */
        
        //drawFrame();
        //timer.schedule(task, 1000/mFPS);
        
        
        
    }

    void drawFrame(PaintEvent e, Shell shell) {
        
    	
    	drawScene(e, shell);
    	
    	
    }
    
    void drawScene(PaintEvent e, Shell shell) {
        
        if ((int)Math.floor(rand.nextDouble() * 4) == 0)
        {
            sim.crawl(((legIndex++) * 3) % 8);
            if (legIndex > 1000) 
                legIndex = 0;
        }
		
        sim.frame(step);
        sim.draw(e, shell);        	
    	
    }

    
}
