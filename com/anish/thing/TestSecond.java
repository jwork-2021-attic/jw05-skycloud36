package com.anish.thing;

import java.awt.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.anish.maze.World;

import asciiPanel.AsciiPanel;

public class TestSecond extends Creature{

    public TestSecond(Color color, char glyph, World world, int xPos, int yPos, String team) {
        super(color, glyph, world, xPos, yPos, team);
        this.name = TESTSECOND;
        target = null;
        this.speed = 500;  
        this.HP = 10;
        this.MaxHP = 10;
        this.Defence = 0;
        this.ATK = 10;
        this.bullets = new ArrayList<>(100);
        bulletByThread(this.bullets, 50);
    }

    private Thing target;
    private List<Bullet> bullets;

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
        moveRandom();
    }

    @Override
    public void Attack(Thing victim) {
        if(victim.ifExist()){
                if(DebugEnemyAttack){
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
            this.world.setBackground(this.getX(), this.getY());
            if(DebugBeAttacked){
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

    @Override
    public void Attack() {
        Bullet bullet = null;
        switch (this.toward) {
            case UP:
                bullet = new Bullet((char) 7, this, 0, -1);
                break;
            case DOWN:
                bullet = new Bullet((char) 7, this, 0, 1);
                break;
            case RIGHT:
                bullet = new Bullet((char) 7, this, 1, 0);
                break;
            case LEFT:
                bullet = new Bullet((char) 7, this, -1, 0);
                break;
        }
        if(bullet != null){
            synchronized(this.bullets){
                this.bullets.add(bullet);
            }
        }
    }

    public void bulletByThread(List<Bullet> bullets, int bulletSpeed){
        Timer myTimer = new Timer();
        class myTask extends TimerTask{
            List<Bullet> bullets;
            myTask(List<Bullet> bullets){
                this.bullets = bullets;
            }
            @Override
            public void run(){
                synchronized(this.bullets){
                    Iterator<Bullet> it = bullets.iterator();
                    Bullet bullet;
                    while (it.hasNext()) {
                        bullet = it.next();
                        if (bullet.ifExist()) {
                            bullet.moveDirect();
                        } else {
                            it.remove();
                        }
                    }
                }
            }
        };
        myTask mytask = new myTask(bullets);
        myTimer.schedule(mytask, 0, bulletSpeed);
    }

    @Override
    public void disPlayout(AsciiPanel terminal){
        for(Bullet bullet:this.bullets){
            if(bullet.ifExist()){
                terminal.write(bullet.getGlyph(), bullet.getX(), bullet.getY(), this.getColor());
            }
        }
        if(this.ifExist())
            terminal.write(this.getGlyph(), this.getX(), this.getY(), this.getColor());
    }
}
