package dsl;


import java.io.*;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.ParserRuleContext;

import dsl.ast.ASTCreator;
import dsl.ast.ASTNode;
import dsl.gen.DSLCodeGenerator;
import dsl.lexparse.DSLParser;
import dsl.semantic.TypeChecker;
import dsl.symbol.SymbolCreator;
import dsl.utility.DSLFactory;
import dsl.utility.DijkstraTreeDisplayer;


public class DSLRunner {

	private static String fileContents = null;

	/**
	 * Main program to run compiler.
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		String commandLine;
		BufferedReader console = new BufferedReader
				(new InputStreamReader(System.in));

		while (true) {
			// read what the user entered
			System.out.print("dsl>");
			commandLine = console.readLine(); 
			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			} 
			else if(commandLine.equalsIgnoreCase("exit") || commandLine.equalsIgnoreCase("quit"))  {
				System.out.println("Goodbye");
				System.exit(0);
			}
			else { //run dsl statement

//				System.out.println("output: " + commandLine);
				try
				{
					fileContents = new Scanner(commandLine).useDelimiter("\\A").next();
//					fileContents = new Scanner(new File(fileName)).useDelimiter("\\A").next();
				}
				catch(Exception e)
				{
					System.out.println("Either no input file was specified or it could not be read.");
					showHelp();
					continue;
				}

				doCompile();
			}
		}
	}


	/**
	 * Prints help information
	 */
	static private void showHelp()
	{
		System.out.println("Arguments: create [playlist name] "
				+ "[artist | album | song].[similarTo | sameGenre | sameDecade](\"input string\")");
	}

	static private void doCompile()
	{
		DSLParser parser = DSLFactory.makeParser(new ANTLRInputStream(fileContents));
		ParserRuleContext tree = parser.dslText();
		ASTNode ast = tree.accept(new ASTCreator());
		ast.accept(new SymbolCreator());
		TypeChecker checker = new TypeChecker();
		do {
			ast.accept(checker);
		} while (checker.checkAgain());
		
		ast.toString();
//		DijkstraTreeDisplayer.showTree(parser, tree);
		DSLCodeGenerator generator = new DSLCodeGenerator();
		
//		if (customPackage != null) {
//			generator.setClassPackage(customPackage);
//		}
		
//		// get the class name
//		programName = ((ProgramNode)ast).programName;
		ast.accept(new DSLCodeGenerator());
//		FileOutputStream fos;
//		try {
//			fos = new FileOutputStream(outputDirectory + "/" 
//					+ customPackage + "/" + programName + ".class");
//			fos.write(code);
//			fos.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}


