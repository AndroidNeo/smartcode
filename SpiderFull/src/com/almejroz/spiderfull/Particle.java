package com.almejroz.spiderfull;


public class Particle {

    public Vec2 pos;
    public Vec2 lastPos;
    
    public Particle(Vec2 pos) {
        this.pos     = new Vec2(pos);
        this.lastPos = new Vec2(pos);
    }
	
}
