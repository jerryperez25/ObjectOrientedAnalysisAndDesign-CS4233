/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package pig;

/**
 * This class is the main class for the Pig Game programming assignment.
 * @version Oct 21, 2020
 */
public class PigGame
{

	//Pig Game Variables
	private PigGameVersion version;
	private int scoreNeeded;
	private Die dice[];
	private boolean gameOver = false; //game is not over
	
	//Player Variables
	private int player1 = 0; //player 1 stands for array index 0
	private int player2 = 1; //player 2 stands for array index 1
	private int playerScores[] = {0,0,0,0}; //pre-define empty arrays 
	private int whoseTurn = 0;//0 for player 1, 1 for player 2 etc
	
	//Roll Variables
	private int currentRoll = 0;
	private int currentRoll2 = 0;
	private int player1PreviousRoll = 0;
	private int player2PreviousRoll = 0;
	private int turnTotal = 0;
	private int rollCount = 0;
	
	/**
	 * Constructor that takes in the version and dice to be used for the game version.
	 * @param version the game version
	 * @param scoreNeeded the score that a player must achieve (equal to this or greater)
	 * @param dice an array of dice to be used for the game.
	 * @throws a PigGameException if any of the arguments are invalid (e.g. scoreNeeded is <= 0)
	 */
	public PigGame(PigGameVersion version, int scoreNeeded, Die ...dice) throws PigGameException
	{
		
		if(wrongConditions(version, scoreNeeded, dice))
		{
			throw new PigGameException("You have entered an invalid game.");
		}
		
		this.version = version;
		this.scoreNeeded = scoreNeeded;
		this.dice = dice;	
	}
	
	public PigGameVersion getVersion() //gets the game version
	{
		return this.version;
	}
	
	public int getTurnTotal() //gets the turn total
	{
		return this.turnTotal;
	}
	public int getPlayerTotal(int player)
	{
		//this is decremented by 1 to give me a piece of mind when testing, so that I 
		//dont have to think in zero based indexes
		return this.playerScores[player-1];
	}

	public int getRollCount() //gets the count of rolls
	{
		return this.rollCount;
	}
	/**
	 * This method rolls the dice for the play and returns the result.
	 * @return the amount rolled. If this returns 0 it means that the
	 * 	player who rolled the dice has pigged out and receives a 0 for the turn
	 *  and the opposing player will make the next roll. If the method returns 
	 *  the scoreNeeded, it means the player who rolled wins.
	 * @throws a PigGameException if the game is over when this method is called.
	 */
	public int roll()
	{
		
		Die firstDieRoll = this.dice[0]; //loads the first die into a global variable
		currentRoll = firstDieRoll.roll(); //rolls that die to obtain the value
		
		if(gameOver == true)
		{
			throw new PigGameException("The game is over. - roll");
		}
		
		if (this.getVersion()==PigGameVersion.STANDARD)
		{
			if(whoseTurn == 0) //if its player 1's turn
			{
				
				rollCount++; //the player has rolled now
				return standardGame(player1); //turn calculations and game over scenarios
			}
			else 
			{
				rollCount = 0; //reset the roll counter when players swap
				rollCount++; 
				return standardGame(player2); 
			}
		}
		
		if (this.getVersion()==PigGameVersion.TWO_DICE) //Two Dice Game
		{
			
			Die secondDieRoll = this.dice[1]; //loads the second dice into a second global variable
			currentRoll2 = secondDieRoll.roll(); //rolls that die to obtain the value
			
			//we do the same thing as above here to determine who's turn
			if(whoseTurn == 0)
			{
				rollCount++;
				return twoDice(player1); //turn calculations and game over scenarios
			}
			else 
			{
				rollCount = 0;
				rollCount++;
				return twoDice(player2);
			}
		}
		
		if (this.getVersion()==PigGameVersion.ONE_DIE_DUPLICATE) //not done yet
		{	
			
			if(whoseTurn == 0)
			{
				rollCount++;
				//we have 2 players previous rolls here to eliminate possibility of player 1 rolling
				//a number and the program checking player 2's roll to see if its the same 
				if(currentRoll == player1PreviousRoll)   
				{
					turnTotal = 0; //the player has pigged out and needs to have the turnTotal reset
					this.hold();
					return 0;
				}
				player1PreviousRoll = currentRoll;
				return duplicateGame(player1); //turn calculations and game over scenarios
			}
			
			else

			{
				rollCount = 0;
				rollCount++;
				if(currentRoll == player2PreviousRoll) 
				{
					turnTotal = 0; //the player has pigged out and needs to have the turnTotal reset
					this.hold();
					return 0;
				}
				player2PreviousRoll = currentRoll;
				return duplicateGame(player2);
			}
		}
		
		return 0;
	}
	
