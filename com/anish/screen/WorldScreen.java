package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.anish.thing.Bullet;
import com.anish.thing.Creature;
import com.anish.thing.CreatureAttribute;
import com.anish.thing.First;
import com.anish.thing.Player;
import com.anish.thing.Second;
import com.anish.thing.Thing;

// import org.w3c.dom.events.MouseEvent;

import com.anish.thing.First;
import com.anish.maze.World;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    volatile public static boolean gameStart = false;
    volatile public static boolean gamePause = false;
    private World world;
    String[] sortSteps;
    // Player player;

    Creature player = null;
    Creature choose = null;
    int index = 0;

    public WorldScreen() {
        world = new World();
        // Second b1 = new Second(world, 30, 27,CreatureAttribute.BLUETEAM);   world.addBlue(b1);
        // Second b2 = new Second(world, 15, 6,CreatureAttribute.BLUETEAM);    world.addBlue(b2);
        // Second b3 = new Second(world, 6, 19,CreatureAttribute.BLUETEAM);    world.addBlue(b3);
        Second b4 = new Second(world, 3, 20,CreatureAttribute.BLUETEAM);   world.addBlue(b4);
        // First b5 = new First(Color.BLUE, (char)2, world, 7, 18,CreatureAttribute.BLUETEAM);   world.addBlue(b5);
        First b5 = new First(world, 3, 5, CreatureAttribute.BLUETEAM);     world.addBlue(b5);
        First r1 = new First(world, 3, 4,CreatureAttribute.REDTEAM);        world.addRed(r1);
        // First r2 = new First(world, 10, 7,CreatureAttribute.REDTEAM);        world.addRed(r2);
        // First r3 = new First(world, 5, 15,CreatureAttribute.REDTEAM);        world.addRed(r3);
        // Second r4 = new Second(world, 30, 1,CreatureAttribute.REDTEAM);      world.addRed(r4);
        // Second r5 = new Second(world, 60, 18,CreatureAttribute.REDTEAM);     world.addRed(r5);
        this.gameStart();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());
            }
        }
        for (Creature t : world.getBlue()) {
            t.disPlayout(terminal);
        }
        for (Creature t : world.getRed()) {
            t.disPlayout(terminal);
        }
        String stats;
        if (player != null) {
            stats = String.format("Team:%s", player.getTeam());
            terminal.write(stats, 1, world.HEIGHT);
            stats = String.format("Creatures:%s", player.getName());
            terminal.write(stats, 1, world.HEIGHT + 1);
            stats = String.format("%3d/%3d hp", player.getHP(), player.getMaxHP());
            terminal.write(stats, 1, world.HEIGHT + 2);
        } else {
            stats = String.format("Player is null");
            terminal.write(stats, 1, world.HEIGHT);
        }
        if (this.gameStart == false) {
            stats = String.format("Game Not Start");
            terminal.write(stats, 1, world.HEIGHT / 2);
        }
        if (this.gamePause == true) {
            stats = String.format("Game Pause");
            terminal.write(stats, 1, world.HEIGHT / 2);
        }
    }

    private void displayPlayer(AsciiPanel terminal) {
        if (player.ifExist())
            player.disPlayout(terminal);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (this.gameStart == false) {
            switch (key.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    this.gameStart();
                    break;
            }
        } 
        else {
            if (player != null) {
                if (player.ifExist() == false) {
                    UnselectPlayer();
                    return this;
                }
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        player.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.moveRight();
                        break;
                    case KeyEvent.VK_UP:
                        player.moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        player.moveDown();
                        break;
                    case KeyEvent.VK_SPACE:
                        player.Attack();
                        break;
                    case KeyEvent.VK_Q:
                        this.UnselectPlayer();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        if (this.gamePause == false) {
                            gamePause();
                        } else {
                            gameUnPause();
                        }
                        break;
                }
            } else {
                switch (key.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        index = (index - 1 + this.world.getRed().size()) % this.world.getRed().size();
                        this.ChoosePlayer(this.world.getRed().get(index % this.world.getRed().size()));
                        break;
                    case KeyEvent.VK_RIGHT:
                        index = (index + 1) % this.world.getRed().size();
                        this.ChoosePlayer(this.world.getRed().get(index % this.world.getRed().size()));
                        break;
                    case KeyEvent.VK_ENTER:
                        if (choose != null && choose.ifExist()) {
                            player = choose;
                            player.Select();
                        }
                        break;
                    case KeyEvent.VK_ESCAPE:
                        if (this.gamePause == false) {
                            gamePause();
                        } else {
                            gameUnPause();
                        }
                        break;
                }
            }
        }
        return this;
    }

    public Screen Finish() {
        if (this.world.getBlue().size() == 0) {
            return new WinScreen();
        } else if (this.world.getRed().size() == 0) {
            return new LoseScreen();
        }
        return null;
    }

    public void gameStart() {
        this.gameStart = true;
        for (Creature t : world.getBlue()) {
            synchronized (t) {
                t.notify();
            }
        }
        for (Creature t : world.getRed()) {
            synchronized (t) {
                t.notify();
            }
        }        
        if (choose == null || !choose.ifExist()) {
            if (this.world.getRed().size() > 0) {
                this.ChoosePlayer(this.world.getRed().get(0));
            }
        }
    }

    public void gameUnPause() {
        this.gamePause = false;
        for (Creature t : world.getBlue()) {
            synchronized (t) {
                t.notify();
            }
        }
        for (Creature t : world.getRed()) {
            synchronized (t) {
                t.notify();
            }
        }
    }

    public void gamePause() {
        this.gamePause = true;
    }

    void ChoosePlayer(Creature c) {
        if (this.choose != null) {
            choose.UnChoose();
        }
        choose = c;
        choose.Choose();
    }

    void SelectPlayer() {
        player = choose;
        player.Select();
    }

    void UnselectPlayer() {
        if (this.player != null) {
            synchronized (player) {
                player.UnSelect();
                player.notify();
            }
            if (player.ifExist()) {
                this.ChoosePlayer(player);
            } else {
                if (this.world.getRed().size() > 0) {
                    this.ChoosePlayer(this.world.getRed().get(0));
                }
            }
        }
        player = null;
    }

    @Override
    public Screen respondToUserMouse(MouseEvent mouseEvent) {
        if(true){
            int x = cursorxToWorldx(mouseEvent.getX());
            int y = cursoryToWorldy(mouseEvent.getY());
            System.out.println("x:" + x + " y:" + y);
            Thing temp = this.world.get(x, y);
            if(temp.getName() == CreatureAttribute.FLOOR){     
                First t = new First(world, x, y, CreatureAttribute.REDTEAM);
                world.addRed(t);
            }
        }
        return this;
    }

    int cursorxToWorldx(int x) {
        return x / 16;
    }

    int cursoryToWorldy(int y) {
        return (y - 30) / 16;
    }

    void SelectThing(Thing t) {
        // if (this.thing != null) {
        //     this.UnSelectThing();
        // }
        // this.thing = t;
        // this.thing.setColor(Color.PINK);
    }

    void UnSelectThing() {
        // this.world.setBackground(thing.getX(), thing.getY());
        // thing = null;
    }
}
