// Generated from DSL.g4 by ANTLR 4.5
package dsl.lexparse;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DSLParser}.
 */
public interface DSLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DSLParser#dslText}.
	 * @param ctx the parse tree
	 */
	void enterDslText(DSLParser.DslTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLParser#dslText}.
	 * @param ctx the parse tree
	 */
	void exitDslText(DSLParser.DslTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLParser#createPlaylist}.
	 * @param ctx the parse tree
	 */
	void enterCreatePlaylist(DSLParser.CreatePlaylistContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLParser#createPlaylist}.
	 * @param ctx the parse tree
	 */
	void exitCreatePlaylist(DSLParser.CreatePlaylistContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLParser#queryList}.
	 * @param ctx the parse tree
	 */
	void enterQueryList(DSLParser.QueryListContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLParser#queryList}.
	 * @param ctx the parse tree
	 */
	void exitQueryList(DSLParser.QueryListContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(DSLParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(DSLParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLParser#queryString}.
	 * @param ctx the parse tree
	 */
	void enterQueryString(DSLParser.QueryStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLParser#queryString}.
	 * @param ctx the parse tree
	 */
	void exitQueryString(DSLParser.QueryStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(DSLParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(DSLParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DSLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(DSLParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DSLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(DSLParser.TypeContext ctx);
}