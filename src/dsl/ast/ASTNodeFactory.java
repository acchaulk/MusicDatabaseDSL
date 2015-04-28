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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

import dsl.gen.JVMInfo;
import dsl.lexparse.DSLParser;
import dsl.symbol.Symbol;
import dsl.utility.DSLType;
import static dsl.utility.DSLType.*;
import static dsl.ast.ASTNode.ASTNodeType.*;

/**
 * Node factory for all of the nodes that are in the AST. The specific nodes are
 * defined here. Each one has one or more factory methods.
 * 
 * @version Feb 10, 2015
 */
public class ASTNodeFactory {
	// --------------------- CreatePlaylistNode -----------------------
	/**
	 * CreatePlaylist -> CreatePlaylistNode(queryListNode)
	 */
	public static class CreatePlaylistNode extends ASTNode {
		public final String playlistName;

		public CreatePlaylistNode(String playlistName) {
			super();
			nodeType = CREATEPLAYLIST;
			this.playlistName = playlistName;
		}

		@Override
		protected String extraInfo() {
			return ", name=" + playlistName;
		}

		@Override
		public <T> T accept(ASTVisitor<? extends T> visitor) {
			return visitor.visit(this);
		}

		/**
		 * Factory method for the CreatePlaylistNode
		 * 
		 * @param playlistName
		 * @return the new node with no parent.
		 */
		public static CreatePlaylistNode makeCreatePlaylistNode(String playlistName) {
			return new CreatePlaylistNode(playlistName);
		}
	}
	
	// --------------------- QueryListNode -----------------------
		/**
		 * query op query -> QueryListNode(ASTNode ASTNode, op)
		 */
		public static class QueryListNode extends ASTNode {
			public QueryListNode(Token op, ASTNode expr1, ASTNode expr2) {
				super();
				nodeType = QUERYLIST;
				token = op;

				// TODO: need to get the typ eof the query

				addChild(expr1);
				addChild(expr2);
			}
			
			public QueryListNode(ASTNode expr) {
				super();
				nodeType = QUERYLIST;
				addChild(expr);
			}

			public ASTNode getExpr1() {
				return getChild(0);
			}

			public ASTNode getExpr2() {
				return getChild(1);
			}

			public int getOp() {
				return token.getType();
			}

			@Override
			public <T> T accept(ASTVisitor<? extends T> visitor) {
				return visitor.visit(this);
			}

			public static QueryListNode makeQueryListNode(Token op,
					ASTNode expr1, ASTNode expr2) {
				return new QueryListNode(op, expr1, expr2);
			}
			public static QueryListNode makeQueryListNode(ASTNode expr) {
				return new QueryListNode(expr);
			}
		}

		// --------------------- QueryStringNode -----------------------
		/**
		 * ID -> IDNode(token symbol)
		 */
		public static class QueryStringNode extends ASTNode {
			public Symbol symbol;

			public QueryStringNode(Token t) {
				super();
				nodeType = QUERYSTRING;
				token = t;
				symbol = null;
			}

			public DSLType getType() {
				return symbol != null ? symbol.getType() : type;
			}

			public int getAddress() {
				int address = symbol.getAddress();
				if (address == Symbol.NO_ADDRESS) {
					symbol.setAddress(JVMInfo.getNextAddress());
				}
				return symbol.getAddress();
			}

			public void setType(DSLType dt) {
				type = dt;
				if (symbol != null) {
					symbol.setType(dt);
				}
			}

			@Override
			public String extraInfo() {
				return symbol == null ? "" : ", symbol={" + symbol.toString() + "}";
			}

			@Override
			public <T> T accept(ASTVisitor<? extends T> visitor) {
				return visitor.visit(this);
			}

			public String getName() {
				return token.getText();
			}

			public static QueryStringNode makeQueryNode(Token t) {
				return new QueryStringNode(t);
			}
		}

