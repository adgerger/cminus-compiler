/*
  Zeynep Erdogru & Dogu gerger 
  CIS*4650 Compilers Checkpoint 2
*/

   
import java.io.*;
import absyn.*;
import java.util.Arrays;
import java.util.List;

class Main {
  public static boolean SHOW_TREE = false; // Show the Abstract Syntax Tree
  public static boolean SHOW_TABLE = false; // Show the Symbol Table
  
  public static String fileName = null;
  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      decideFlag(argv);
      parser p = new parser(new Lexer(new FileReader(fileName)));
      
      SHOW_TREE  = List.of(argv).contains("-a") ? true : false;
      SHOW_TABLE  = List.of(argv).contains("-s") ? true : false;
      

      Absyn result = (Absyn)(p.parse().value);
      
      if (SHOW_TREE && result != null) {
         System.out.println("The abstract syntax tree is:");
         ShowTreeVisitor visitor = new ShowTreeVisitor();
         result.accept(visitor, 0); 
        // write to .ast

        /*for (String argument : argv) {
          if (argument.endsWith(".cm")) fileName = argument;
        }
        fileName = filter_cm(fileName, ".ast");

        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(visitor.getSymbolTableString());
        } */
      }

      if (SHOW_TABLE && result != null) {
        SemanticAnalyzer visitor = new SemanticAnalyzer((DecList)result);
        

        // write string visitor.getSymbolTableString() to a file with .st extension
        for (String argument : argv) {
          if (argument.endsWith(".cm")) fileName = argument;
        }
        fileName = filter_cm(fileName, ".st");
        
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(visitor.getSymbolTableString());
        }
        //System.out.println(visitor.getSymbolTableString());

      }

    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }

  public static void decideFlag(String argv[]) {
    for (String argument : argv) {
      if (argument.equals("-a")) {
          System.out.println("Displaying Abstract Syntax Tree:\n");
          SHOW_TREE = true;
      } else if (argument.equals("-s")) {
          System.out.println("Displaying Symbol Table:\n");
          SHOW_TABLE = true;
      } else if (argument.endsWith(".cm")){
          fileName = argument;
      } else {
          System.out.println("Error: Please enter a valid argument: {-a, -s}\n");
      }
    }
  }

  public static String filter_cm(String fileName, String extension) {
    if (fileName != null && fileName.length() > 0) {
        fileName = fileName.replaceFirst(".cm", extension);
    }
    return fileName;
  }

}


