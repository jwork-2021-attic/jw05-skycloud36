package com.anish.thing;

import com.anish.maze.World;

import java.awt.Color;

public class Floor extends Thing {

    public Floor(World world) {
        super(Color.gray, (char) 250, world);
    }

}
