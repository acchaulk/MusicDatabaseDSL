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

package dsl.ast;

import static org.junit.Assert.*;
import org.junit.*;
import dsl.ast.ASTNodeFactory.*;
import static dsl.utility.DSLType.*;

/**
 * Description
 * @version Feb 10, 2015
 */
public class ASTVisitorTest
{
	private ASTNode root;
	/**
	 * @throws java.lang.Exception
	 */
//	@Before
	public void setUp() throws Exception
	{
		root = CreatePlaylistNode.makeCreatePlaylistNode("test");
		ASTNode child = QueryNode.makeQueryNode(null, null, null);
		root.addChild(child);
		child = QueryNode.makeQueryNode(null, null, null);
		root.addChild(child);
	}

//	@Test
	public void test()
	{
		assertEquals(UNDEFINED, root.type);
		root.accept(new TestVisitor());
	}

	class TestVisitor extends ASTVisitor<Void>
	{
		@Override
		public Void visit(CreatePlaylistNode node)
		{
			System.out.println("CreatePlaylistNode");
			visitChildren(node);
			return null;
		}
		
		@Override
		public Void visit(QueryNode node)
		{
			System.out.println("QueryNode");
			return null;
		}
		
	}
}
