package com.zipcodeconway;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SimpleWindow {
    static JPanel panel;
    static JFrame frame;
    private Integer dim = 0;
    private int genCount;
    private Color genColor;

    public SimpleWindow(Integer dimension) {
        this.dim = dimension * 10;
        panel = new JPanel();
        Dimension dim = new Dimension(this.dim, this.dim);
        panel.setPreferredSize(dim);
        frame = new JFrame();
        Integer framesize = (this.dim < 100) ? 100 : this.dim;
        frame.setSize(framesize, framesize);
        Container contentPane = frame.getContentPane();
        contentPane.add(panel);
        frame.setVisible(true);

        genCount = 0;
    }

    public void sleep(Integer millisecs) {
        try {
            Thread.sleep(millisecs);
            Graphics g = panel.getGraphics();
            g.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void display(int[][] array, Integer n) {
        frame.setTitle(String.format("Generation: %6d", n));
        Graphics g = panel.getGraphics();
        int BOX_DIM = 10;

        if(n%1 == 0){
            genColor = generateColor();
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                g.drawRect(i * BOX_DIM, j * BOX_DIM, 10, 10);
                if (array[i][j] == 0) {
                    g.setColor(Color.white);
                    g.fillRect(i * BOX_DIM, j * BOX_DIM, 10, 10);
                }
                if (array[i][j] == 1) {
                    g.setColor(genColor);
                    g.fillRect(i * BOX_DIM, j * BOX_DIM, 10, 10);
                }
            }
        }
    }

    public Color generateColor(){
        Color returnColor = new Color(0);
        Random rand = new Random();
        int randColor = rand.nextInt(3);
        switch (randColor) {
            case 0:
                returnColor = Color.cyan;
                break;
            case 1:
                returnColor = Color.MAGENTA;
                break;
            case 2:
                returnColor = Color.GREEN;
                break;
        }
        return returnColor;
    }

}
