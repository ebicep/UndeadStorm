package com.ebicep.undeadstorm.AStar;

import com.ebicep.undeadstorm.Entities.zombies.AbstractZombie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pathfinder {
    private Node parent; //
    private Node obstructionNode; //if node is wall
    private ArrayList<Node> nullSet; //hasnt been seen/visited
    private ArrayList<Node> open; //seen but not visited
    private ArrayList<Node> closed; //visited

    private String[][] gridData = new String[375][500];

    public Pathfinder() {
        try {
            File grid = new File("grid");
            Scanner myReader = new Scanner(grid);
            int counter = 0;
            System.out.println("READING MAP");

            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    gridData[counter][i] = line.charAt(i) + "";
                }
                counter++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void act(AbstractZombie zombie) {

    }

    public void updateParent(Node parent) {
        this.parent = parent;
    }
}
