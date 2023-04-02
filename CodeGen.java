import absyn.*;

import java.io.*;
import java.util.ArrayList;

public class CodeGen {
    public DecList result; 
    public SymbolTable symbolTable; 
    public String filename;

    // Special Registers
    public static final int PC = 7;
    public static final int GP = 6; // Points to top of dMem
    public static final int FP = 5; // Points to curr stackframe in dMem
    public static final int AC = 0;
    public static final int AC1 = 1;

    //Points to curr instr generating, may go back to earlier loc for backpatching
    public int emitLoc = 0;
    // Points to next available space so we can continue adding new instr
    public int highEmitLoc = 0; 
    // Points to the bottom of the global stackframe dMem
    //Main function is the last declaration in a program: that’s where we set values for “entry” and “globalOffset”.
    public int globalOffset = 0;

    // add constructor and all emitting routines
    public CodeGen(String filename, DecList result) {
        System.out.println("Code Gen is printing, inside constructor.");
        this.result = result; 
        this.filename = filename; 
        this.symbolTable = new SymbolTable();
    
        //visit(result);
  }

  public void outputCode(String code) {

    PrintWriter output = null;

    try {
      output = new PrintWriter(new FileOutputStream("test.tm", true)); //MAKE SURE TO CHANGE HARDCODED FILENAME
      output.printf(code);
      output.close();
      System.out.println("I am here testing.\n");
    } catch( FileNotFoundException err) {
      err.printStackTrace();
    }

  }


  //Routines to generate different kinds of assembly instructions


  /*  Continue - Code Emitting Routines: Slide 24 - Lecture 11 */
  public void emitRO(String op, int dest, int r, int r1, String comment) {
      //fprintf( code, “%3d: %5s %d, %d, %d”, emitLoc, op, r, s, t );
      //fprintf( code, “\t%s\n”, c );
      String output = emitLoc + ": " + op + " " + dest + "," + r + "," + r1;
      outputCode(output);

      ++emitLoc;
      if( highEmitLoc < emitLoc ) {
        highEmitLoc = emitLoc;
      }
      outputCode("\t" + comment + "\n");
  }

  public void emitRM(String op, int r, int d, int s, String comment) {
    String output = emitLoc + ": " + op + " " + r + "," + d + "(" + s + ")";

      outputCode(output);
      ++emitLoc;
      outputCode("\t" + comment + "\n");

      if(highEmitLoc < emitLoc) {
        highEmitLoc = emitLoc;
      }
  }

  public void emitRM_Abs(String op, int r, int a, String comment) {
    String output = emitLoc + ": " + op + " " + r + "," + (a - (emitLoc + 1)) + "(" + PC + ")";

    outputCode(output);
    ++emitLoc;
    outputCode("\t" + comment + "\n");

    if(highEmitLoc < emitLoc) {
      highEmitLoc = emitLoc;
    }
  }
    /*  Code Emitting Routines: Slide 26 - Lecture 11 */
    public int emitSkip(int distance) {
        int i = emitLoc;
        emitLoc += distance;

        if(highEmitLoc < emitLoc) {
          highEmitLoc = emitLoc;
        }

        return i;
  }
  public void emitBackup(int loc) {
        if(loc > highEmitLoc) {
          emitComment("BUG in emitBackup");
        }
        emitLoc = loc;
  }

  public void emitRestore() {
    emitLoc = highEmitLoc;
  }

  //Routine to generate one line of comment
  void emitComment(String comment) {
      String output = "* " + comment + "\n";
      outputCode(output);
  } 


}