		// --------------------- QueryNode -----------------------
		/**
		 * query -> QueryNode(IDNode, type)
		 */
		public static class QueryNode extends ASTNode {
			public QueryNode(DSLType type, FunctionNode a, QueryStringNode queryString) {

				super();
				nodeType = QUERY;
				this.type = type;

				addChild(queryString); 
				addChild(a);
			}


			public ASTNode getQueryStringNode() {
				return getChild(0);
			}
			public ASTNode getFunction(){
				return getChild(1);
			}

			@Override
			public <T> T accept(ASTVisitor<? extends T> visitor) {
				return visitor.visit(this);
			}

			public static QueryNode makeQueryNode(DSLType type, FunctionNode a, QueryStringNode queryString) {
				return new QueryNode(type, a, queryString);
			}
		}
		public static class FunctionNode extends ASTNode{
			public FunctionNode(Token t){
				super();
				nodeType = FUNCTION;
				token = t;
			}
			public static FunctionNode makeFunctioNode(Token t) {
								return new FunctionNode(t);
							}
			
		}

//	// --------------------- ConstantNode -----------------------
//	/**
//	 * CONSTANT -> ConstantNode(token) Constants are TRUE, FALSE, INTEGER tokens
//	 */
//	//	public static class ConstantNode extends ASTNode {
//	//		private String firstInteger;
//	//		private String secondInteger;
//	//
//	//		public ConstantNode(Token t) {
//	//			super();
//	//			nodeType = CONSTANT;
//	//			token = t;
//	//			type = t.getType() == DijkstraParser.INTEGER ? INT : BOOLEAN;
//	//		}
//	//
//	//		public ConstantNode(String firstInteger, String secondInteger) {
//	//			super();
//	//			this.firstInteger = firstInteger;
//	//			this.secondInteger = secondInteger;
//	//			nodeType = CONSTANT;
//	//			type = FLOAT;
//	//		}
//	//
//	//		@Override
//	//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//	//			return visitor.visit(this);
//	//		}
//	//
//	//		public static ConstantNode makeConstantNode(Token t) {
//	//			return new ConstantNode(t);
//	//		}
//	//
//	//		public static ConstantNode makeConstantNode(String firstInteger,
//	//				String secondInteger) {
//	//			return new ConstantNode(firstInteger, secondInteger);
//	//		}
//	//
//	//		public String getFirstInteger() {
//	//			return firstInteger;
//	//		}
//	//
//	//		public String getSecondInteger() {
//	//			return secondInteger;
//	//		}
//	//
//	//	}
//
//	// --------------------- DeclarationNode -----------------------
//	/**
//	 * declaration -> DeclarationNode(IDNode, type)
//	 */
//	public static class VariableDeclarationNode extends ASTNode {
//		IDNode[] M;
//
//		public VariableDeclarationNode(IDNode[] id, DijkstraType type) {
//
//			super();
//			M = new IDNode[id.length];
//			int i = 0;
//			nodeType = DECL;
//			this.type = type;
//			for (IDNode z : id) {
//
//				addChild(z); // child[0] is the ID
//				M[i] = z;
//				i++;
//			}
//
//		}
//
//		public IDNode[] getId() {
//			return M;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static VariableDeclarationNode makeVariableDeclarationNode(
//				IDNode[] id) {
//			return new VariableDeclarationNode(id, DijkstraType.UNDEFINED);
//		}
//
//		public static VariableDeclarationNode makeVariableDeclarationNode(
//				IDNode[] id, DijkstraType type) {
//			return new VariableDeclarationNode(id, type);
//		}
//	}
//
//	// --------------------- AssignNode -----------------------
//	/**
//	 * assignStatement -> AssignNode(IDNode Expression, type)
//	 */
//	public static class AssignNode extends ASTNode {
//		public AssignNode() {
//			super();
//			nodeType = ASSIGN;
//		}
//
//		List<ASTNode> astX = new ArrayList<ASTNode>();
//		List<ASTNode> n = new ArrayList<ASTNode>();
//
//		public AssignNode(ASTNode[] id, ASTNode[] expression) {
//			super();
//			nodeType = ASSIGN;
//			for (ASTNode node : id) {
//				n.add(node);
//				addChild(node); // identifier
//			}
//			for (ASTNode ast : expression) {
//				astX.add(ast);
//				addChild(ast); // expression or terminal
//			}
//
//		}
//
//
//
//		public List<ASTNode> getIdOrArrayAcessor() {
//			return n;
//		}
//
//		public List<ASTNode> getExpression() {
//			return astX;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static AssignNode makeAssignNode() {
//			return new AssignNode();
//		}
//
//		public static AssignNode makeAssignNode(ASTNode[] id,
//				ASTNode[] expression) {
//			return new AssignNode(id, expression);
//		}
//	}
//
//	// --------------------- ParameterNode -----------------------
//	/**
//	 * paramter -> paramterNode(IDNode, type)
//	 */
//	public static class parameterNode extends ASTNode {
//		public parameterNode(IDNode id, DijkstraType type) {
//			super();
//			nodeType = PARAMETER;
//			this.type = type;
//			addChild(id); // child[0] is the ID
//		}
//
//		public IDNode getId() {
//			return (IDNode) getChild(0);
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static parameterNode makeParameterNode(IDNode id) {
//			return new parameterNode(id, DijkstraType.UNDEFINED);
//		}
//
//		public static parameterNode makeParameterNode(IDNode id,
//				DijkstraType type) {
//			return new parameterNode(id, type);
//		}
//	}
//
//	// --------------------- ArrayNode -----------------------
//	/**
//	 * arrayNode -> ArrayNode(IDNode[] id, ASTNODE[] Expression, type)
//	 */
//	public static class ArrayNode extends ASTNode {
//		IDNode[] nodes;
//
//		public ArrayNode(IDNode[] id, ASTNode expression, DijkstraType type) {
//			super();
//			nodeType = ARRAY;
//			this.type = type;
//			int init = 0;
//			nodes = new IDNode[id.length];
//			addChild(expression); // expression or terminal
//			for (IDNode node : id) {
//				addChild(node);
//				// identifier
//				nodes[init] = node;
//				init += 1;
//			}
//
//		}
//
//		public IDNode[] getId() {
//			return nodes;
//		}
//
//		public ASTNode getExpression() {
//			return getChild(0);
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static ArrayNode makeArrayNode(IDNode[] id, ASTNode expression,
//				DijkstraType type) {
//			return new ArrayNode(id, expression, type);
//		}
//	}
//
//	// --------------------- InputNode -----------------------
//	/**
//	 * inputStatement -> InputNode(IDNode type)
//	 */
//	public static class InputNode extends ASTNode {
//		List<IDNode> input = new ArrayList<IDNode>();
//
//		public InputNode(List<IDNode> id) {
//			super();
//			nodeType = INPUT;
//			for (IDNode m : id) {
//				input.add(m);
//				addChild(m);
//			}
//
//		}
//
//		public List<IDNode> getID() {
//			return input;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static InputNode makeInputNode(List<IDNode> id) {
//			return new InputNode(id);
//		}
//	}
//
//	// --------------------- OutputNode -----------------------
//	/**
//	 * outputStatement -> OutputNode(ASTNode)
//	 */
//	public static class OutputNode extends ASTNode {
//		public OutputNode(ASTNode expr) {
//			super();
//			nodeType = OUTPUT;
//			addChild(expr);
//		}
//
//		public ASTNode getExpression() {
//			return getChild(0);
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static OutputNode makeOutputNode(ASTNode expr) {
//			return new OutputNode(expr);
//		}
//	}
//
//	// --------------------- ReturnNode -----------------------
//	/**
//	 * ReturnStatement -> ReturnNode(ASTNode[])
//	 */
//	public static class ReturnNode extends ASTNode {
//		public ReturnNode() {
//			super();
//			nodeType = RETURN;
//		}
//
//		List<ASTNode> m = new ArrayList<ASTNode>();
//
//		public ReturnNode(List<ASTNode> expr) {
//			super();
//			nodeType = RETURN;
//			for (ASTNode ast : expr) {
//				addChild(ast);
//				m.add(ast);
//			}
//
//		}
//
//		public List<ASTNode> getExpressions() {
//			return m;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static ReturnNode makeReturnNode(List<ASTNode> expr) {
//			return new ReturnNode(expr);
//		}
//
//		public static ReturnNode makeReturnNode() {
//			return new ReturnNode();
//		}
//	}
//
//	// --------------------- ArrayAccessorNode -----------------------
//	/**
//	 * ArrayAccessor -> ArrayAccessorNode(IDNode node,ASTNode expr)
//	 */
//	public static class ArrayAccessorNode extends ASTNode {
//
//		public ArrayAccessorNode(IDNode node, ASTNode expr) {
//			super();
//			nodeType = ARRAYACCESSOR;
//			addChild(node);
//			addChild(expr);
//		}
//
//		public IDNode getID() {
//			return (IDNode) getChild(0);
//		}
//
//		public ASTNode getExpression() {
//			return getChild(1);
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static ArrayAccessorNode makeArrayAccessorNode(IDNode n, ASTNode expr) {
//			return new ArrayAccessorNode(n, expr);
//		}
//
//	}
//
//	// --------------------- FunctionCallNode -----------------------
//	/**
//	 * FunctionCall -> FunctionCallNode(IDNode node,ASTNode expr)
//	 */
//	public static class FunctionCallNode extends ASTNode {
//
//		List<ASTNode> astList = new ArrayList<ASTNode>();
//
//		public FunctionCallNode(IDNode node) {
//			super();
//			nodeType = FUNCTIONCALL;
//			addChild(node);
//		}
//
//		public FunctionCallNode(IDNode node, List<ASTNode> expr) {
//			super();
//			nodeType = FUNCTIONCALL;
//			addChild(node);
//			for (ASTNode a : expr) {
//				addChild(a);
//				astList.add(a);
//			}
//
//		}
//
//		public FunctionNode getFunc(String id) {
//			ASTNode n = this.parent;
//			while(n.nodeType != PROGRAM){
//				n = n.parent;
//			}
//			List<ASTNode> tree = n.children;
//			for (ASTNode t : tree) {
//				ASTNodeType test = t.nodeType;
//				if (test == FUNCTION) {
//					FunctionNode f = (FunctionNode) t;
//					String name = f.getID().getName();
//					if (name.equals(id)) {
//						return f;
//					}
//				}
//			}
//			return null;
//		}
//
//		public IDNode getID() {
//			return (IDNode) getChild(0);
//		}
//
//		public List<ASTNode> getExpression() {
//			return astList;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static ASTNode makeFunctionCallNode(IDNode n) {
//			return new FunctionCallNode(n);
//		}
//
//		public static ASTNode makeFunctionCallNode(IDNode n, List<ASTNode> expr) {
//			return new FunctionCallNode(n, expr);
//		}
//
//	}
//
//	// --------------------- ProcedureCallNode -----------------------
//	/**
//	 * ProcedureCall -> ProcedureCallNode(IDNode node,ASTNode expr)
//	 */
//	public static class ProcedureCallNode extends ASTNode {
//
//		List<ASTNode> astList = new ArrayList<ASTNode>();
//
//		public ProcedureCallNode(IDNode node) {
//			super();
//			nodeType = PROCEDURECALL;
//			addChild(node);
//		}
//
//		public ProcedureCallNode(IDNode node, List<ASTNode> expr) {
//			super();
//			nodeType = PROCEDURECALL;
//			addChild(node);
//			for (ASTNode a : expr) {
//				addChild(a);
//				astList.add(a);
//			}
//
//		}
//
//		public IDNode getID() {
//			return (IDNode) getChild(0);
//		}
//
//		public List<ASTNode> getExpression() {
//			return astList;
//		}
//
//		public List<parameterNode> getProc(String id) {
//			ASTNode n = this.parent;
//			while(n.nodeType != PROGRAM){
//				n = n.parent;
//			}
//			List<ASTNode> tree = n.children;
//			for (ASTNode t : tree) {
//				ASTNodeType test = t.nodeType;
//				if (test == PROCEDURE) {
//					ProcedureNode proc = (ProcedureNode) t;
//					String name = proc.getID().getName();
//					if (name.equals(id)) {
//						return proc.getParameters();
//					}
//				}
//			}
//			return null;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static ProcedureCallNode makeProcedureCallNode(IDNode n) {
//			return new ProcedureCallNode(n);
//		}
//
//		public static ProcedureCallNode makeProcedureCallNode(IDNode n,
//				List<ASTNode> expr) {
//			return new ProcedureCallNode(n, expr);
//		}
//
//	}
//
//	// --------------------- IterativeNode -----------------------
//	/**
//	 * iterativeStatement -> IterativeNode(ASTNode ASTNode) Description
//	 * 
//	 * @version Feb 12, 2015
//	 */
//	public static class IterativeNode extends ASTNode {
//		public int lineNumber;
//
//		public IterativeNode() {
//			super();
//			nodeType = ITERATIVE;
//		}
//
//		public int getLineNumber() {
//			return lineNumber;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static IterativeNode makeIterativeNode(int lineNumber) {
//			IterativeNode node = new IterativeNode();
//			node.lineNumber = lineNumber;
//			return node;
//		}
//	}
//
//	// --------------------- AlternativeNode -----------------------
//	/**
//	 * alternativeStatement -> AlternativeNode(GuardNode+)
//	 */
//	public static class AlternativeNode extends ASTNode {
//		public int lineNumber;
//
//		public AlternativeNode() {
//			super();
//			nodeType = ALTERNATIVE;
//		}
//
//		public int getLineNumber() {
//			return lineNumber;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static AlternativeNode makeAlternativeNode(int lineNumber) {
//			AlternativeNode node = new AlternativeNode();
//			node.lineNumber = lineNumber;
//			return node;
//		}
//	}
//
//	// --------------------- FunctionNode -----------------------
//	/**
//	 * Function -> FunctionNode(IDNode, paramaterNode[], DijkstraType[])
//	 */
//	public static class FunctionNode extends ASTNode {
//		List<parameterNode> plist = new ArrayList<parameterNode>();
//		List<DijkstraType> types = new ArrayList<DijkstraType>();
//
//		public FunctionNode(IDNode node, List<parameterNode> param,
//				List<DijkstraType> type, CompoundNode compound) {
//			super();
//			nodeType = FUNCTION;
//			addChild(node);
//			addChild(compound);
//			for (parameterNode p : param) {
//				addChild(p);
//				plist.add(p);
//			}
//			for (DijkstraType t : type) {
//				types.add(t);
//			}
//		}
//
//		// because params are optional
//		public FunctionNode(IDNode node, List<DijkstraType> type,
//				CompoundNode compound) {
//			super();
//			nodeType = FUNCTION;
//			addChild(node);
//			addChild(compound);
//			for (DijkstraType t : type) {
//				types.add(t);
//			}
//		}
//
//		public IDNode getID() {
//			return (IDNode) getChild(0);
//		}
//
//		public List<DijkstraType> getTypes() {
//			return types;
//		}
//
//		public CompoundNode getcompoundStmt() {
//			return (CompoundNode) getChild(1);
//		}
//
//		public List<parameterNode> getparamterList() {
//			return plist;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static FunctionNode makeFunctionNode(IDNode node,
//				List<parameterNode> param, List<DijkstraType> type,
//				CompoundNode compound) {
//			return new FunctionNode(node, param, type, compound);
//		}
//
//		// because params are optional
//		public static FunctionNode makeFunctionNode(IDNode node,
//				List<DijkstraType> type, CompoundNode compound) {
//			return new FunctionNode(node, type, compound);
//		}
//	}
//
//	// --------------------- ProcedureNode -----------------------
//	/**
//	 * Procedure -> ProcedurenNode(IDNode, paramaterNode[], DijkstraType[])
//	 */
//	public static class ProcedureNode extends ASTNode {
//		List<parameterNode> paramList = new ArrayList<parameterNode>();
//
//		public ProcedureNode(IDNode node, List<parameterNode> param,
//				CompoundNode compound) {
//			super();
//			nodeType = PROCEDURE;
//			addChild(node);
//			addChild(compound);
//			for (parameterNode para : param) {
//				addChild(para);
//				paramList.add(para);
//			}
//		}
//
//		// because params are optional
//		public ProcedureNode(IDNode node, CompoundNode compound) {
//			super();
//			nodeType = PROCEDURE;
//			addChild(node);
//			addChild(compound);
//		}
//
//		public IDNode getID() {
//			return (IDNode) getChild(0);
//		}
//
//		public CompoundNode getCompoundStmt() {
//			return (CompoundNode) getChild(1);
//		}
//
//		public List<parameterNode> getParameters() {
//			return paramList;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static ProcedureNode makeProcedureNode(IDNode node,
//				List<parameterNode> param, CompoundNode compound) {
//			return new ProcedureNode(node, param, compound);
//		}
//
//		// because params are optional
//		public static ProcedureNode makeProcedureNode(IDNode node,
//				CompoundNode compound) {
//			return new ProcedureNode(node, compound);
//		}
//	}
//
//	// --------------------- CompoundNode -----------------------
//	/**
//	 * program -> CompoundNode()
//	 */
//	public static class CompoundNode extends ASTNode {
//		public CompoundNode() {
//			super();
//			nodeType = COMPOUND;
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static CompoundNode makeCompoundNode() {
//			return new CompoundNode();
//		}
//	}
//
//	// --------------------- GuardNode -----------------------
//	/**
//	 * guard -> GuardNode(ASTNode ASTNode)
//	 */
//	public static class GuardNode extends ASTNode {
//		public GuardNode(ASTNode expr, ASTNode stmt) {
//			super();
//			nodeType = GUARD;
//			addChild(expr);
//			addChild(stmt);
//		}
//
//		public ASTNode getExpression() {
//			return getChild(0);
//		}
//
//		public ASTNode getStatement() {
//			return getChild(1);
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static GuardNode makeGuardNode(ASTNode expr, ASTNode stmt) {
//			return new GuardNode(expr, stmt);
//		}
//	}
//
//	// --------------------- UnaryExpressionNode -----------------------
//	/**
//	 * op expression -> UnaryExpressionNode(ASTNode, op)
//	 */
//	public static class UnaryExpressionNode extends ASTNode {
//		public UnaryExpressionNode(Token op, ASTNode expr) {
//			super();
//			nodeType = UNARYEXPR;
//			token = op;
//			addChild(expr);
//			type = op.getType() == DijkstraParser.TILDE ? BOOLEAN : NUM;
//		}
//
//		public int getOp() {
//			return token.getType();
//		}
//
//		public ASTNode getExpression() {
//			return getChild(0);
//		}
//
//		@Override
//		public <T> T accept(ASTVisitor<? extends T> visitor) {
//			return visitor.visit(this);
//		}
//
//		public static UnaryExpressionNode makeUnaryExpressionNode(Token op,
//				ASTNode expr) {
//			return new UnaryExpressionNode(op, expr);
//		}
//	}

	
	
}
