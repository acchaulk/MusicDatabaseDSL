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

import java.util.*;
import dsl.utility.DSLType;

/**
 * Singleton manager class that manages all symbol tables in the compilation.
 * 
 * @version Feb 7, 2015
 */
public class SymbolTableManager
{
	private static SymbolTableManager instance =  null;
	private SymbolTable currentSymbolTable;
	private final ArrayList<SymbolTable> tables;
	
	/**
	 * Constructor that sets up the initial (global) symbol table.
	 */
	private SymbolTableManager()
	{
		tables = new ArrayList<SymbolTable>();
		currentSymbolTable = new SymbolTable(null);
		tables.add(currentSymbolTable);
	}
	
	/**
	 * Enter a new scope. This adds a new symbol table to the lexical scope.
	 */
	public void enterScope()
	{
		currentSymbolTable = new SymbolTable(currentSymbolTable);
		tables.add(currentSymbolTable);
	}
	
	/**
	 * Exit a scope.
	 */
	public void exitScope()
	{
		currentSymbolTable = currentSymbolTable.getParent();
	}

	/**
	 * @return the instance
	 */
	public static SymbolTableManager getInstance()
	{
		if (instance == null) {
			instance = new SymbolTableManager();
		}
		return instance;
	}
	
	// The next methods are pass through methods to the current symbol table, but the
	// symbol table manager takes care of creating the appropriate symbols.
	
	/**
	 * Add a symbol to the current symbol table.
	 * @param symbol the symbol to add
	 * @return the added symbol
	 * @throws DSLSymbolException if the symbol already exists in this table
	 * @see SymbolTable#add(Symbol)
	 */
	public Symbol add(Symbol symbol)
	{
		return currentSymbolTable.add(symbol);
	}
	
	/**
	 * Add a symbol to the current symbol table with a type of UNDEFINED.
	 * @param id the symbol name 
	 * @return the added symbol
	 * @throws DSLSymbolException if the symbol already exists in this table
	 * @see SymbolTable#add(Symbol)
	 * @see Symbol#Symbol(String)
	 */
	public Symbol add(String id)
	{
		return currentSymbolTable.add(new Symbol(id));
	}
	
	/**
	 * Add a symbol to the current symbol table with the type specified.
	 * @param id the symbol name 
	 * @param symbolType the symbol's type
	 * @return the added symbol
	 * @throws DSLSymbolException if the symbol already exists in this table
	 * @see SymbolTable#add(Symbol)
	 * @see Symbol#Symbol(String, dsl.utility.DijkstraType)
	 */
	public Symbol add(String id, DSLType symbolType)
	{
		return currentSymbolTable.add(new Symbol(id, symbolType));
	}
	
	/**
	 * Add a symbol to the current symbol table with a type of UNDEFINED.
	 * @param symbol symbol to add 
	 * @return the symbol (whether added or one that was visible)
	 * @see SymbolTable#addIfNew(Symbol)
	 * @see Symbol#Symbol(String)
	 */
	public Symbol addIfNew(Symbol symbol)
	{
		return currentSymbolTable.addIfNew(symbol);
	}
	
	/**
	 * Add a symbol to the current symbol table with a type of UNDEFINED.
	 * @param id the symbol name 
	 * @return the symbol (whether added or one that was visible)
	 * @see SymbolTable#addIfNew(Symbol)
	 * @see Symbol#Symbol(String)
	 */
	public Symbol addIfNew(String id)
	{
		return currentSymbolTable.addIfNew(new Symbol(id));
	}
	
	/**
	 * Add a symbol to the current symbol table with a type of UNDEFINED.
	 * @param id the symbol name 
	 * @param type the type associated with the id
	 * @return the symbol (whether added or one that was visible)
	 * @see SymbolTable#addIfNew(Symbol)
	 * @see Symbol#Symbol(String)
	 */
	public Symbol addIfNew(String id, DSLType type)
	{
		return currentSymbolTable.addIfNew(new Symbol(id, type));
	}
	
	/**
	 * Get the symbol with the specified key in the current scope.
	 * @param id the desired symbol's ID
	 * @return the symbol referenced or null if it does not exist.
	 * @see SymbolTable#getSymbol(String)
	 */
	public Symbol getSymbol(String id)
	{
		return currentSymbolTable.getSymbol(id);
	}
	
	/**
	 * @return the current symbol table
	 */
	public SymbolTable getCurrentSymbolTable()
	{
		return currentSymbolTable;
	}

	/**
	 * @return the tables
	 */
	public ArrayList<SymbolTable> getTables()
	{
		return tables;
	}
	
	// Next methods added for testing and debugging
	/**
	 * @param i
	 * @return the symbol table at index i in the symbol table array
	 */
	public SymbolTable getSymbolTable(int i)
	{
		return tables.get(i);
	}
	
	public void reset()
	{
		tables.clear();
		currentSymbolTable = new SymbolTable(null);
		tables.add(currentSymbolTable);
	}
}
