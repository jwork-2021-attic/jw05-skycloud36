package com.anish.maze;

public class Node {
    public final int x;
    public final int y;
    public final int rank;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.rank = 0;
    }

    Node(int x, int y, int r){
        this.x = x;
        this.y = y;
        this.rank = r;
    }

    public Boolean isBigger(Node node){
        return this.rank > node.rank;
    }
}