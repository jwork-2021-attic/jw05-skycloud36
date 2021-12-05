package com.anish.thing;

import java.awt.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.anish.maze.World;

public class First extends Creature{

    public First(Color color, char glyph, World world, int xPos, int yPos, String team) {
        super(color, glyph, world, xPos, yPos, team);
        this.name = "First";
        target = null;
        this.speed = 2;
        moveByThread(this);
    }

    private Thing target;

    public void moveRandom(){
        Random random = new Random();
        int k = random.nextInt(4);
        switch(k){
            case 0:moveByTest(0, 1);    break;
            case 1:moveByTest(0, -1);   break;
            case 2:moveByTest(-1, 0);   break;
            case 3:moveByTest(1, 0);    break;
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
            if(target.ifExist() == false){
                target = null;
                moveRandom();
            }
        }
        else{
            int dx = target.getX() - this.getX();
            int dy = target.getY() - this.getY();
            if(dx == 0){
                if(Math.abs(dy) == 1){
                    this.Attack(target);
                }
                else{
                    this.moveBy(0, dy/Math.abs(dy));
                }
            }
            else if(dy == 0){
                if(Math.abs(dx) == 1){
                    this.Attack(target);
                }
                else{
                    this.moveBy(dx/Math.abs(dx), 0);
                }
            }
            else if(Math.abs(dx) >= 1 && Math.abs(dy) >= 1){
                Random random = new Random();
                int k = random.nextInt(2);
                if(k == 0){
                    this.moveBy(dx/Math.abs(dx),0);
                }
                else{
                    this.moveBy(0, dy/Math.abs(dy));
                }
            }
        }
    }

    @Override
    public void Attack(Thing victim) {
        if(DebugEnemyAttack){
            System.out.println(this.getTeam() + " " + this.getName()
            + " Attack " 
            + victim.getTeam() + " " + victim.getName());
        }
        victim.beAttacked(this);
    }

    @Override
    public void beAttacked(Thing attacker){
        this.world.setBackground(this.getX(), this.getY());
        if(DebugEnemyBeAttacked){
            System.out.println(this.getTeam() + " " + this.getName()
            + " be attacked by " 
            + attacker.getTeam() + " " + attacker.getName());
        }
        this.beDead();
    }
    
    private void moveByThread(First first){
        class MyThread extends Thread{
            First first;

            MyThread(First first){
                this.first = first;
            }

            @Override
            public void run(){
                Timer myTimer = new Timer();
                class myTask extends TimerTask{
                    @Override
                    public void run(){
                        if(first.ifExist()){
                            // first.moveRandom();
                            first.moveByAI();
                        }
                        else{
                            cancel();
                        }
                    }
                };
                myTask mytask = new myTask();
                myTimer.schedule(mytask, 0, 1000/first.speed);
            }
        };

        Thread t = new MyThread(first);
        t.start();
    }
    
}
