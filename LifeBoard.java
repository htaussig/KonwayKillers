package gameOfLife;


import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

import acm.graphics.GCompound;
import acm.graphics.GLine;

import java.awt.event.ActionEvent;

import java.awt.event.MouseListener;
import java.awt.geom.Line2D;




public class LifeBoard extends GCompound{

    private int sideLength;   //number of pixels of the side of one block
    private int gridSize;   //number of blocks in each row and column

    
   
    public LifeBoard(int theSideLength, int theGridSize){
    	
    	sideLength = theSideLength;
    	gridSize = theGridSize;
    	
    	
    	for(int j = 0; j <= gridSize; j++){

            add(new GLine(sideLength, (j + 1) * sideLength, (sideLength) * (gridSize + 1), (j + 1) * sideLength ));  //horizontal lines

            add(new GLine((j + 1) * sideLength, sideLength, (j + 1) * sideLength, sideLength * (gridSize + 1)));  //vertical lines


        }

    	
    }
    
    

    /*public void actionPerformed(ActionEvent e)
    {
        System.out.println("cheese");

        int x = (int) getMousePosition().getX();

        int y = (int) getMousePosition().getY();

        System.out.println(x + " " + y);


        for(int i = 1; i <= gridSize; i++){

            for(int j = 1; j <= gridSize; j++){

                if(x > gridSize * i && x < gridSize * (i + 1) && y > gridSize * j && y < gridSize * (j + 1)){

                    x = i - 1;
                    y = j - 1;

                }

            }

        }

        System.out.println(x + " " + y);

        clicks[x][y].setBackground(Color.BLACK);
        clicks[x][y].setOpaque(true);
   }*/


   
}
