package com.anish.thing;

import com.anish.maze.World;

import asciiPanel.AsciiPanel;

public class StartPoint extends Thing {
    public StartPoint(World world){
        super(AsciiPanel.brightRed, (char) 10, world);
    }
}
