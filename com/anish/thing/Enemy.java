package com.anish.thing;

import java.awt.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.anish.maze.World;

public class Enemy extends Creature{

    public Enemy(Color color, char glyph, World world, int xPos, int yPos) {
        super(color, glyph, world, xPos, yPos);
        moveByThread();
    }

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

    
    private void moveByThread(){
        class MyThread extends Thread{
            @Override
            public void run(){
                Timer myTimer = new Timer();
                class myTask extends TimerTask{
                    @Override
                    public void run(){moveRandom();}
                };
                myTask mytask = new myTask();
                myTimer.schedule(mytask, 0, 500);
            }
        };
        Thread t = new MyThread();
        t.start();
    }
    
}
