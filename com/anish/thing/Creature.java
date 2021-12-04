package com.anish.thing;

import com.anish.maze.World;
import java.awt.Color;


public class Creature extends Thing{
    private int xPos, yPos;

    // private World world;
    // public static World world;
    // private final char glyph;
    // private Color color;

    Creature(Color color, char glyph, World world, int xPos, int yPos) {
        super(color, glyph, world);
        // this.color = color;
        // this.glyph = glyph;
        // this.world = world;

        this.xPos = xPos;
        this.yPos = yPos;
        world.put(this, xPos, yPos);

        this.exist = true;
    }

    public Boolean moveTo(int xPos, int yPos) {
        if(this.world.putPlayingThing(this, xPos, yPos) == null){
            this.world.setBackground(this.getX(), this.getY());
            this.setxPos(xPos);
            this.setyPos(yPos);
            System.out.println("player:" + Thread.currentThread().getId() + "x:" + xPos + "y:" + yPos);
            return true;
        }
        System.out.println("Player:" + Thread.currentThread().getId() + "x:" + xPos + "y:" + yPos);
        return false;
    }

    public Boolean moveTest(int xPos, int yPos){
        if(this.world.putPlayingThing(this, xPos, yPos) == null){
            this.world.setBackground(this.getX(), this.getY());
            this.setxPos(xPos);
            this.setyPos(yPos);
            System.out.println("enemy:" + Thread.currentThread().getId() + "x:" + xPos + "y:" + yPos);
            return true;
        }
        System.out.println("enemy:" + Thread.currentThread().getId() + "x:" + xPos + "y:" + yPos);
        return false;
    }

    @Override
    public int getX() {
        return this.xPos;
    }

    @Override
    public int getY() {
        return this.yPos;
    }

    @Override
    public void setxPos(int xPos){
        this.xPos = xPos;
    }

    @Override
    public void setyPos(int yPos){
        this.yPos = yPos;
    }


    public synchronized void moveBy(int xPos, int yPos) { 
        int x = this.getX()+xPos;
        int y = this.getY()+yPos;
        // this.moveTo(x, y);
        Thing thing = this.world.get(x, y);
        if(thing.getName() == "Floor"){
            this.moveTo(x, y);
        }
        if(this.getName() == "Player" && thing.getName() == "Enemy"){
            thing.beAttacked();
        }
        // this.world.setBackground(this.getX() - xPos, this.getY() - yPos);
    }

    public synchronized void moveByTest(int xPos, int yPos) { 
        int x = this.getX()+xPos;
        int y = this.getY()+yPos;
        // this.moveTest(x, y);
        if(this.world.get(x, y).getName() == "Floor"){
            this.moveTo(x, y);
        }
    }

    public void moveUp(){
        // this.world.put(new Up(this.world), this.getX(), this.getY());
        this.moveBy(0, -1);
    }

    public void moveDown(){
        // this.world.put(new Down(this.world), this.getX(), this.getY());
        this.moveBy(0, 1);
    }

    public void moveRight(){
        // this.world.put(new Right(this.world), this.getX(), this.getY());
        this.moveBy(1, 0);
    }

    public void moveLeft(){
        // this.world.put(new Left(this.world), this.getX(), this.getY());
        this.moveBy(-1, 0);
    }

    public void moveUpTest(){
        // this.world.put(new Up(this.world), this.getX(), this.getY());
        this.moveByTest(0, -1);
    }

    public void moveDownTest(){
        // this.world.put(new Down(this.world), this.getX(), this.getY());
        this.moveByTest(0, 1);
    }

    public void moveRightTest(){
        // this.world.put(new Right(this.world), this.getX(), this.getY());
        this.moveByTest(1, 0);
    }

    public void moveLeftTest(){
        // this.world.put(new Left(this.world), this.getX(), this.getY());
        this.moveByTest(-1, 0);
    }

    
    // public Color getColor() {
    //     return this.color;
    // }

    // public void setColor(Color color){
    //     this.color = color;
    // }

    // public char getGlyph() {
    //     return this.glyph;
    // }

    // public World getWorld(){
    //     return this.world;
    //     // return Creature.world;
    // }

    private boolean exist;

    public boolean ifExist(){
        return this.exist;
    }

    public void beDead(){
        this.exist = false;
    }
}


