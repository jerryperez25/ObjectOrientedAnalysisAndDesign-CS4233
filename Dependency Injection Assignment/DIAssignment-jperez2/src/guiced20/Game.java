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

import java.util.List;

import com.google.inject.Inject;

import guiced20.service.BoardService;
import guiced20.service.PieceService;

/**
 * This is the generic game. It will be populated with the correct pieces by the
 * GameBindingModule.
 * 
 * The only thing you can do to this file is use annotations and add one or more
 * constructors if necessary. DO NOT just use constructor injection for both the
 * pieces and the board services.
 * 
 * @version Mar 24, 2020
 */
public class Game
{
	private PieceService pieces;
	private BoardService board;
	
	@Inject //Constructor Injection
	public Game(BoardService board)
	{
		this.board = board;
	}
	@Inject //Method Injection
	public void setPieces(PieceService pieces)
	{
		this.pieces = pieces;
	}
	public String getBoardSize()
	{
		return board.getBoardSize();
	}
	
	public List<String> getPieces()
	{
		return pieces.getPieceNames();
	}
}
