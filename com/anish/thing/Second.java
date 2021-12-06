package com.anish.thing;

import java.awt.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.anish.maze.World;
import com.anish.screen.WorldScreen;

import asciiPanel.AsciiPanel;

public class Second extends Creature {

    public Second(Color color, char glyph, World world, int xPos, int yPos, String team) {
        super(color, glyph, world, xPos, yPos, team);
        this.name = "Second";
        target = null;
        this.speed = 700;
        this.HP = 10;
        this.MaxHP = 10;
        this.Defence = 0;
        this.ATK = 1;
        curTime = System.currentTimeMillis();
        this.bullets = new ArrayList<>(100);
        bulletByThread(this, 50);
        moveByThread(this);
    }

    public Second(World world, int xPos, int yPos, String team) {
        super((char) 202, world, xPos, yPos, team);
        this.name = SECOND;
        target = null;
        this.speed = 700;
        this.HP = 100;
        this.MaxHP = 100;
        this.Defence = 0;
        this.ATK = 10;
        curTime = System.currentTimeMillis();
        this.bullets = new ArrayList<>(100);
        bulletByThread(this, 50);
        moveByThread(this);
    }

    private Thing target;
    private List<Bullet> bullets;
    private long curTime;

    public void moveRandom() {
        Random random = new Random();
        int k = random.nextInt(4);
        switch (k) {
            case UP:
                this.moveUp();
                break;
            case DOWN:
                this.moveDown();
                break;
            case RIGHT:
                this.moveRight();
                break;
            case LEFT:
                this.moveLeft();
                break;
        }
    }

    public void moveByAI() {
        if (target == null || target.ifExist() == false) {
            int min = 1000;
            for (Creature t : this.enemyList) {
                int len = Math.min(Math.abs(t.getX() - this.getX()), Math.abs(t.getY() - this.getY()));
                if (len < min) {
                    target = t;
                }
            }
            if (target == null || target.ifExist() == false) {
                target = null;
                moveRandom();
            }
        } else {
            int dx = target.getX() - this.getX();
            int dy = target.getY() - this.getY();
            if (dx == 0) {
                if (dy < 0) {
                    this.toward = UP;
                    this.setGlyph((char) 24);
                    this.Attack();
                } else {
                    this.toward = DOWN;
                    this.setGlyph((char) 25);
                    this.Attack();
                }
            } else if (dy == 0) {
                if (dx < 0) {
                    this.toward = LEFT;
                    this.setGlyph((char) 27);
                    this.Attack();
                } else {
                    this.toward = RIGHT;
                    this.setGlyph((char) 26);
                    this.Attack();
                }
            } else if (Math.abs(dx) >= 1 && Math.abs(dy) >= 1) {
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dy < 0) {
                        this.moveUp();
                    } else {
                        this.moveDown();
                    }
                } else {
                    if (dx < 0) {
                        this.moveLeft();
                    } else {
                        this.moveRight();
                    }
                }
            }
        }
    }

    @Override
    public void moveWithHandle(int xPos, int yPos) {
        moveBy(xPos, yPos);
        this.changeGlyph();
    }

    void changeGlyph() {
        switch (this.toward) {
            case UP:
                this.setGlyph((char) 24);
                break;
            case DOWN:
                this.setGlyph((char) 25);
                break;
            case LEFT:
                this.setGlyph((char) 27);
                break;
            case RIGHT:
                this.setGlyph((char) 26);
                break;
        }
    }

    @Override
    public void Attack(Thing victim) {
        if (victim.ifExist()) {
            if (DebugEnemyAttack) {
                System.out.println(this.getTeam() + " " + this.getName()
                        + " Attack "
                        + victim.getTeam() + " " + victim.getName()
                        + " " + this.getATK());
            }
            victim.beAttacked(this);
        }
    }

    @Override
    public void beAttacked(Thing attacker) {
        synchronized (this) {
            if (DebugBeAttacked) {
                System.out.println(this.getTeam() + " " + this.getName()
                        + " be attacked by "
                        + attacker.getTeam() + " " + attacker.getName()
                        + " " + attacker.getATK());
            }
            this.HP -= attacker.getATK() - this.getDefence();
            if (this.HP <= 0) {
                this.HP = 0;
                this.beDead();
            }
        }
    }

    @Override
    public void Attack() {
        if (timeToAttack()) {
            Bullet bullet = null;
            switch (this.toward) {
                case UP:
                    bullet = new Bullet(this, 0, -1);
                    break;
                case DOWN:
                    bullet = new Bullet(this, 0, 1);
                    break;
                case RIGHT:
                    bullet = new Bullet(this, 1, 0);
                    break;
                case LEFT:
                    bullet = new Bullet(this, -1, 0);
                    break;
            }
            synchronized (this.bullets) {
                if (bullet != null) {
                    bullets.add(bullet);
                }
            }
        }
    }

    boolean timeToAttack() {
        if (System.currentTimeMillis() - curTime >= 200) {
            curTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void bulletByThread(Second second, int bulletSpeed) {
        Timer myTimer = new Timer();
        class myTask extends TimerTask {
            Second second;

            myTask(Second second) {
                this.second = second;
            }

            @Override
            public void run() {
                synchronized (this.second.bullets) {
                    Iterator<Bullet> it = second.bullets.iterator();
                    Bullet bullet;
                    while (it.hasNext()) {
                        bullet = it.next();
                        if (bullet.ifExist()) {
                            bullet.moveDirect();
                        } else {
                            it.remove();
                        }
                        if (WorldScreen.gameStart == false || WorldScreen.gamePause == true) {
                            synchronized (this.second) {
                                try {
                                    this.second.wait();
                                } catch (InterruptedException i) {
                                }
                            }
                        }
                    }
                    if (second.ifExist() == false) {
                        if (second.bullets.size() > 0) {
                            second.bullets.clear();
                        }
                        cancel();
                    }
                }
            }
        }
        ;
        myTask mytask = new myTask(second);
        myTimer.schedule(mytask, 0, bulletSpeed);
    }

    public void moveByThread(Second second) {
        Timer myTimer = new Timer();
        class myTask extends TimerTask {
            Second second;

            myTask(Second second) {
                this.second = second;
            }

            @Override
            public void run() {
                if (WorldScreen.gameStart == false || WorldScreen.gamePause == true || this.second.selected == true) {
                    synchronized (this.second) {
                        try {
                            this.second.wait();
                            this.second.notify();
                        } catch (Exception r) {
                        }
                    }
                } else {
                    if (this.second.ifExist()) {
                        this.second.moveByAI();
                    } else {
                        cancel();
                    }
                }
            }
        }
        ;
        myTask mytask = new myTask(second);
        myTimer.schedule(mytask, 0, second.speed);
    }

    @Override
    public void disPlayout(AsciiPanel terminal) {
        for (Bullet bullet : this.bullets) {
            if (bullet.ifExist()) {
                terminal.write(bullet.getGlyph(), bullet.getX(), bullet.getY(), this.getColor());
            }
        }
        if (this.ifExist())
            terminal.write(this.getGlyph(), this.getX(), this.getY(), this.getColor());
    }
}
