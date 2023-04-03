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
    //Points to next available space so we can continue adding new instr
    public int highEmitLoc = 0; 
    // Points to the bottom of the global stackframe dMem
    //Main function is the last declaration in a program: that’s where we set values for “entry” and “globalOffset”.
    public int globalOffset = 0;

    // add constructor and all emitting routines
    public CodeGen(String filename, DecList result) {
        this.result = result; 
        this.filename = filename; 
        this.symbolTable = new SymbolTable();
    
        visit(result);
  }

  public void outputCode(String code) {

    PrintWriter output = null;

    try {
      output = new PrintWriter(new FileOutputStream("test.tm", true)); //MAKE SURE TO CHANGE HARDCODED FILENAME
      output.printf(code);
      output.close();
      System.out.println("I am testing Code Gen.\n");
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

  public void emitOp(String op, int dest, int r, int r1, String comment) {
    String output = emitLoc + ": " + op + " " + dest + "," + r + "," + r1;
    
    outputCode(output);
    ++emitLoc;
    outputCode("\t" + comment + "\n");
  }

  public void visit( Dec dec) {
        // Check if the declaration is a variable or function declaration.
        if (dec instanceof VarDec) {
            visit((VarDec)dec);
        } else if (dec instanceof FunctionDec) {
            visit((FunctionDec)dec);
        }
    }




  public void visit( DecList decList) {

        try {
          PrintWriter writer = new PrintWriter(this.filename);
          writer.close();
        } catch (FileNotFoundException err) {
          err.printStackTrace();
        }
        
        // Create a new scope
        // do current_scope++ ?

        // Create two NodeType's with a function declaration one for input() one for output()
      

        // Maybe: Check if the program contains a main function 
      
        // Prelude for code generation
        emitComment("Standard prelude:");
        emitRM("LD  ", GP, 0, AC, " load gp with maxaddress");
        emitRM("LDA ", FP, 0, GP, " copy to gp to fp");
        emitRM("ST  ", 0, 0, 0, " clear location 0");

        int savedLoc = emitSkip(1);

        emitComment("Jump around I/O functions");
        emitComment("Code for Input Routine");
        emitRM("ST", 0, -1, FP, "Store return");
        emitOp("IN", 0, 0, 0, "Input");
        emitRM("LD", PC, -1, FP, "Return caller");

        emitComment("Code for Output Routine");
        emitRM("ST", 0, -1, FP, "Store return");
        emitRM("LD", 0, -2, FP, "Load output value");
        emitOp("OUT", 0, 0, 0, "Output");
        emitRM("LD", 7, -1, FP, "Return caller");

        // Jump around I/O
        int savedLoc2 = emitSkip(0);
        emitBackup(savedLoc);
        emitRM_Abs("LDA", PC, savedLoc2, "Jump around I/O code");
        emitRestore();
        emitComment("End of standard prelude.");
          /*
         // Traverse through all declarations in the program
        while (decList != null) {
            if(decList.head != null){
                visit(decList.head);
            }
            decList = decList.tail;
        }

        emitComment("Finale Generation");
        emitRM("ST", FP, globalOffset, FP, "Push Old Frame Pointer");
        emitRM("LDA", FP, globalOffset, FP, "Push frame");
        emitRM("LDA", 0, 1, PC, "Load AC with return pointer");
        //emitRM_Abs("LDA", PC, adr.address, "Jump to main location");
        emitRM("LD", FP, 0, FP, "Pop frame");
        emitOp("HALT", 0, 0, 0, "HALT");
        */


    }





}