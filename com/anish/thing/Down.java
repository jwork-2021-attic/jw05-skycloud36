package com.anish.thing;

import com.anish.maze.World;

import java.awt.Color;

public class Down extends Thing {
    public Down(World world) {
        super(Color.red, (char) 25, world);
    }
}