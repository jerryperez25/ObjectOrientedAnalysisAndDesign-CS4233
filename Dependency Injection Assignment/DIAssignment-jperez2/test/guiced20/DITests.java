/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package guiced20;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.google.inject.*;
import guiced20.service.*;

/**
 * Test for assignment on Dependency Injection with Guice
 * @version Mar 24, 2020
 */
class DITests
{
	@Test
	void chessTest()
	{
	    VersionProvider.setVersionName("chess");
		Injector injector = Guice.createInjector(new GameBindingModule());
		Game g = injector.getInstance(Game.class);
		assertEquals("8 X 8 squares", g.getBoardSize());
		List<String> pieces = g.getPieces();
		for (ChessPieces.Piece cp : ChessPieces.Piece.values())
		{
			assertTrue(pieces.contains(cp.name()));
		}
	}
	
	@Test
	void strategoTest()
	{
	    VersionProvider.setVersionName("stratego");
		Injector injector = Guice.createInjector(new GameBindingModule());
		Game g = injector.getInstance(Game.class);
		assertEquals("10 X 10 squares", g.getBoardSize());
		List<String> pieces = g.getPieces();
		for (StrategoPieces.Piece sp : StrategoPieces.Piece.values())
		{
			assertTrue(pieces.contains(sp.name()));
		}
	}
	
}
