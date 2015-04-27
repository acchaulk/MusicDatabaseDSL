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

package dsl.utility;

/**
 * General runtime exception for the DSL compiler.
 * @version Feb 14, 2015
 */
public class DSLException extends RuntimeException
{
	
	/**
	 * @see java.lang.RuntimeException#RuntimeException()
	 */
	public DSLException(String msg)
	{
		super(msg);
	}
	
	/**
	 * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
	 */
	public DSLException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
