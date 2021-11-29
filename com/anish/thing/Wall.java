package com.anish.thing;

import com.anish.maze.World;

import asciiPanel.AsciiPanel;

public class Wall extends Thing {

    public Wall(World world) {
        super(AsciiPanel.cyan, (char) 177, world);
    }

}
