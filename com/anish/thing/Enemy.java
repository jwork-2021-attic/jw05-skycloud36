package com.anish.thing;

import java.awt.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.anish.maze.World;

public class Enemy extends Creature{

    public Enemy(Color color, char glyph, World world, int xPos, int yPos) {
        super(color, glyph, world, xPos, yPos);
        this.name = "Enemy";
        moveByThread(this);
    }

    public void moveRandom(){
        Random random = new Random();
        moveByTest(1, 0);
        System.out.println(this.getName() + "x:" + this.getX() + "y:" + this.getY());
        // moveBy(1, 0);
        // int k = random.nextInt(4);
        // switch(k){
        //     case 0:moveBy(0, 1);    break;
        //     case 1:moveBy(0, -1);   break;
        //     case 2:moveBy(-1, 0);   break;
        //     case 3:moveBy(1, 0);    break;
        // }
    }

    @Override
    public void beAttacked(){
        this.world.setBackground(this.getX(), this.getY());
        this.beDead();
    }
    
    private void moveByThread(Enemy enemy){
        class MyThread extends Thread{
            Enemy enemy;

            MyThread(Enemy enemy){
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
                            enemy.world.getCreatures().remove(enemy);
                            System.out.println(enemy.getName() + "dead");
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
