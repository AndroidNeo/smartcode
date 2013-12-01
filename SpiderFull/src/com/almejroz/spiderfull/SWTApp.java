package com.almejroz.spiderfull;
import java.io.IOException;
import java.util.TimerTask;

import javax.annotation.processing.Messager;
import javax.print.attribute.standard.Finishings;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class SWTApp {

    private Shell shell;
    private int screenWidth;
    private int screenHeight;

	SpiderEngine spiderEngine;
	
	static int exitPeriod = 20;
	
	/*
    boolean needRedraw;
	
    java.util.Timer timer = new java.util.Timer();
    TimerTask task = new TimerTask() {
      public void run()
      {
    	  needRedraw = true;
      }
    };
    */
    
    public SWTApp(Display display) {

    	spiderEngine = new SpiderEngine();
    	
    	screenWidth = 1000;
    	screenHeight = 700;
    	
        shell = new Shell(display);

        shell.addPaintListener(new ColorsPaintListener());
        
        shell.setText("spiderfull");
        shell.setSize(screenWidth, screenHeight);
        shell.setLocation(500, 100);
        shell.open();
        
        
        /*
        timer.scheduleAtFixedRate(task, 0, 1000/30);        

        while (!shell.isDisposed()) {
            
        	if (!display.readAndDispatch()) {
                display.sleep();
            }
        	
            if(needRedraw){
            	needRedraw = false;
            	shell.redraw();
            }
            	
        }
        */
        
        long startTime = System.currentTimeMillis();
        
        
        while (!shell.isDisposed()) {
        	shell.redraw();
        	shell.update();
        	
        	if(System.currentTimeMillis() - startTime > exitPeriod*1000)
        		System.exit(0);
        	
        	//t = display.getThread();
        	try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//System.out.println("done");
        }
        
    }

    private class ColorsPaintListener implements PaintListener {
        public void paintControl(PaintEvent e) {
        	
        	spiderEngine.drawFrame(e, shell);
            e.gc.dispose();
            
            
        }
    }

    
    

    public static void main(String[] args) {
        Display display = new Display();
        new SWTApp(display);
        display.dispose();
    }
}