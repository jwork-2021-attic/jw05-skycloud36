package com.anish.maze;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.Arrays;

public class MazeGenerator {
    
    private Stack<Node> stack = new Stack<>();
    private Random rand = new Random();
    private int[][] maze;
    private int row, col;
    private Node maxNode;

    public MazeGenerator(int row, int col) {
        maze = new int[col][row];
        this.row = col;
        this.col = row;
        maxNode = new Node(1,0);
    }

    public void generateMaze() {
        stack.push(new Node(1,1));
        while (!stack.empty()) {
            Node next = stack.pop();
            if (validNextNode(next)) {
                maze[next.x][next.y] = 1;
                ArrayList<Node> neighbors = findNeighbors(next);
                randomlyAddNodesToStack(neighbors);
                if(next.isBigger(maxNode) && pointNextToBound(next)){
                    maxNode = next;
                }
            }
        }
        maze[1][0] = 2;
        setFinalPoint(maxNode);
    }

    private void setFinalPoint(Node node){
        for(int i = -1; i < 2; i = i + 2){
            if(pointOnBound(node.x + i, node.y) && pointOnMaze(node.x + i, node.y)){
                maze[node.x + i][node.y] = 3;
                return;
            }
        }
        for(int i = -1; i < 2; i = i + 2){
            if(pointOnBound(node.x, node.y + i) && pointOnBound(node.x, node.y + i)){
                maze[node.x][node.y + i] = 3;
                return;
            }
        }
    }
    public int PassByLoc(int x, int y){
        return maze[y][x];
    }

    public String getRawMaze() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : maze) {
            sb.append(Arrays.toString(row) + "\n");
        }
        return sb.toString();
    }

    public String getSymbolicMaze() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                sb.append(maze[i][j] == 1 ? "*" : " ");
                sb.append("  "); 
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean validNextNode(Node node) {
        int numNeighboringOnes = 0;
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotNode(node, x, y) && maze[x][y] == 1) {
                    numNeighboringOnes++;
                }
            }
        }
        return (numNeighboringOnes < 3) && maze[node.x][node.y] != 1;
    }

    private void randomlyAddNodesToStack(ArrayList<Node> nodes) {
        int targetIndex;
        while (!nodes.isEmpty()) {
            targetIndex = rand.nextInt(nodes.size());
            stack.push(nodes.remove(targetIndex));
        }
    }

    private ArrayList<Node> findNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotCorner(node, x, y)
                    && pointNotNode(node, x, y)) {
                    neighbors.add(new Node(x, y, node.rank + 1));
                }
            }
        }
        return neighbors;
    }

    private Boolean pointOnGrid(int x, int y) {
        return x >= 1 && y >= 1 && x < row - 1 && y < col - 1;
    }

    private Boolean pointNotCorner(Node node, int x, int y) {
        return (x == node.x || y == node.y);
    }
    
    private Boolean pointNotNode(Node node, int x, int y) {
        return !(x == node.x && y == node.y);
    }

    private Boolean pointNextToBound(Node node){
        return node.x == 1 || node.x == row - 2 || node.y == 1 || node.y == col - 2;
    }

    private Boolean pointOnBound(int x, int y){
        return x == 0 || x == this.row - 1 || y == 0 || y == this.col - 1;
    }

    private Boolean pointOnMaze(int x, int y){
        return x >= 0 && y >= 0 && x <= row - 1 && y <= col - 1;
    }

}