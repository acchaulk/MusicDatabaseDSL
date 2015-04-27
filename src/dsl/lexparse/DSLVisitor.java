// Generated from DSL.g4 by ANTLR 4.5
package dsl.lexparse;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DSLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DSLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DSLParser#dslText}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDslText(DSLParser.DslTextContext ctx);
	/**
	 * Visit a parse tree produced by {@link DSLParser#createPlaylist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreatePlaylist(DSLParser.CreatePlaylistContext ctx);
	/**
	 * Visit a parse tree produced by {@link DSLParser#queryList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryList(DSLParser.QueryListContext ctx);
	/**
	 * Visit a parse tree produced by {@link DSLParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(DSLParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link DSLParser#queryString}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryString(DSLParser.QueryStringContext ctx);
	/**
	 * Visit a parse tree produced by {@link DSLParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(DSLParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DSLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(DSLParser.TypeContext ctx);
}