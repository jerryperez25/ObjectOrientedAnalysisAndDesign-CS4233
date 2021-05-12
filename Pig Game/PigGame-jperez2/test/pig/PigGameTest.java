/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package pig;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static pig.PigGameVersion.*;

/**
 * TestCases for the PigGame
 * 
 * @version Oct 21, 2020
 */
class PigGameTest
{
	
	Die oneRollDie = new ReturnOneDie();
	Die sixRollDie = new ReturnDie();
	Die twoDie[] = new Die[2];
	PigGame standardGame = new PigGame(STANDARD, 20, sixRollDie);
	PigGame standardBadGame = new PigGame(STANDARD, 10, oneRollDie);
	PigGame twoDieGame = new PigGame(TWO_DICE, 10, twoDie);
	PigGame duplicateGame = new PigGame(ONE_DIE_DUPLICATE, 10, oneRollDie);
	
	
	// #1
	@Test
	void noDice()
	{ // See https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions
		assertThrows(PigGameException.class, () -> new PigGame(STANDARD, 10, null));
		assertThrows(PigGameException.class, () -> new PigGame(TWO_DICE, 10, null));
		assertThrows(PigGameException.class, () -> new PigGame(ONE_DIE_DUPLICATE, 10, null));
		
	}
	
	// #2
	@Test
	void manyDiceforOneDiceGame() //checks to see if there is more than one die that is given
	{ 
		assertThrows(PigGameException.class, () -> new PigGame(STANDARD, 10, twoDie));
	}
	
	// #3
	@Test
	void wrongDiceForTwoDiceGame()
	{
		
		assertThrows(PigGameException.class, () -> new PigGame(TWO_DICE, 10, oneRollDie));
	}
	
	// #4
	@Test
	void wrongVersionChecker()
	{
		assertThrows(PigGameException.class, () -> new PigGame(null, 10, oneRollDie));
	}
	
	// #5 
	@Test
	void targetCheck()
	{
		assertThrows(PigGameException.class, () -> new PigGame(STANDARD, -1, oneRollDie));
	}
	
	// #6
	@Test
	void standardRollEquals1LossCheckandStandardRollScoreChecker()
	{
		assertEquals(0, standardBadGame.roll());
		assertEquals(6, standardGame.roll());
	}
	
	// #7
	@Test
	void duplicateRollCheckForDuplicateGame()
	{
		//Player 1 Check
		duplicateGame.roll();
		assertEquals(0, duplicateGame.roll());
		
		//Player 2 Check
		PigGame twoPlayerFail = new PigGame(ONE_DIE_DUPLICATE, 10, oneRollDie);
		twoPlayerFail.roll(); //player 1 has 1
		twoPlayerFail.hold();
		twoPlayerFail.roll(); //player 2 has 1
		assertEquals(0,twoPlayerFail.roll()); //player 2 rolls previous and current
	}
	
	// #8
	@Test
	void twoDiceEqual7TotalRollCheckForTwoDice()
	{
		twoDie[0] = new ReturnDie();
		twoDie[1] = new ReturnOneDie();
		assertEquals(0, twoDieGame.roll());
	}
	
	// #9
	@Test
	void standardDiceRegularTotallingAndPlayerTotalling() 
	{
		//regular implies the return that is given when roll is called
		PigGame standardNoWin = new PigGame(STANDARD, 15, sixRollDie);
		assertEquals(6, standardNoWin.roll()); //player 1 is at 6
		standardNoWin.roll();
		standardNoWin.hold();
		assertEquals(12, standardNoWin.getPlayerTotal(1)); //player 1 should be at 12
	}
	
	//#10
	@Test
	void twoDiceRegularTotallingAndPlayerTotaling()
	{
		//regular implies the return that is given when roll is called
		PigGame twoDieNoWin = new PigGame(TWO_DICE, 13, twoDie);
		twoDie[0] = new ReturnDie(); //returns 6 
		twoDie[1] = new ReturnDie(); //returns 6 
		assertEquals(12, twoDieNoWin.roll());
		twoDieNoWin.hold();
		assertEquals(12, twoDieNoWin.getPlayerTotal(1));
	}
	
