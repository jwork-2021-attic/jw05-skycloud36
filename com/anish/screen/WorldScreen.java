package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import com.anish.thing.Creature;
import com.anish.thing.First;
import com.anish.thing.Player;
import com.anish.thing.First;
import com.anish.maze.World;

import asciiPanel.AsciiPanel;


public class WorldScreen implements Screen {

    private World world;
    String[] sortSteps;
    Player player;

    public WorldScreen() {
        world = new World();
        player = new Player(Color.RED, (char)2, world, 1, 1);
        world.addRed(player);
        First e1 = new First(Color.BLUE, (char)2, world, 20, 10, "BlueTeam");
        First e2 = new First(Color.green, (char)2, world, 20, 11, "BlueTeam");
        First e3 = new First(Color.gray, (char)2, world, 20, 12, "BlueTeam");
        First e4 = new First(Color.yellow, (char)2, world, 20, 13, "BlueTeam");
        First e5 = new First(Color.pink, (char)2, world, 20, 14, "BlueTeam");
        world.addBlue(e1);
        world.addBlue(e2);
        world.addBlue(e3);
        world.addBlue(e4);
        world.addBlue(e5);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
            }
        }
        for(Creature t : world.getBlue()){
            terminal.write(t.getGlyph(), t.getX(), t.getY(), t.getColor());
        }
        for(Creature t : world.getRed()){
            terminal.write(t.getGlyph(), t.getX(), t.getY(), t.getColor());
        }
        // displayPlayer(terminal);
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

    public Screen Finish(){
        if(this.world.getBlue().size() == 0){
            return new WinScreen();
        }
        else if(this.world.getRed().size() == 0){
            return new LoseScreen();
        }
        return null;
    }

    

}
