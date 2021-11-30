package com.anish.thing;

import com.anish.maze.World;
import java.awt.Color;


public class Creature{
    private int xPos, yPos;

    private World world;
    private final char glyph;
    private Color color;

    Creature(Color color, char glyph, World world, int xPos, int yPos) {
        this.color = color;
        this.glyph = glyph;
        this.world = world;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void moveTo(int xPos, int yPos) {
        // this.world.put(this, xPos, yPos);
        this.setxPos(xPos);
        this.setyPos(yPos);
    }

    public int getX() {
        return this.xPos;
    }

    public int getY() {
        return this.yPos;
    }

    public void setxPos(int xPos){
        this.xPos = xPos;
    }

    
    public void setyPos(int yPos){
        this.yPos = yPos;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public char getGlyph() {
        return this.glyph;
    }

    public World getWorld(){
        return this.world;
    }

    public Boolean moveBy(int xPos, int yPos) {
        int x = this.getX()+xPos;
        int y = this.getY()+yPos;
        if(!(this.getWorld().get(x, y) instanceof Wall)){
            this.moveTo(x, y);
        }
        return false;
    }

    public Boolean moveUp(){
        // this.world.put(new Up(this.world), this.getX(), this.getY());
        return this.moveBy(0, -1);
    }

    public Boolean moveDown(){
        // this.world.put(new Down(this.world), this.getX(), this.getY());
        return this.moveBy(0, 1);
    }

    public Boolean moveRight(){
        // this.world.put(new Right(this.world), this.getX(), this.getY());
        return this.moveBy(1, 0);
    }

    public Boolean moveLeft(){
        // this.world.put(new Left(this.world), this.getX(), this.getY());
        return this.moveBy(-1, 0);
    }

}


// package com.anish.thing;

// import com.anish.maze.World;
// import java.awt.Color;


// public class Creature extends Thing {

//     Creature(Color color, char glyph, World world) {
//         super(color, glyph, world);
//     }

//     public void moveTo(int xPos, int yPos) {
//         this.world.put(this, xPos, yPos);
//     }


// }