	//#11
	@Test
	void duplicateDiceRegularTotallingAndPlayerTotalling()
	{
		//regular implies the return that is given when roll is called
		assertEquals(1, duplicateGame.roll());
		duplicateGame.hold();
		assertEquals(1, duplicateGame.getPlayerTotal(1));
	}
	
	//#12
	@Test
	void standardGameHoldFunctionality()
	{
		PigGame newStandardGame = new PigGame(STANDARD, 13, sixRollDie);
		newStandardGame.roll();//return a 6
		newStandardGame.roll();//return a 6
		newStandardGame.hold(); //player 1 has 12
		assertEquals(12, newStandardGame.getPlayerTotal(1));
		newStandardGame.roll(); //return a 6
		newStandardGame.hold(); //player 2 should have 6
		assertEquals(6, newStandardGame.getPlayerTotal(2));
	}
	
	//#13
	@Test
	void twoDiceGameHoldFunctionality()
	{
		twoDie[0] = new ReturnOneDie(); //returns a 1
		twoDie[1] = new ReturnOneDie(); //returns a 1
		twoDieGame.roll(); //returns 2
		twoDieGame.roll();//return 4
		twoDieGame.hold(); //should return 4 for player 1
		assertEquals(4, twoDieGame.getPlayerTotal(1)); 
		twoDieGame.roll(); //player 2 should have 2
		twoDieGame.hold(); //hold to total
		assertEquals(2, twoDieGame.getPlayerTotal(2));
	}
	//#14
	@Test
	void duplicateDiceGameHoldFunctionality()
	{
		duplicateGame.roll(); //returns 1
		duplicateGame.hold(); //should return 1 for player 1
		assertEquals(1, duplicateGame.getPlayerTotal(1));
		duplicateGame.roll(); 
		duplicateGame.hold(); 
		assertEquals(1, duplicateGame.getPlayerTotal(2));
	}
	
	//#15
	@Test
	void loseStandardGameAfterSucessfulRoll()
	{
		
		PigGame testGame = new PigGame(STANDARD, 10, new MultiDie());
		testGame.roll(); //2
		testGame.roll(); //3 
		assertEquals(0, testGame.roll()); //rolls a 1
		assertEquals(0, testGame.getPlayerTotal(1));
		
	}
	
	//#16
	@Test
	void loseTwoDieGameAfterSucessfulRoll()
	{
		twoDie[0] = new MultiDie(); //2, 3, 1
		twoDie[1] = sixRollDie;
		PigGame testGame = new PigGame(TWO_DICE, 20, twoDie);
		
		testGame.roll(); //bring the round count to 8
		testGame.roll(); //adds 9
		assertEquals(0, testGame.roll()); //pigged out
		testGame.hold(); //attempts to hold
		assertEquals(0, testGame.getPlayerTotal(1));
			
	}
	
	//#17
	@Test
	void loseWithDuplicateDieAfterSucessfulRoll()
	{
		Die regDie = new DuplicateDieTester(); //2,5,5
		PigGame testGame = new PigGame(ONE_DIE_DUPLICATE, 10, regDie);
		testGame.roll(); //returns a 2
		testGame.roll(); //returns a 5
		assertEquals(0,testGame.roll()); //returns 0
		testGame.hold(); //attempts to hold
		assertEquals(0, testGame.getPlayerTotal(1));
				
	}
	
	//#18
	@Test
	void swappingPlayersUsingHoldForStandardGame()
	{
		
		standardGame.roll(); //player 1 has 6
		standardGame.roll(); //player 1 has 12 now
		standardGame.hold(); //player 1 holds, it should be player 2's turn now
		assertEquals(12, standardGame.getPlayerTotal(1));
		standardGame.roll(); //player 2 has 6
		standardGame.hold(); //player 2 must hold at this point
		assertEquals(6, standardGame.getPlayerTotal(2));
	}
	
