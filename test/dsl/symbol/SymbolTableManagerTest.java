/*******************************************************************************
 * Copyright (c) 2015 Gary F. Pollice
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Used in CS4533/CS544 at Worcester Polytechnic Institute
 *******************************************************************************/

package dsl.symbol;

import static org.junit.Assert.*;
import org.junit.*;
import static dsl.utility.DSLType.*;

/**
 * Test cases for the SymbolTableManager singleton.
 * @version Feb 8, 2015
 */
public class SymbolTableManagerTest
{
	private SymbolTableManager stm = SymbolTableManager.getInstance();
	
	@Before
	public void setup()
	{
		stm.reset();
	}
	
	@Test
	public void addSymbol()
	{
		final Symbol symbol = stm.add("Kendrick Lamar");
		assertEquals("Kendrick Lamar", symbol.getId());
		assertEquals(UNDEFINED, symbol.getType());
	}
	
	@Test
	public void addSymbolAndType()
	{
		Symbol symbol = stm.add("Nirvana", ARTIST);
		assertEquals("Nirvana", symbol.getId());
		assertEquals(ARTIST, symbol.getType());
		
		symbol = stm.add("Good Kid Mad City", ALBUM);
		assertEquals("Good Kid Mad City", symbol.getId());
		assertEquals(ALBUM, symbol.getType());
		
		symbol = stm.add("Money Trees", SONG);
		assertEquals("Money Trees", symbol.getId());
		assertEquals(SONG, symbol.getType());
		
	}

	@Test(expected=DSLSymbolException.class)
	public void addSymbolTwice()
	{
		stm.add("Nirvana");
		stm.add("Nirvana", ARTIST );
	}
	
	@Test
	public void addSameIdInParentAndChild()
	{
		final Symbol a1 = stm.add("a");
		stm.enterScope();
		final Symbol a2 = stm.add("a");
		assertFalse(a1 == a2);
		assertTrue(stm.getSymbol("a") == a2);
		stm.exitScope();
		assertFalse(stm.getSymbol("a") == a2);
		assertTrue(stm.getSymbol("a") == a1);
	}
}
