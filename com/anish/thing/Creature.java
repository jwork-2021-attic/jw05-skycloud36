package com.anish.thing;

import com.anish.maze.World;
import java.awt.Color;


public class Creature extends Thing {

    Creature(Color color, char glyph, World world) {
        super(color, glyph, world);
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(this, xPos, yPos);
    }

}
