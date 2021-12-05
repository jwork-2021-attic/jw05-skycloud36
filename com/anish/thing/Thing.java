package com.anish.thing;

import com.anish.maze.World;

import java.awt.Color;

public class Thing implements CreatureAttribute{
    protected World world;

    // public World getWorld(){
    //     return world;
    // }

    public Tile<? extends Thing> tile;

    public int getX() {
        return this.tile.getxPos();
    }

    public int getY() {
        return this.tile.getyPos();
    }

    public void setxPos(int xPos){
        this.tile.setxPos(xPos);
    }

    
    public void setyPos(int yPos){
        this.tile.setyPos(yPos);
    }

    public void setTile(Tile<? extends Thing> tile) {
        this.tile = tile;
    }

    Thing(Color color, char glyph, World world) {
        this.color = color;
        this.glyph = glyph;
        this.world = world;
    }

    private Color color;

    public Color getColor() {
        return this.color;
    }

    private char glyph;

    public char getGlyph() {
        return this.glyph;
    }

    protected String name;

    public String getName(){
        return this.name;
    }

    // protected String team;
    
    // public String getTeam(){
    //     return this.team;
    // }

    // protected String enemyTeam;

    // public String getEnemyTeam(){
    //     return this.enemyTeam;
    // }

    // public void beAttacked(){
        
    // }
}