	//#19
	@Test
	void swappingPlayersUsingHoldForTwoDieGame()
	{
		twoDie[0] = new ReturnOneDie(); 
		twoDie[1] = new ReturnOneDie();
		
		twoDieGame.roll(); //player 1 has 2
		twoDieGame.roll(); //player 1 has 4 now
		twoDieGame.hold(); //player 1 holds, it should be player 2's turn now
		assertEquals(4, twoDieGame.getPlayerTotal(1));
		twoDieGame.roll(); //player 2 has 2
		twoDieGame.hold(); //player 2 must hold at this point
		assertEquals(2, twoDieGame.getPlayerTotal(2));
	}
	
	//#20
	@Test
	void swappingPlayersUsingHoldForDuplicateDiceGame()
	{
		duplicateGame.roll(); //player 1 rolls a 1
		duplicateGame.hold(); //player 2's turn now
		duplicateGame.roll();
		duplicateGame.hold();
		assertEquals(1, duplicateGame.getPlayerTotal(2));
	}
	//#21
	@Test
	void standardGameWinScenario()
	{
		PigGame easyWin = new PigGame(STANDARD, 10, sixRollDie);
		easyWin.roll(); //player 1 has 6
		assertEquals(10, easyWin.roll()); //should return the game winning score
		
	}
	//#22
	@Test
	void twoDieGameWinScenario()
	{
			twoDie[0] = new ReturnDie(); //returns a 6
			twoDie[1] = new ReturnDie(); //returns a 6
			//player 1 should have 12 but the game is until 10
			assertEquals(10, twoDieGame.roll());
	}
	//#23
	@Test
	void oneDiceDuplicateGameWinScenario()
	{
		PigGame duplicateGame = new PigGame(ONE_DIE_DUPLICATE, 6, sixRollDie);
		assertEquals(6, duplicateGame.roll());
	}
	
	//#24
	@Test
	void countHowManyRolls()
	{
		PigGame newStandardGame = new PigGame(STANDARD, 15, sixRollDie);
		newStandardGame.roll(); //count is 1 - rolls a 6
		newStandardGame.roll(); //count should be 2 for player 1 - roll is a 12
		newStandardGame.hold();
		assertEquals(2, newStandardGame.getRollCount());
		assertEquals(0, newStandardGame.getTurnTotal());
		newStandardGame.roll(); //player 2 has count 1
		newStandardGame.hold();
		assertEquals(1, newStandardGame.getRollCount());
	}
	
	//#25
	@Test
	void multiRollDieTester()
	{
		PigGame test1 = new PigGame(STANDARD, 10, new MultiDie());
		test1.roll(); //returns 2
		test1.roll(); //returns 3
		assertEquals(0, test1.roll()); //returns 0
		
		PigGame test2 = new PigGame(STANDARD, 10, new NoLossDie());
		test2.roll(); //2
		test2.roll(); //3
		test2.roll(); //2
		test2.hold();
		assertEquals(7, test2.getPlayerTotal(1));
		
		PigGame test3 = new PigGame(STANDARD, 20, new DuplicateDieTester());
		test3.roll(); //2
		test3.roll(); //5
		test3.roll(); //5
		test3.roll(); //4
		test3.hold();
		assertEquals(16, test3.getPlayerTotal(1));
		
		PigGame test4 = new PigGame(ONE_DIE_DUPLICATE, 20, new DuplicateDieTester());
		test4.roll(); //player 1 has 2
		test4.roll(); // 5
		test4.hold();
		assertEquals(7, test4.getPlayerTotal(1));
	}
	
	//#26
	@Test
	void noRollFirst()
	{
		
		assertThrows(PigGameException.class, () -> standardGame.hold());
			
	}
	
