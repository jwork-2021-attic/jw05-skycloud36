package com.anish.thing;

import com.anish.maze.World;

import java.awt.Color;

public class Player extends Creature{
    public Player(Color color, char glyph, World world, int xPos, int yPos) {
        super(color, glyph, world, xPos, yPos, "RedTeam");
        this.name = "Player";
    }

    @Override
    public void Attack(Thing victim) {
        if(DebugPlayerAttack){
            System.out.println(this.getTeam() + " " + this.getName()
            + " Attack " 
            + victim.getTeam() + " " + victim.getName());
        }
        victim.beAttacked(this);
    }

    @Override
    public void beAttacked(Thing attacker){
        this.world.setBackground(this.getX(), this.getY());
        if(DebugPlayerBeAttacked){
            System.out.println(this.getTeam() + " " + this.getName()
            + " be attacked by " 
            + attacker.getTeam() + " " + attacker.getName());
        }
        this.beDead();
    }
 
}
