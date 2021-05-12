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

/**
 * The Stratego board service.
 * You may NOT modify this class.
 * 
 * @version Mar 24, 2020
 */
public class StrategoBoard implements BoardService
{
	
	/*
	 * @see guiced20.service.BoardService#getBoardSize()
	 */
	@Override
	public String getBoardSize()
	{
		return "10 X 10 squares";
	}
	
}
