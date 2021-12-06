package com.anish.thing;

import java.awt.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.anish.maze.World;
import com.anish.screen.WorldScreen;

import asciiPanel.AsciiPanel;

public class First extends Creature{

    public First(Color color, char glyph, World world, int xPos, int yPos, String team) {
        super(color, glyph, world, xPos, yPos, team);
        this.name = FIRST;
        target = null;
        this.speed = 500;        
        this.HP = 100;
        this.MaxHP = 100;
        this.Defence = 0;
        this.ATK = 10;
        moveByThread(this);
    }

    private Thing target;

    public void moveRandom(){
        Random random = new Random();
        int k = random.nextInt(4);
        switch(k){
            case 0:moveBy(0, 1);    break;
            case 1:moveBy(0, -1);   break;
            case 2:moveBy(-1, 0);   break;
            case 3:moveBy(1, 0);    break;
        }
    }

    public void moveByAI(){
        if(target == null || target.ifExist() == false){
            int min = 1000;
            for(Creature t : this.enemyList){
                int len = Math.abs(t.getX() - this.getX()) + Math.abs(t.getY() - this.getY());
                if(len < min){
                    target = t;
                }
            }
            if(target == null || target.ifExist() == false){
                target = null;
                moveRandom();
            }
        }
        else{
            int dx = target.getX() - this.getX();
            int dy = target.getY() - this.getY();
            if(dx == 0){
                // this.moveWithHandle(0, dy/Math.abs(dy));
                if(dy < 0){
                    this.moveUp();
                }
                else{
                    this.moveDown();
                }
            }
            else if(dy == 0){
                // this.moveWithHandle(dx/Math.abs(dx), 0);
                if(dx < 0){
                    this.moveLeft();
                }
                else{
                    this.moveRight();
                }
            }
            else if(Math.abs(dx) >= 1 && Math.abs(dy) >= 1){
                Random random = new Random();
                int k = random.nextInt(2);
                if(k == 0){
                    // this.moveWithHandle(dx/Math.abs(dx),0);
                    if(dx < 0){
                        this.moveLeft();
                    }
                    else{
                        this.moveRight();
                    }
                }
                else{
                    // this.moveWithHandle(0, dy/Math.abs(dy));
                    if(dy < 0){
                        this.moveUp();
                    }
                    else{
                        this.moveDown();
                    }
                }
            }
        }
    }

    @Override
    public void moveWithHandle(int xPos, int yPos) {
        Thing temp = moveBy(xPos, yPos);
        if( temp != null && (this.ifSelected() == true ||(temp == target && target.ifExist()))){
            this.Attack(temp);
        }
    }
    
    @Override
    public void Attack(Thing victim) {
        if(victim.ifExist()){
            if(DebugFirstAttack){
                System.out.println(this.getTeam() + " " + this.getName()
                + " Attack " 
                + victim.getTeam() + " " + victim.getName()
                + " " + this.getATK());
            }
            victim.beAttacked(this);
        }
    }

    @Override
    public void beAttacked(Thing attacker){
        synchronized(this){
            if(DebugFirstBeAttacked){
                System.out.println(this.getTeam() + " " + this.getName()
                + " be attacked by " 
                + attacker.getTeam() + " " + attacker.getName()
                + " " + attacker.getATK());
            }
            this.HP -= attacker.getATK() - this.getDefence();
            if(this.HP <= 0) {
                this.HP = 0;
                this.beDead();
            }
        }
    } 

    public void moveByThread(First first){
        Timer myTimer = new Timer();
        class myTask extends TimerTask{
            First first;
            myTask(First first){
                this.first = first;
            }
            @Override
            public void run(){
                if(WorldScreen.gameStart == false || first.selected == true){
                    synchronized(first){
                        try{
                            first.wait();
                        }
                        catch(Exception r){
                        }   
                    }
                }
                else{
                    if(first.ifExist()){
                        first.moveByAI();
                    }
                    else{
                        cancel();
                    }
                }
            }
        };
        myTask mytask = new myTask(first);
        myTimer.schedule(mytask, 0, first.speed);
    }

    @Override
    public void disPlayout(AsciiPanel terminal){
        if(this.ifExist())
            terminal.write(this.getGlyph(), this.getX(), this.getY(), this.getColor());
    }
}
