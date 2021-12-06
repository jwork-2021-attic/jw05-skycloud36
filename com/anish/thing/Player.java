package com.anish.thing;

import com.anish.maze.World;

import java.awt.Color;

public class Player extends Creature{
    public Player(Color color, char glyph, World world, int xPos, int yPos) {
        super(color, glyph, world, xPos, yPos, REDTEAM);
        this.name = PLAYER;
        this.HP = 100;
        this.MaxHP = 100;
        this.Defence = 0;
        this.ATK = 10;
    }

    @Override
    public void Attack(Thing victim) {
        if(victim.ifExist()){
            if(DebugPlayerAttack){
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
            if(DebugPlayerBeAttacked){
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
    public void moveWithHandle(int xPos, int yPos) {
        Thing temp = moveBy(xPos, yPos);
        if(temp != null && temp.getTeam() == this.enemyTeam){
            this.Attack(temp);
        }
    }
 
}
