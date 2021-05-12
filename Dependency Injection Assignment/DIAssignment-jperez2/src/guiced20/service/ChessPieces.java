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
package guiced20.service;

import java.util.*;
import static guiced20.service.ChessPieces.Piece.*;

/**
 * Pieces for the chess game.
 * You may NOT modify this class.
 * @version Mar 24, 2020
 */
public class ChessPieces implements PieceService
{
	public enum Piece {PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING};
	
	
	/*
	 * @see guiced20.service.PieceService#getPieceNames()
	 */
	@Override
	public List<String> getPieceNames()
	{
		return Arrays.asList(PAWN.name(), KNIGHT.name(), BISHOP.name(), 
			ROOK.name(), QUEEN.name(), KING.name());
	}

	
	
}
