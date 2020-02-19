package com.zipcodeconway;

import java.util.Random;

public class ConwayGameOfLife {

    int[][] current;
    int[][] next;
    int curGen = 0;
    SimpleWindow displayWindow;

    public ConwayGameOfLife(Integer dimension) {
        this.displayWindow = new SimpleWindow(dimension);
        this.current = createRandomStart(dimension);
        this.next = new int[dimension][dimension];
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        this.displayWindow = new SimpleWindow(dimension);
        this.current = startmatrix;
        this.next = new int[dimension][dimension];
    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        int[][] endingWorld = sim.simulate(5000);
    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        Random rand = new Random();
        int[][] startBoard = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                startBoard[i][j] = rand.nextInt(2);
            }
        }
        return startBoard;
    }

    public int[][] simulate(Integer maxGenerations) {
        for (; curGen <= maxGenerations; curGen++) {
            this.displayWindow.display(current, curGen);
            for (int j = 0; j < current.length; j++) {
                for (int k = 0; k < current.length; k++) {
                    int neighbors = isAlive(j, k, current);
                    if(current[j][k] == 1 && neighbors < 2 || neighbors > 3){
                        next[j][k] = 0;
                    }else if(current[j][k] == 0 && neighbors == 3){
                        next[j][k] = 1;
                    }else {
                        next[j][k] = current[j][k];
                    }
                }
            }
            copyAndZeroOut(next, current);
            this.displayWindow.sleep(15);
        }
        this.displayWindow.display(current, curGen);
        curGen = 0;
        return current;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {
        int length = next.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                current[i][j] = next[i][j];
                next[i][j] = 0;
            }
        }
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private int isAlive(int row, int col, int[][] world) {
        int aliveCounter = 0;
        int length = world.length;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                    aliveCounter += world[(row + length + i) % length][(col + length + j) % length];
            }
        }
        aliveCounter -= world[row][col];
        return aliveCounter;
    }
}
