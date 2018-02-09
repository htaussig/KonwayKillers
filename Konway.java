package gameOfLife;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Konway extends GraphicsProgram{

	private static final int sideLength = 14;
   private static final int gridSize = 40;  //should be even otherwise have to change addCells()
   
   private static final int delay = 200; //millisecond delay between each step
   
   private static final Font goFont = new Font("Helvetica", 0, 48);
   
   
   private int timeStep = 0;
   
   private int lifeCount = 0;
   private int redLifeCount = 0;
   private int blueLifeCount = 0;
   
   
   private GLabel life = new GLabel("Life: " + lifeCount, (gridSize + 2) * sideLength, sideLength * 4);
   private GLabel goButton = new GLabel("Click to Start", (gridSize + 2) * sideLength, sideLength * 16);
   private GLabel redLife = new GLabel("Red Life: " + redLifeCount, (gridSize + 2) * sideLength, sideLength * 6);
   private GLabel blueLife = new GLabel("Blue Life: " + blueLifeCount, (gridSize + 2) * sideLength, sideLength * 8);
   private GLabel time = new GLabel("Time: " + timeStep, (gridSize + 2) * sideLength, sideLength * 2);
   
   
   
   
   private Cell[][] cells = new Cell[gridSize][gridSize];
   
   private boolean go = false;
	
   
   private Object lastObject;
   
    public void run(){

    	addMouseListeners();

    	
        add(new LifeBoard(sideLength ,gridSize));
        
        addCells();
    	
        addLabels();
        
        
        while(true){
        	
        	
        	System.out.print("");  //interesante, it needs something to do
        	
        	while(go){
        		
        		
            	iterate();
            	

            	timeStep++;
        		
            	refreshLabels();
            	
            	
            	if(lifeCount == 0){
        			
        			timeSwitch();
        			timeStep = 0;
        			break;
        			
        		}
        		
        		if(timeStep % 50 == 0 && timeStep != 0){
        			
        			timeSwitch();
        			break;
        			
        		}
        		
            	
            	pause(delay);
            	
            }
            
        	
        }
        
    }

    

	private void timeSwitch() {
		
		if(go == true){
			
			go = false;
			
			goButton.setLabel("paused");
			
		}
		else{
			go = true;
			
			goButton.setLabel("living!");
		}
		
	}



	private void addLabels() {
		
		add(time);
        add(life);
        
        redLife.setColor(Color.RED);
        add(redLife);
        
        blueLife.setColor(Color.BLUE);
        add(blueLife);
        
        goButton.setFont(goFont);
        add(goButton);
        
		
	}



	private int getLifeTotal() {
		
		int sum = 0;
		
		int redSum = 0;
		int blueSum = 0;
		
		for(int i = 0; i < gridSize; i++){
    		
    		for(int j = 0; j < gridSize; j++){
    			
    			
    			if(cells[i][j].isAlive()){
    				
    				sum++;
    				
    				if(cells[i][j].getTeam() == Color.RED){
    					
    					redSum++;
    					
    				}
    				else{
    					
    					blueSum++;
    					
    				}
    				
    			}
    			
    		}
    		
    	}
		
		lifeCount = sum;
		redLifeCount = redSum;
		blueLifeCount = blueSum;
		
		return sum;
		
	}



	private void iterate() {
		
        	calcNextState();
        	
        	changeState();
        	
        
	}



	private void changeState() {
		
		for(int i = 0; i < gridSize; i++){
    		
    		for(int j = 0; j < gridSize; j++){
    			
    			cells[i][j].nextState();
    			
    		}
    		
    	}
		
	}



	private void calcNextState() {

		for(int i = 0; i < gridSize; i++){
    		
    		for(int j = 0; j < gridSize; j++){
    			
    			
    			
    			String numAdjAlive = getAdjAlive(i ,j);  //number of 8 surrounding squares that are alive (R B)
    			
    			int numRed = Character.getNumericValue(numAdjAlive.charAt(0));
    			int numBlue = Character.getNumericValue(numAdjAlive.charAt(2));
    			
    			cells[i][j].setNextLife(numRed, numBlue);
    			
    		}
    		
    	}
		
	}



	private String getAdjAlive(int i, int j) {
		
		int numBlue = 0;
		int numRed = 0;
		
		for(int x = i - 1; x < i + 2; x++){
			
			for(int y = j - 1; y < j + 2; y++){
				
				if(x >= 0 && x < gridSize && y >= 0 && y < gridSize){
					
					if(cells[x][y].isAlive()){
						
						if(!(x == i && y == j)){
							
							if(cells[x][y].getTeam().equals(Color.BLUE)){
								
								numBlue++;
								
							}
							else{
								numRed++;
							}
							
						}			
						
					}
					
				}
					
			}
			
		}
		
		return numRed + " " + numBlue;
		
	}



	private void addCells() {
		
		for(int i = 1; i <= gridSize; i++){
			
			for(int j = 1; j <= gridSize; j++){
				
				Cell temp = new Cell((sideLength * i) + 1, (j * sideLength) + 1, sideLength - 2, sideLength - 2);
				
	            cells[i - 1][j - 1] = temp;
	            
	            if(i - 1 >= (gridSize / 2) - 2 && i - 1 < (gridSize / 2) + 2){
	            	
	            	temp.setDeadColor(Color.LIGHT_GRAY);
	            	
	            }
	            
	            add(temp);  
	            
	
	        }
			
		}
		
	}

	
	public void refreshLabels(){
		
		life.setLabel("Life: " + getLifeTotal());
		redLife.setLabel("Red Life: " + redLifeCount);
		blueLife.setLabel("Blue Life: " + blueLifeCount);
		time.setLabel("Time: " + timeStep);
		
	}
	


	public void mousePressed(MouseEvent e) {
		
			int x = e.getX();
			int y = e.getY();
			
			Object temp = getElementAt(x,y);
			
			lastObject = temp;
			
			if(temp instanceof Cell){
				
				if(((Cell) temp).isAlive()){
					
					((Cell) temp).setAlive(false);
					
					lifeCount--;
					if(((Cell) temp).getTeam() == Color.RED){
						
						redLifeCount--;
						
					}
					else{
						
						blueLifeCount--;
						
					}
					
					
					refreshLabels();
					
					
				}
				else{
					
					if(x < sideLength * ((gridSize / 2) + 1)){
						
						((Cell) temp).setTeam(Color.BLUE);  //shift to turn blue
						
					}
					else{
						
						((Cell) temp).setTeam(Color.RED);
						
					}
					
					((Cell) temp).setAlive(true);
					
					lifeCount++;
					
					if(((Cell) temp).getTeam() == Color.RED){
						
						redLifeCount++;
						
					}
					else{
						
						blueLifeCount++;
						
					}
					
					refreshLabels();
					
					
				}
				
			}
			else if(temp == null){
				//might use this for something later
				
			}
			else if(temp instanceof GLabel && temp == goButton){
				
					timeSwitch();
				
			}
			
	}

	
	public void mouseDragged(MouseEvent e){
		
		int x = e.getX();
		int y = e.getY();
		
		Object temp = getElementAt(x,y);
		
		if(lastObject == null || temp != lastObject){
			
			mousePressed(e);
			lastObject = temp;
			
		}
		
		
	}

}
