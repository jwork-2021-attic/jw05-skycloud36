package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.thing.Creature;
import com.anish.thing.Enemy;
import com.anish.thing.Player;
import com.anish.thing.Enemy;
import com.anish.maze.World;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    String[] sortSteps;
    Player player;

    public WorldScreen() {
        world = new World();
        player = new Player(Color.RED, (char)2, world, 1, 1);
        Enemy e1 = new Enemy(Color.BLUE, (char)2, world, 2, 1);
        Enemy e2 = new Enemy(Color.green, (char)2, world, 20, 1);
        Enemy e3 = new Enemy(Color.gray, (char)2, world, 20, 1);
        Enemy e4 = new Enemy(Color.yellow, (char)2, world, 20, 1);
        Enemy e5 = new Enemy(Color.pink, (char)2, world, 20, 1);
        world.addCreatures(e1);
        world.addCreatures(e2);
        world.addCreatures(e3);
        world.addCreatures(e4);
        world.addCreatures(e5);
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
        switch(key.getKeyCode()){
            case KeyEvent.VK_LEFT:
                player.moveLeft();
                // player.moveLeftTest();
                break;
            case KeyEvent.VK_RIGHT:
                player.moveRight();
                // player.moveRightTest();
                break;
            case KeyEvent.VK_UP:
                player.moveUp();
                // player.moveUpTest();
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown();
                // player.moveDownTest();
                break;
        }
        return this;
    }

    

}
