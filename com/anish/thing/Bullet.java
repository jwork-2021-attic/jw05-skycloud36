package com.anish.thing;

import java.awt.Color;

import com.anish.maze.World;

public class Bullet extends Creature {

    Bullet(char glyph, Thing owner, int dx, int dy) {
        super(owner.getColor(), glyph, owner.world, owner.getTeam());
        this.xPos = owner.getX();
        this.yPos = owner.getY();
        this.name = BULLET;
        this.owner = owner;
        this.ATK = owner.getATK();
        this.dx = dx;
        this.dy = dy;
    }

    private Thing owner;
    private int dx = 0;
    private int dy = 0;

    public void moveDirect(){
        this.moveWithHandle(dx, dy);
    } 

    @Override
    public void moveWithHandle(int xPos, int yPos){
        this.xPos += xPos;
        this.yPos += yPos;
        Thing temp = this.world.get(this.xPos, this.yPos);
        if(temp.name != FLOOR && this.ifExist()){
            if(temp.getName() == WALL){
                this.xPos -= xPos;
                this.yPos -= yPos;
                this.beDead();
            }
            else if(temp.getTeam() == this.getTeam()){
                // this.world.setBackground(this.xPos, this.yPos);
                this.xPos += xPos;
                this.yPos += yPos;
            }
            else if(temp.getTeam() == this.enemyTeam){
                this.Attack(temp);
                this.beDead();
            }
        }
    }

    @Override
    public void Attack(Thing victim){
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
    public void beAttacked(Thing attacker) {
        synchronized (this) {
            this.world.setBackground(this.getX(), this.getY());
            if (DebugBeAttacked) {
                System.out.println(this.getTeam() + " " + this.getName()
                        + " be attacked by "
                        + attacker.getTeam() + " " + attacker.getName()
                        + " " + attacker.getATK());
            }
            this.beDead();
        }
    }

}
