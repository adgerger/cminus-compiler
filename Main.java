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

  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      
      SHOW_TREE  = List.of(argv).contains("-a") ? true : false;
      SHOW_TABLE  = List.of(argv).contains("-s") ? true : false;
      
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);
      
      if (SHOW_TREE && result != null) {
         System.out.println("The abstract syntax tree is:");
         ShowTreeVisitor visitor = new ShowTreeVisitor();
         result.accept(visitor, 0); 
      }

      if (SHOW_TABLE && result != null) {
        System.out.println("The symbol table is:");
        SemanticAnalyzer visitor = new SemanticAnalyzer((DecList)result);

      }

    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}


