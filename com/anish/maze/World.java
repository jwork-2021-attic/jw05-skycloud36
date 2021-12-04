package com.anish.maze;

import javax.swing.text.StyledEditorKit.BoldAction;

import java.util.ArrayList;
import java.util.List;

import com.anish.thing.*;

public class World {

    public static final int WIDTH = 40;
    public static final int HEIGHT = 20;

    private Tile<Thing>[][] tiles;
    private Thing[][] background;

    private MazeGenerator maze;
    private List<Creature> creatures;

    public World() {

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        if (background == null){
            background = new Thing[WIDTH][HEIGHT];
        }

        maze = new MazeGenerator(WIDTH, HEIGHT);
        maze.generateMaze();

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                // int t = maze.PassByLoc(i, j);
                Thing t = getThingByrank(maze.PassByLoc(i, j));
                background[i][j] = t;
                tiles[i][j].setThing(t);
            }
        }

        creatures = new ArrayList<>();
    }

    public Thing get(int x, int y) {
        if (pointInWorld(x, y)){
            return this.tiles[x][y].getThing();
        }
        return null;
    }

    public void setBackground(int x, int y){
        tiles[x][y].setThing(background[x][y]);
    }

    public Thing getBackground(int x, int y) {
        if (pointInWorld(x, y)){
            return this.background[x][y];
        }
        return null;
    }


    public void put(Thing t, int x, int y) {
        if (pointInWorld(x, y)){
            // this.tiles[x][y].setThing(t);
            this.tiles[x][y].setThing(t);
        }
    }

    public Thing putPlayingThing(Thing t, int x, int y) {
        if (pointInWorld(x, y)){
            // this.tiles[x][y].setThing(t);
            return this.tiles[x][y].setPlayingThing(t);
        }
        else return null;
    }


    private Thing getThingByrank(int x){
        switch(x){
            case 0:return new Wall(this);
            case 1:return new Floor(this);
            default: return new Wall(this);
        }
    }

    private Boolean pointInWorld(int x, int y){
        return x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT;
    }

    public void addCreatures(Creature creature){
        this.creatures.add(creature);
    }

    public List<Creature> getCreatures(){
        return creatures;
    }
}
