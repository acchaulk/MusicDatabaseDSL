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

package dsl.gen;

import java.util.Stack;


import dsl.ast.*;
import dsl.ast.ASTNodeFactory.*;
import dsl.lexparse.DSLParser;
import dsl.utility.*;
import static dsl.utility.DSLType.*;
import static dsl.ast.ASTNode.ASTNodeType.*;

/**
 * Description
 * 
 * @version Feb 21, 2015
 */
public class DSLCodeGenerator extends ASTVisitor<DSLType> {

	private final String DEFAULT_PACKAGE = "djkcode";
	private String classPackage;

	public DSLCodeGenerator() {
		classPackage = DEFAULT_PACKAGE;
	}
	
	


}
