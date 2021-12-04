package com.anish.thing;

import com.anish.maze.World;

import java.awt.Color;

public class Player extends Creature{
    public Player(Color color, char glyph, World world, int xPos, int yPos) {
        super(color, glyph, world, xPos, yPos);
        this.name = "Player";
    }

    String answer;
    int answerloc;
    public Boolean auto;

}
