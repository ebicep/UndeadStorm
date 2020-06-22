package com.ebicep.undeadstorm.AStar;

public class Node {
    private int x;
    private int y;
    private int gCost;
    private int hCost;
    private int fCost;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
