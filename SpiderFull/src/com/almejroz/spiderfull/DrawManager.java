package com.almejroz.spiderfull;



public class DrawManager {
    
	Vec2 positionCenter;
    double resolutionRatio;
    
    int width;
    int height;
    
    int screenWidth;
    int screenHeight;
    
	public DrawManager(int screenWidth, int screenHeight, int width, int height) {
		
		super();

        int minScreenLength = Math.min(screenWidth, screenHeight);
        int maxLength = Math.max(width, height);
        
        resolutionRatio = (double) minScreenLength/maxLength;
        positionCenter = new Vec2( screenWidth/2,screenHeight/2);
		
		this.width  = width;
		this.height = height;
		
		this.screenWidth  = screenWidth;
		this.screenHeight = screenHeight;
		
	}
    
	double getX(double x){
		return ((x-width/2)*resolutionRatio+positionCenter.x);
	}
    
	double getY(double y){
		return ((y-height/2)*resolutionRatio+positionCenter.y);
	}

	double getSimX(double xScreen){
		return (xScreen-positionCenter.x)/resolutionRatio + width/2;
	}
    
	double getSimY(double yScreen){
		return (yScreen-positionCenter.y)/resolutionRatio + height/2;
	}
	
	float getWidthLine(float widthLine){
		return widthLine*(float)resolutionRatio;
	}
	
	float getWidthLine(float widthLine, boolean isMinWidthLine){
		float result = widthLine*(float)resolutionRatio;
		if(isMinWidthLine && result<widthLine){
			result = widthLine;
		}
		return result;
	}
	
	public boolean isLandOrientation(){
		return (screenWidth >= screenHeight);
	}
	
	public boolean isDefineSize(){
		return (screenWidth != 0 && screenHeight !=0);
	}
	
	
}