	//27
	@Test
	void standardGameOverallTester() //try to break the game in any ways possible
	{
		
		
		//STANDARD GAME TESTER
		PigGame standardGame = new PigGame(STANDARD, 23, sixRollDie);
		standardGame.roll();//6
		standardGame.roll();//6
		standardGame.roll();//6
		standardGame.hold();
		assertEquals(18, standardGame.getPlayerTotal(1));
		assertEquals(0, standardGame.getTurnTotal()); //resets turn total
		standardGame.roll(); //player 2 has 6
		standardGame.hold();
		assertEquals(6, standardGame.getPlayerTotal(2));
		assertEquals(23, standardGame.roll()); //player 1 wins
		
		
	}
	
	//28
	@Test
	void twoDiceGameOverallTester()
	{	
		//TWO DICE GAME TESTER
		PigGame twoGame = new PigGame(TWO_DICE, 35, twoDie);
		twoDie[0] = new NoLossDie(); //2, 3, 2, 1
		twoDie[1] = new MultiDie(); //2, 3, 1, 1
		twoGame.roll();//4 (2+2)
		twoGame.roll();//6 (3+3)
		twoGame.hold();
		assertEquals(10, twoGame.getPlayerTotal(1));
		assertEquals(0, twoGame.getTurnTotal());
		twoGame.roll();//3 player 2(2+1)
		twoGame.hold();
		assertEquals(3, twoGame.getPlayerTotal(2));
		twoGame.roll(); //player 1 adds 2 to their 10
		twoGame.hold();
		assertEquals(12, twoGame.getPlayerTotal(1));
	}
	
	//29
	@Test
	void duplicateGameOverallTester()
	{
		//DUPLICATE DIE TESTER
		PigGame duplicate = new PigGame(ONE_DIE_DUPLICATE, 23, new NoLossDie());
		duplicate.roll();//2
		duplicate.roll();//3
		duplicate.roll();//2
		duplicate.hold();
		assertEquals(7, duplicate.getPlayerTotal(1));
		assertEquals(0, duplicate.getTurnTotal());
		duplicate.roll();//1 
		duplicate.hold();
		assertEquals(1, duplicate.getPlayerTotal(2));
	}
	
	//30 
	@Test
	void swapPlayersMoreThanOnceEach()
	{
		//player swapper
		PigGame standardGamer = new PigGame(STANDARD, 23, sixRollDie);
		standardGamer.roll();//player 1 has 6
		standardGamer.hold();//player 2s turn
		standardGamer.roll();//player 2 has 6
		standardGamer.hold();//player 1s turn
		standardGamer.roll(); //player 1 has 12
		standardGamer.hold(); //player 1
		assertEquals(12, standardGamer.getPlayerTotal(1));
	}
	
	//31 
	@Test
	void gameOverThenTryToRollAndHold()
	{
		PigGame standardGamer = new PigGame(STANDARD, 5, sixRollDie);
		 //player rolls a 6 which is greater than 5, they win
		assertEquals(5, standardGamer.roll());
		assertEquals(5,standardGamer.getPlayerTotal(1));
		assertThrows(PigGameException.class, () -> standardGamer.roll());
		assertThrows(PigGameException.class, () -> standardGamer.hold());
		
	}
	
	//32 
	@Test
	void setPlayerScoreToWinningAfterMoreThanOneRound()
	{
		PigGame testGame = new PigGame(STANDARD, 13, sixRollDie);
		testGame.roll(); //player 1 - 6
		testGame.roll(); //player 1 - 12
		testGame.hold();
		assertEquals(12, testGame.getPlayerTotal(1));
		testGame.roll(); //player 2 - 6
		testGame.roll(); //player 2 - 12
		testGame.hold();
		assertEquals(12, testGame.getPlayerTotal(2));
		testGame.roll(); //player 1 has 13
		assertEquals(13, testGame.getPlayerTotal(1));
			
	}
	
	

}
