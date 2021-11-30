package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.thing.Creature;
import com.anish.thing.Player;
import com.anish.maze.World;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    String[] sortSteps;
    Player player;

    public WorldScreen() {
        world = new World();
        player = new Player(Color.gray, (char)2, world, 1, 1);
        world.addCreatures(player);
    }


    @Override
    public void displayOutput(AsciiPanel terminal) {
        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
            }
        }
        for(Creature t : world.getCreatures()){
            terminal.write(t.getGlyph(), t.getX(), t.getY(), t.getColor());
        }
        displayPlayer(terminal);
    }

    private void displayPlayer(AsciiPanel terminal){
        terminal.write(player.getGlyph(), player.getX(), player.getY(), player.getColor());
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        Boolean t = false;
    switch(key.getKeyCode()){
        case KeyEvent.VK_LEFT:
            t = player.moveLeft();
            player.auto = false;
            break;
        case KeyEvent.VK_RIGHT:
            t = player.moveRight();
            player.auto = false;
            break;
        case KeyEvent.VK_UP:
            t = player.moveUp();
            player.auto = false;
            break;
        case KeyEvent.VK_DOWN:
            t = player.moveDown();
            player.auto = false;
            break;
        default:t = player.moveAuto();
    }
        if(t)
            return new WinScreen();
        return this;
    }

}
