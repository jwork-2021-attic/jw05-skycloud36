package com.anish.thing;

import com.anish.maze.World;

import java.awt.Color;

public class Up extends Thing {
    public Up(World world) {
        super(Color.red, (char) 24, world);
    }
}