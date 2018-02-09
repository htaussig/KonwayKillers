package gameOfLife;

import java.awt.Color;

import acm.graphics.GRect;
import acm.util.RandomGenerator;

public class Cell extends GRect{

	private boolean isAlive;
	
	private boolean nextLife;
	
	private Color team = Color.RED;
	
	private Color deadColor = Color.WHITE;
	
	public Cell(int x1, int y1, int x2, int y2){
		
		super(x1, y1, x2, y2);
		
		isAlive = false;
		nextLife = false;
		
		setFilled(true);
		
		setColor(deadColor);
		
	}
	
	
	public void setAlive(boolean aliveness){
		
		isAlive = aliveness;
		
		if(isAlive == true){
			
			setColor(team);
			
		}
		else{
			
			setColor(deadColor);
			
		}
	}
	
	public boolean isAlive(){
		
		return isAlive;
		
	}
	
	public boolean getNextLife(){
		
		return nextLife;
		
	}
	
	public void setNextLife(int numRed, int numBlue){
		
		int numAdjAlives = numRed + numBlue;
		
		Color winner = getWinnerColor(numRed, numBlue);
		
		
		if(isAlive){
			
			
			if(numAdjAlives == 2 || numAdjAlives == 3){
				
				nextLife = true;   //this is unnecessary
				
				if(winner != null && !winner.equals(getTeam())){    //if a live cell is surrounded by 3 cells, and the majority are the opposing team, it will become that team
					
					setTeam(winner);
					
				}
				
			}
			else{
				nextLife = false;
			}
			
		}
		else{
			
			if(numAdjAlives == 3){
				
				nextLife = true;
				
				if(numRed > numBlue){
					
					setTeam(Color.RED);
					
				}
				else if(numBlue > numRed){
					
					setTeam(Color.BLUE);
					
				}
				else{
					
					if(rgen.nextBoolean()){
						
						setTeam(Color.RED);
						
					}
					else{
						
						setTeam(Color.BLUE);
						
					}
					
				}
			}
			
		}
		
	}
	
	
	public void setDeadColor(Color color){
		
		deadColor = color;
		setColor(deadColor);
		
	}
	
	
	private Color getWinnerColor(int numRed, int numBlue) {
		
		Color winner;
		
		if(numRed > numBlue){  //could make this another method
			
			winner = Color.RED;
			
		}
		else if(numBlue > numRed){
			
			winner = Color.BLUE;
			
		}
		else{
			
			winner = null;
			
		}
		
		return winner;
		
	}


	public void nextState(){
		
		setAlive(nextLife);
		
	}
	
	
	public void setTeam(Color theTeam){
		
		team = theTeam;
		
	}
	
	public Color getTeam(){
		
		return team;
		
	}
	
	public static RandomGenerator rgen = RandomGenerator.getInstance();
}
