package com.anish.thing;

import asciiPanel.AsciiPanel;

import com.anish.maze.World;

public class EndPoint extends Thing{
    public EndPoint(World world){
        super(AsciiPanel.brightGreen, (char) 10, world);
    }
}
