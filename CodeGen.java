import absyn.*;

import java.io.*;
import java.util.ArrayList;

public class CodeGen {
    public DecList result; 
    public SymbolTable symtable; 
    public String filename;

    public StringBuilder codeDesc;

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
      
        this.symtable = new SymbolTable();

        codeDesc = new StringBuilder();



        visit(result, false);
  }

  public void outputCode(String code) {

    PrintWriter output = null;

    try {
      output = new PrintWriter(new FileOutputStream("test.tm", true)); //MAKE SURE TO CHANGE HARDCODED FILENAME
      output.printf(code);
      output.close();
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


  public void emitComment(String comment) {
      String output = "* " + comment + "\n";
      outputCode(output);
  } 


  public void emitOp(String op, int dest, int r, int r1, String comment) {
    String output = emitLoc + ": " + op + " " + dest + "," + r + "," + r1;
    
    outputCode(output);
    ++emitLoc;
    outputCode("\t" + comment + "\n");
  }



  public void visit( Dec dec, boolean isAddress) {
        // Check if the declaration is a variable or function declaration.
        if (dec instanceof VarDec) {
            visit((VarDec)dec, false );
        } else if (dec instanceof FunctionDec) {
            visit((FunctionDec)dec, false );
        }
    }


  public void visit( DecList decList, boolean isAddress) {

    try {
    PrintWriter writer = new PrintWriter(this.filename);
    writer.close();
    } catch (FileNotFoundException err) {
    err.printStackTrace();
    }

    // Create two NodeType's with a function declaration one for input() one for output()
    
    // Check if the program contains a main function 

    // Prelude for code generation, boolean isAddress
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
            
        while (decList != null) {
            if(decList.head != null){
                visit(decList.head, false);
            }
            decList = decList.tail;
        }

    }

    public void visit( FunctionDec exp, int level, boolean isAddress) {
       
        symtable.addNode(new NodeType(filename, exp, level));
        
        return;
    }

    public void visit( Dec dec, int level, boolean isAddress) {
        // Check if the declaration is a variable or function declaration.
        if (dec instanceof VarDec) {
            visit((VarDec)dec, level, false); //false ? 
        } else if (dec instanceof FunctionDec) {
            visit((FunctionDec)dec, level, false);
        }
    }


    public void visit( VarDec exp , int level, boolean isAddress) {
        
    }


    public void visit( SimpleDec exp, int level, boolean isAddress) {


    }

    public void visit( ArrayDec exp, int level, boolean isAddress) {


    }


    public void visit( ExpList expList, int level, boolean isAddress ) {
 
  
    }


    public void visit( AssignExp exp, int level, boolean isAddress) {    

    }


    public void visit( IfExp exp , int level, boolean isAddress) {
        
        return;
    }


    public void visit( IntExp exp, int level, boolean isAddress) {
        //System.out.println(exp.value);
    }

    public void visit( OpExp exp, int level, boolean isAddress) {

    }


    public void visit( VarExp exp, int level, boolean isAddress) {

    }


    public void visit( CompoundExp exp, int level, boolean isAddress) {

    }





    public void visit( VarDecList exp , int level, boolean isAddress) {

    }


    public void visit( SimpleVar exp, int level , boolean isAddress) {
        
    }


    public void visit( Var exp, int level, boolean isAddress ) {

    }


    public void visit( Exp exp, int level , boolean isAddress) {
        
    }


    public void visit( IndexVar exp, int level, boolean isAddress) {
        

    }


    public void visit( CallExp exp, int level, boolean isAddress) {


    }
    
    
    public void visit( WhileExp exp, int level, boolean isAddress ) {

        return;
    }

    
    public void visit( NilExp exp, int level, boolean isAddress ) {

    }


    public void visit( ReturnExp exp, int level, boolean isAddress) {
        

    }



}