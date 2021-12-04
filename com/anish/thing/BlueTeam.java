package com.anish.thing;

import java.awt.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.anish.maze.World;

public class BlueTeam extends Creature{

    public BlueTeam(Color color, char glyph, World world, int xPos, int yPos) {
        super(color, glyph, world, xPos, yPos);
        this.name = "BlueTeam";
        moveByThread(this);
    }

    public void moveRandom(){
        Random random = new Random();
        int k = random.nextInt(4);
        switch(k){
            case 0:moveByTest(0, 1);    break;
            case 1:moveByTest(0, -1);   break;
            case 2:moveByTest(-1, 0);   break;
            case 3:moveByTest(1, 0);    break;
        }
        System.out.println(this.getName() + "x:" + this.getX() + "y:" + this.getY());
    }

    @Override
    public void beAttacked(){
        this.world.setBackground(this.getX(), this.getY());
        this.beDead();
        this.world.getCreatures().remove(this);
        System.out.println(this.getName() + Thread.currentThread().getId() + "dead");
    }
    
    private void moveByThread(BlueTeam enemy){
        class MyThread extends Thread{
            BlueTeam enemy;

            MyThread(BlueTeam enemy){
                this.enemy = enemy;
            }

            @Override
            public void run(){
                Timer myTimer = new Timer();
                class myTask extends TimerTask{
                    @Override
                    public void run(){
                        if(enemy.ifExist()){
                            enemy.moveRandom();
                        }
                        else{
                            // enemy.world.getCreatures().remove(enemy);
                            // System.out.println(enemy.getName() + Thread.currentThread().getId() + "dead");
                            cancel();
                        }
                    }
                };
                myTask mytask = new myTask();
                myTimer.schedule(mytask, 0, 500);
            }
        };

        Thread t = new MyThread(enemy);
        t.start();
    }
    
}
