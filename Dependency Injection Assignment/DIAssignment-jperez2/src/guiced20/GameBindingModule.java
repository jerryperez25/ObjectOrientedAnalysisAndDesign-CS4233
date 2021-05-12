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

import com.google.inject.AbstractModule;

import guiced20.service.BoardService;
import guiced20.service.ChessBoard;
import guiced20.service.ChessPieces;
import guiced20.service.PieceService;
import guiced20.service.StrategoBoard;
import guiced20.service.StrategoPieces;


/**
 * This is where all of the binding will occur for the game. The VersionProvider
 * will be set before the test creates the injector. See DITests.java in the
 * text folder.
 * @version Mar 24, 2020
 */
public class GameBindingModule extends AbstractModule
{
	/*
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure()
	{
		
		String vName = VersionProvider.getVersionName();
		
		if(vName == "chess")
		{
			bind(BoardService.class).to(ChessBoard.class);
			bind(PieceService.class).to(ChessPieces.class);
		}
		else
		{
			bind(BoardService.class).to(StrategoBoard.class);
			bind(PieceService.class).to(StrategoPieces.class);
		}
		
		
	}
	
}
