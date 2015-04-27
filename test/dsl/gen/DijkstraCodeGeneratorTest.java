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

import static org.junit.Assert.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

import org.antlr.v4.runtime.*;
import org.junit.*;

import dijkstra.ast.*;
import dijkstra.lexparse.DijkstraParser;
import dijkstra.runtime.DijkstraRuntime;
import dijkstra.semantic.DijkstraSemanticException;
import dijkstra.semantic.TypeChecker;
import dijkstra.symbol.*;
import dijkstra.utility.DijkstraFactory;

/**
 * Code generation tests.
 * 
 * @version Feb 22, 2015
 */
public class DijkstraCodeGeneratorTest extends ClassLoader {
	private DijkstraParser parser;
	private ParserRuleContext tree;
	private ASTCreator creator;
	private ASTNode ast;
	private SymbolCreator symbolCreator;
	private SymbolTableManager stm;
	private byte[] code;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		stm = SymbolTableManager.getInstance();
		stm.reset();
		DijkstraRuntime.setInputs(null);
	}

	@Test
	public void printIntConstant() throws Exception {
		runCode("print 1");
	}

	@Test
	public void funTest() throws Exception {
		runCode("fun incr(int i) : int { " + "return i + 1 " + "} "
				+ "x <- incr(1) " + "print x " + "x <- incr(-1) " + "print x "
				+ "" + "print incr(41) ");
	}
	@Test
	public void isPrimeFun() throws Exception{
		runCode("fun isPrime(candidateNumber) : int "
				+ "{ "
				+ ""
				+ "isPrime <- false "
				+ "if "
				+ "candidateNumber < 2 :: continue <- false "
				+ "candidateNumber = 2 :: return true "
				+ "candidateNumber = 3 :: return true "
				+ "candidateNumber > 3 :: { "
				+ "if "
				+ "candidateNumber mod 2 = 0 :: continue <- false "
				+ "candidateNumber mod 2 ~= 0 :: continue <- true "
				+ "fi "
				+ "i, isPrime <- 3, false "
				+ "do "
				+ "continue & (i <= candidateNumber div 2) :: { "
				+ "if "
				+ "candidateNumber mod i = 0 :: continue <- false "
				+ "candidateNumber mod i > 0 :: i <- i + 2 "
				+ "fi "
				+ "} "
				+ "continue & (i > candidateNumber div 2) :: isPrime, continue <- true, false "
				+ "od "
				+ "return isPrime "
				+ "} "
				+ "fi "
				+ "return false "
				+ "} "
				+ "print isPrime(37) "
				+ "print isPrime(225)");
	}
	
	@Test
	public void FuncSort() throws Exception{
		runCode("float[8] array; "
				+ "fun isDone():boolean{ "
				+ "if "
				+ "(array[0]<= array[1]) & (array[1]<= array[2]) &(array[2]<= array[3]) &(array[3]<= array[4]) &(array[4]<= array[5]) &(array[5]<= array[6]) & (array[6]<= array[7]) :: "
				+ "return true; "
				+ "true:: return false; "
				+ "fi "
				+ "return false; "
				+ "} "
				+ "array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7] <- 5, 3, 2, 1, 6, 9, 4, 19; "
				+ "boolean done; "
				+ "done <- false; "
				+ "do "
				+ "~done :: "
				+ "{int i; "
				+ "i <- 0; "
				+ "do "
				+ "i<7 :: "
				+ "{ "
				+ "if "
				+ "array[i] > array[i+1] :: {array[i],array[i+1] <- array[i+1], array[i]; i <- i+1;} "
				+ "true:: i<- i+1; "
				+ "fi "
				+ "} "
				+ "od "
				+ "done <- isDone(); "
				+ "} "
				+ "od "
				+ "print array[0]; "
				+ "print array[1]; "
				+ "print array[2]; "
				+ "print array[3]; "
				+ "print array[4]; "
				+ "print array[5]; "
				+ "print array[6]; "
				+ "print array[7]; ");
	}
	
	@Test
	public void FuncTemp() throws Exception{
		runCode("fun convFtoC(float temp) : float { "
				+ "return (temp - 32) * (5 / 9) "
				+ "} "
				+ "fun convCtoF(float temp) : float { "
				+ "return temp * (9 / 5) + 32 "
				+ "} "
				+ "outputTemp <- convFtoC(68.0) "
				+ "print outputTemp "
				+ "outputTemp <- convCtoF(20.0) "
				+ "print outputTemp");
	}
	
	@Test
	public void FuncTemp1() throws Exception{
		runCode("fun convFtoC(float temp) : float { "
				+ "return (temp - 32) * (5 / 9) "
				+ "} "
				+ "fun convCtoF(float temp) : float { "
				+ "return temp * (9 / 5) + 32 "
				+ "} "
				+ "outputTemp <- convFtoC(68) "
				+ "print outputTemp "
				+ "outputTemp <- convCtoF(20.0) "
				+ "print outputTemp");
	}
	
	@Test
	public void ProcIncr() throws Exception{
		runCode("	int i "
				+ "proc incr(int x) { i <- i + x } "
				+ "i <- 0 "
				+ "incr(5) "
				+ "print i "
				+ "incr(32) "
				+ "print i");
	}
	@Test
	public void Array() throws Exception{
		runCode("int [5] a "
				+ "a[0], a[1], a[2], a[3], a[4], a[5] <- 1, 2, 3, 4, 5, 6 "
				+ "print a[0] "
				+ "print a[1] "
				+ "print a[2] "
				+ "print a[3] "
				+ "print a[4] "
				+ "print a[5]"
				);
	}
	

	@Test
	public void checkFloatLessThan() throws Exception {
		runCode("f1, f2, f3 <- -6.8, 820.75, 2387.92" + "print f3 < 2387.921");
		assertEquals("b=true", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void checkAssertions() throws Exception {
		runCode("i1, i2, i3 <- 2, -5, 42 "
				+ "f1, f2, f3 <- -6.8, 820.75, 2387.92 "
				+ "t, f <- true, false " + ""
				+ "print i3 div 7 + 3 * 8 mod 5 = 10 ");
		assertEquals("b=true", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void checkAssertions2() throws Exception {
		runCode("i1, i2, i3 <- 2, -5, 42 "
				+ "f1, f2, f3 <- -6.8, 820.75, 2387.92 "
				+ "t, f <- true, false " + " "
				+ "print (i3 div 7 + 3 * 8 mod 5 = 10) = t");
		assertEquals("b=true", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void checkAssertions3() throws Exception {
		runCode("f3 <-  2387.92 " + "print f3 > 2387.919 ");
		assertEquals("b=true", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void checkAssertions4() throws Exception {
		runCode("i1, i2, i3 <- 2, -5, 42 "
				+ "f1, f2, f3 <- -6.8, 820.75, 2387.92 "
				+ "t, f <- true, false " + "print f3 + -f3 = 0.0 ");
		assertEquals("b=true", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void checkAssertions5() throws Exception {
		runCode("i1, i2, i3 <- 2, -5, 42 "
				+ "f1, f2, f3 <- -6.8, 820.75, 2387.92 "
				+ "t, f <- true, false " + "print i1 + -i1 = 0 ");
		assertEquals("b=true", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void printFloatConstant() throws Exception {
		runCode("print 5.5");
	}

	@Test
	public void printTrue() throws Exception {
		runCode("print true");
	}

	@Test
	public void printFalse() throws Exception {
		runCode("print false");
	}

	@Test
	public void simpleAssign() throws Exception {
		runCode("a <- 1; print a");
	}

	@Test
	public void basicAlternative() throws Exception {
		runCode("b <- true a <- 1\n" + "if\n" + "  b :: a <- -5\n" + "fi\n"
				+ "print a");
	}

	@Test
	public void logicalNegation() throws Exception {
		runCode("print ~true");
	}

	@Test
	public void nestedUnaryMinus() throws Exception {
		runCode("print ----42");
	}

	@Test
	public void checkStack() throws Exception {
		runCode("x, y, z <- 3, -3, 7 " + "f, g, h, i <- 3.4, 42.0, 7.0, 3.0 "
				+ "print x < y " + "print y >= y " + "print z > y * y "
				+ "print f < g " + "print z * h < g " + "print g < 2 * f * h "
				+ "print g ~= g " + "print x <= i " + "print x < y = f < g "
				+ "print x < y = f < g = h < i");
	}

	@Test
	public void multipleGuardsInAlternative() throws Exception {
		String s = "i <- true; j <- false; k <- true\n" + "x <- 0\n" + "if\n"
				+ "  i :: x <- 42\n" + "  j :: x <- 1\n" + "  k :: x <- 2\n"
				+ "fi\n" + "print x";
		runCode(s);
	}

	@Test
	public void lessThanFalse() throws Exception {
		runCode("print 5 < 2");
	}

	@Test
	public void lessThanTrue() throws Exception {
		runCode("print 2 < 5");
	}

	@Test
	public void greaterThanFalse() throws Exception {
		runCode("print 2 > 5");
	}

	@Test
	public void greaterThanTrue() throws Exception {
		runCode("print 5 > 2");
	}

	@Test
	public void trueEquals() throws Exception {
		runCode("print 2 = 2", "trueEquals");
	}

	@Test
	public void falseEquals() throws Exception {
		runCode("print 1 = 2", "falseEquals");
	}

	@Test
	public void trueNotEquals() throws Exception {
		runCode("print 42 ~= 41", "trueNotEquals");
	}

	@Test
	public void trueNotEqualsWFloats() throws Exception {
		runCode("print 42.2 = 41.2", "trueNotEquals");
	}

	@Test
	public void falseNotEquals() throws Exception {
		runCode("print 42 ~= 42", "falseNotEquals");
	}

	@Test
	public void simpleAdd() throws Exception {
		runCode("print 5+37", "simpleAdd-expect 42");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleAddWFloat() throws Exception {
		runCode("print 5.5 + 37.2", "simpleAdd-expect 42");
		assertEquals("f=42.7", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifFloat() throws Exception {
		runCode("if 42.1 = 42.1 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifORint() throws Exception {
		runCode("if 42 ~= 42 | 42 > 41 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void testLogicalOrWithGuard() throws Exception {
		runCode("if 5 > 1 :: print 1 > 2 fi");
		assertEquals("b=false", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifANDint() throws Exception {
		runCode("if 42 > 41 | 41 = 42 :: print 5  fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifNotFloat() throws Exception {
		runCode("if 42.2 ~= 42.6 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifLessFloat() throws Exception {
		runCode("if 42.2 < 42.6 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifLessOREqualFloat() throws Exception {
		runCode("if 42.2 <= 42.6 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifGreaterThanOrEqualFloat() throws Exception {
		runCode("if 42.6 >= 42.2 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifGreaterThanOrEqual2Float() throws Exception {
		runCode("if 42.6 >= 42.6 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifGreaterThanFloat() throws Exception {
		runCode("if 42.6 > 42.2 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifGreaterThanFloatX() throws Exception {
		runCode("if 2387.92 > 2387.919 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleifLessOREqualForEqualFloat() throws Exception {
		runCode("if 42.6 <= 42.6 :: print 5 fi");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void subtractByAddition() throws Exception {
		runCode("print 47 + -5", "subtractByAddition");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}

	
	@Test
	public void simpleSubtraction() throws Exception {
		runCode("print 47 - 5", "simpleSubtraction");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void additionBySubtraction() throws Exception {
		runCode("print 37 --5", "additionBySubtraction");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleMultiplication() throws Exception {
		runCode("print 7*6", "simpleMultiplication");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleDivision() throws Exception {
		runCode("print 84.0 / 2.0", "simpleDivision");
		assertEquals("f=42.0", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleDivisionWithCast() throws Exception {
		runCode("print 84 / 2", "simpleDivision");
		assertEquals("f=42.0", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleDiv() throws Exception {
		runCode("print 84 div 2", "simpleDivision");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleMod() throws Exception {
		runCode("print 84 mod 2", "simpleDivision");
		assertEquals("i=0", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void simpleLoop() throws Exception {
		runCode("i <- 0; do  i < 1 :: i <- i+1 od ", "simpleLoop");

	}

	@Test
	public void simpleLoopIteration() throws Exception {
		runCode("i <- 0; do  i < 10 :: i <- i+1 od ", "simpleLoop");

	}

	@Test
	public void loopZeroTimes() throws Exception {
		runCode("i <- 42; do i < 41:: print i od", "simpleLoop");
	}

	@Test
	public void simpleIntInput() throws Exception {
		DijkstraRuntime.setInputs(new String[] { "41" });
		runCode("input i; print i+1", "simpleIntInput");
		assertEquals("i=42", DijkstraRuntime.getLastMessage());
	}

	
	@Test
	public void program() throws Exception {
		String text = "i <- 0\n" + "if\n" + "  i <= 0 :: print -1\n"
				+ "  i > 0 :: print 1\n" + "fi";
		runCode(text);
	}

	@Test(expected = Exception.class)
	public void programAbort() throws Exception {
		String text = "i <- 0\n" + "if\n" + "  i < 0 :: print -1\n"
				+ "  i > 0 :: print 1\n" + "fi";
		runCode(text);
	}

	@Test
	public void testAssign() throws Exception {
		String text = "i <- false\n" + "print i";
		runCode(text);
	}

	@Test
	public void testIF() throws Exception {
		String text = "f <- 7 " + "i <- true " + "if "
				+ "f < 9 :: {i <- false } " + "fi " + "print i";
		runCode(text);
	}

	@Test
	public void fibonacci() throws Exception {
		String text = "\n" + "  int f1\n" + "  int f2\n" + "  input n\n"
				+ "  f1 <- 1 f2 <- 1 " + "  if\n" + "    n < 3 :: print 1\n"
				+ "    n > 2 :: n <- n - 2\n" + "  fi\n" + "  do\n "
				+ "    n > 0 :: {" + "      t <- f1\n" + "      f1 <- f2\n"
				+ "      f2 <- t + f1\n" + "      n <- n - 1" + "    }\n"
				+ "  od\n" + "  print f2";
		String m = "	input howMany? " + "if " + "howMany? <= 46 :: { "
				+ "f1, f2 <- 1, 1 " + "if " + "howMany? < 2 :: print 1 "
				+ "howMany? >= 2 :: {print 1; print 1} " + "fi "
				+ "counter <- 2 " + "do "
				+ "counter < howMany? :: f1, f2 <- f2, f1 + f2 "
				+ "counter < howMany? :: print f2 "
				+ "counter < howMany? :: counter <- counter + 1 " + "od "
				+ "} " + "howMany? >= 46 :: { " + "a1, a2 <- 1.0, 1.0 "
				+ "print a1 " + "print a2 " + "counter <- 2 " + "do "
				+ "counter < howMany? :: a1, a2 <- a2, a1 + a2 "
				+ "counter < howMany? :: print a2 "
				+ "counter < howMany? :: counter <- counter + 1 " + "od "
				+ "} " + "fi ";
		DijkstraRuntime.setInputs(new String[] { "5" });
		runCode(text, "fibonacci");
		assertEquals("i=5", DijkstraRuntime.getLastMessage());
	}

	@Test
	public void testLogical1() throws Exception {
		runCode("i <- 1 " + "j <- 2 " + "b <- false " + "print i <= j | b "
				+ "print i <= j & b " + "print 42 div j = 21 | b ");

	}

	@Test
	public void testIsPrime() throws Exception {

		DijkstraRuntime.setInputs(new String[] { "225" });
		runCode("input candidateNumber "
				+ "continue, isPrime <- true, false "
				+ "if "
				+ "candidateNumber < 2 :: continue <- false "
				+ "candidateNumber = 2 :: isPrime <- true "
				+ "candidateNumber = 3 :: isPrime <- true "
				+ "candidateNumber > 3 :: { "
				+ "if "
				+ "candidateNumber mod 2 = 0 :: continue <- false "
				+ "candidateNumber mod 2 ~= 0 :: continue <- true "
				+ "fi "
				+ "i <- 3 "
				+ "do "
				+ "continue & (i <= candidateNumber div 2) :: { "
				+ "if "
				+ "candidateNumber mod i = 0 :: continue <- false "
				+ "candidateNumber mod i > 0 :: i <- i + 2 "
				+ "fi "
				+ "} "
				+ "continue & (i > candidateNumber div 2) :: isPrime, continue <- true, false "
				+ "od " + "} " + "fi " + "print isPrime ");
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testArrayIndexOutOfBounds() throws Exception {
		runCode("int array[5] "
				+ "a[7] = 0");
	}
	
	@Test
	public void testCreateArrayTypes() throws Exception {
		runCode("int iArr[20], float fArr[12], boolean bArr[3]");
	}
	

	

	/********************* Helper methods ********************/
	private void makeParser(String inputText) {
		parser = DijkstraFactory.makeParser(new ANTLRInputStream(inputText));
	}

	/**
	 * This method performs the parse. If you want to see what the tree looks
	 * like, use <br>
	 * <code>System.out.println(tree.toStringTree());<code></br>
	 * after calling this method.
	 * 
	 * @param inputText
	 *            the text to parse
	 */
	private String doParse(String inputText) {
		makeParser("program Test " + inputText);
		tree = parser.dijkstraText();
		assertTrue(true);
		return tree.toStringTree(parser);
	}

	private void doAST(String inputText) {
		doParse(inputText);
		creator = new ASTCreator();
		ast = tree.accept(creator);
	}

	private void doTypeCheck(String inputText) {
		doAST(inputText);
		symbolCreator = new SymbolCreator();
		ast.accept(symbolCreator);
		TypeChecker checker = new TypeChecker();
		do {
			ast.accept(checker);
		} while (checker.checkAgain());
	}

	private void doCodeGen(String inputText) {
		doTypeCheck(inputText);
		code = ast.accept(new DijkstraCodeGenerator());
	}

	private void runCode(String inputText) throws Exception {
		runCode(inputText, null);
	}

	private void runCode(String inputText, String msg) throws Exception {
		doCodeGen(inputText);
		if (msg != null) {
			System.out.print(msg + "> ");
		}
		DijkstraCodeGeneratorTest loader = new DijkstraCodeGeneratorTest();
		Class<?> testClass = loader.defineClass("djkcode.Test", code, 0,
				code.length);

		// Run the dynamically generated class's main method.
		testClass.getMethods()[0].invoke(null, new Object[] { null });
	}

	private void writeCode(String inputText) {
		doCodeGen(inputText);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("classes/djkcode/Test.class");
			fos.write(code);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
