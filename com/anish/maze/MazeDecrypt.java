package com.anish.maze;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;

public class MazeDecrypt {
    private int[][] maze;
    private int[][] path;
    int row, col;
    int elocx, elocy;

    MazeDecrypt(int row, int col){
        this.row = row;
        this.col = col;
        maze = new int[row][col];
        path = new int[row][col];
    }

    public void put(int kind, int x, int y){
        maze[x][y] = kind;
        if(kind == 3){
            elocx = x;
            elocy = y;
        }
    }

    public void findPath(){
        Stack<Node> stack = new Stack<>();
        stack.push(new Node(elocx, elocy, 1));
        while(!stack.empty()){
            Node next = stack.pop();
            path[next.x][next.y] = next.rank;
            ArrayList<Node> neighbors = findNeighbors(next);
            for(Node node : neighbors){
                stack.push(node);
            }                  
        }
    }

    public String getPath(int x, int y){
        String result = "";
        int rank = path[x][y];
        while(rank != 1){
            if(inMaze( x - 1, y) && path[x - 1][y] == rank -1){
                result += 'A';
                x--;
            }
            else if(inMaze( x + 1, y) && path[x + 1][y] == rank -1){
                result += 'D';
                x++;
            }
            else if(inMaze( x, y - 1) && path[x][y - 1] == rank -1){
                result += 'W';
                y--;
            }
            else if(inMaze( x, y + 1) && path[x][y + 1] == rank -1){
                result += 'S';
                y++;
            }
            else{
                result += 'G';
            }
            rank = path[x][y];
        }
        return result;
    }

    private ArrayList<Node> findNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        for(int i = -1; i < 2; i = i + 2){
            int x = node.x + i;
            int y = node.y;
            if(inMaze(x, y) && maze[x][y] != 0){
                if(path[x][y] == 0 || path[x][y] > path[node.x][node.y]){
                    neighbors.add(new Node(x, y, node.rank + 1));
                }
            }
            
            x = node.x;
            y = node.y + i;
            if(inMaze(x, y) && maze[x][y] != 0){
                if(path[x][y] == 0 || path[x][y] > path[node.x][node.y]){
                    neighbors.add(new Node(x, y, node.rank + 1));
                }
            }
        }
        return neighbors;
    }

    private Boolean inMaze(int x, int y){
        return x >= 0 && y >= 0 && x < row && y < col;
    }
}