	/**
	 * This method adds the turn total to the current total for the active
	 * player and switches players. A player must have rolled at least once
	 * during the turn before holding.
	 * @throws PigGameException if the active player has not rolled at least
	 *   one time during the turn.
	 */
	public void hold()
	{

		if (rollCount == 0) //a player doesn't roll
		{
			gameOver = true;
			throw new PigGameException("There has not been a roll yet");
			
		}
		
		if (gameOver == true)
		{
			throw new PigGameException("The game is over. - hold");
		}
		
		else 
		{
			playerScores[whoseTurn] = playerScores[whoseTurn] + turnTotal;
			turnTotal = 0;
			
			if(whoseTurn == 1) //this "1" can be changed with the number of players
				//1 is for 2 players, 2 will be for 3 players and so on 
				//think in 0-based indexing
			{
				whoseTurn = 0; //set back to player 1
			}
			else
			{
				whoseTurn++; //we increment otherwise
			}
		}
	}	
/**
 * This method is used to check the terminal conditions of the duplicate dice
 * game. It also updates the turnTotal for the respective player, 
 * which is a global variable.
 * @param player
 * @return in that is going to be returned in the roll function
 */
	private int duplicateGame(int player)  
	{
		turnTotal = turnTotal + currentRoll; 
		
		if (turnTotal+ playerScores[player] >= this.scoreNeeded)
		{
			this.hold();
			gameOver = true;
			playerScores[player] = this.scoreNeeded; //sets the desired player score to the winning score
			return this.scoreNeeded; //game win
		}
		return currentRoll;
	}

/**
 * This method is used to check the terminal conditions of the two dice
 * game. In addition, it also updates the turnTotal for the respective player, 
 * which is a global variable.
 * @param player
 * @return int that is going to be returned in the roll function
 */
	private int twoDice(int player)  
	{
		if ((currentRoll + currentRoll2) == 7)
		{
			turnTotal = 0; //the player has pigged out and needs to have the turnTotal reset
			this.hold();
			return 0;
		}			
	
		turnTotal = turnTotal + currentRoll + currentRoll2; //totals the turn
		
		if (turnTotal+ playerScores[player] >= this.scoreNeeded)
		{
			this.hold();
			gameOver = true;
			playerScores[player] = this.scoreNeeded; //sets the desired player score to the winning score
			return this.scoreNeeded;
		}
		
		int diceSum = currentRoll + currentRoll2; //sums the die
		return diceSum;
		
	}
/**
 * This method is used to check the terminal conditions of the Standard Game.
 *  In addition, it updates the turnTotal, 
 * a global variable, for each player that is given. 
 * @param player
 * @return int that is going to be returned in the roll function
 */

	private int standardGame(int player) 
	{
		if(currentRoll == 1)
		{
			turnTotal = 0; //the player has pigged out and needs to have the turnTotal reset
			this.hold(); 
			return 0;
		}

		turnTotal = turnTotal + currentRoll;
		
		if ((turnTotal+ playerScores[player]) >= this.scoreNeeded)
		{
			this.hold();
			gameOver = true;
			playerScores[player] = this.scoreNeeded; //sets the desired player score to the winning score
			return this.scoreNeeded;
		}
		return currentRoll;
	}
/**
 * 	This method is intended to determine if any one of the terminal conditions are satisfied.
 * In this method, we check for instances where the game creation is something that is either
 * not feasible, or just plain out wrong.
 * @param version
 * @param scoreNeeded
 * @param dice
 * @return boolean (true or false) if either one of the terminal conditions are satisfied
 */
	private boolean wrongConditions(PigGameVersion version, int scoreNeeded, Die ...dice)
	{
		if(dice == null||scoreNeeded <= 0||(version == PigGameVersion.STANDARD && dice.length != 1)
				|| (version == PigGameVersion.TWO_DICE && dice.length < 2)||
				(version != PigGameVersion.STANDARD && version != PigGameVersion.TWO_DICE
				&& version != PigGameVersion.ONE_DIE_DUPLICATE))
		{
			return true;
		}
		return false;
	}
	
}
