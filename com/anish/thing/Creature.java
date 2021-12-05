package com.anish.thing;

import com.anish.maze.World;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class Creature extends Thing implements Debug{
    private int xPos, yPos;
    
    Creature(Color color, char glyph, World world, int xPos, int yPos, String team) {
        super(color, glyph, world);
        this.team = team;
        if(team == "BlueTeam"){
            this.enemyTeam = "RedTeam";
            this.enemyList = world.getRed();
        }
        else if(team == "RedTeam"){
            this.enemyTeam = "BlueTeam";
            this.enemyList = world.getBlue();
        }
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
            if(DebugPlayerMove)
                System.out.println(this.getTeam() + " " + this.getName() + " " + Thread.currentThread().getId() + "x:" + xPos + "y:" + yPos);
            return true;
        }
        if(DebugPlayerMove)
            System.out.println(this.getTeam() + " " + this.getName() + " " + Thread.currentThread().getId() + "x:" + xPos + "y:" + yPos);
        return false;
    }

    public Boolean moveTest(int xPos, int yPos){
        if(this.world.putPlayingThing(this, xPos, yPos) == null){
            this.world.setBackground(this.getX(), this.getY());
            this.setxPos(xPos);
            this.setyPos(yPos);
            if(DebugEnemyMove)
                System.out.println(this.getTeam() + " " + this.getName() + " " + Thread.currentThread().getId() + "x:" + xPos + "y:" + yPos);
            return true;
        }
        if(DebugEnemyMove)
            System.out.println(this.getTeam() + " " + this.getName() + " " + Thread.currentThread().getId() + "x:" + xPos + "y:" + yPos);
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
        Thing thing = this.world.get(x, y);
        if(thing.getName() == "Floor"){
            this.moveTo(x, y);
        }
        else{
            if(this.getName() == "Player" && thing.getTeam() == this.enemyTeam){
                this.Attack(thing);
            }
        }
    }

    public synchronized void moveByTest(int xPos, int yPos) { 
        int x = this.getX()+xPos;
        int y = this.getY()+yPos;
        if(this.world.get(x, y).getName() == "Floor"){
            this.moveTo(x, y);
        }
    }

    public void moveUp(){
        this.moveBy(0, -1);
    }

    public void moveDown(){
        this.moveBy(0, 1);
    }

    public void moveRight(){
        this.moveBy(1, 0);
    }

    public void moveLeft(){
        this.moveBy(-1, 0);
    }

    public void moveUpTest(){
        this.moveByTest(0, -1);
    }

    public void moveDownTest(){
        this.moveByTest(0, 1);
    }

    public void moveRightTest(){
        this.moveByTest(1, 0);
    }

    public void moveLeftTest(){
        this.moveByTest(-1, 0);
    }

    private boolean exist;

    @Override
    public boolean ifExist(){
        return this.exist;
    }

    @Override
    public void beDead(){
        if(DebugEnemyDie){
            System.out.println(this.getTeam() + " " + this.getName() + Thread.currentThread().getId() + " die");
        }
        this.exist = false;
        if(this.team == "RedTeam"){
            this.world.getRed().remove(this);
        }
        else{
            this.world.getBlue().remove(this);
        }

    }

    
    protected String team;

    @Override
    public String getTeam(){
        return this.team;
    }

    protected String enemyTeam;

    @Override
    public String getEnemyTeam(){
        return this.enemyTeam;
    }

    protected int speed;

    protected List<Creature> enemyList;
}


