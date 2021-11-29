package com.anish.thing;

import com.anish.maze.World;

import java.awt.Color;

public class Left extends Thing {
    public Left(World world) {
        super(Color.red, (char) 27, world);
    }
}