package com.anish.maze;

public class MazeDecrypt {
    private int[][] maze;
    int row, col;
    int elocx, elocy;

    MazeDecrypt(int row, int col){
        this.row = row;
        this.col = col;
        maze = new int[row][col];
    }

    public void put(int kind, int x, int y){
        maze[x][y] = kind;
        if(kind == 3){
            elocx = x;
            elocy = y;
        }
    }

    // private Boolean inMaze(int x, int y){
    //     return x >= 0 && y >= 0 && x < row && y < col;
    // }
}
