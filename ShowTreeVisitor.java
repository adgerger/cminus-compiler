
/*
  Created by: Dogu Gerger and Zeynep Erdogru
  File Name: ShowTreeVisitor.java
  Checkpoint 2
*/

import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;
  public StringBuilder absTreeToString;


  public ShowTreeVisitor () {   
    absTreeToString = new StringBuilder();
    absTreeToString.append("The Abstract Syntax Tree:\n");
  }

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  private void indentForAppend( int level) {
    for( int i = 0; i < level * SPACES; i++ ) absTreeToString.append(" ");
  }

  public String getAbsTreeToString() {
    return absTreeToString.toString();
  }


  /* Expression List */
  public void visit( ExpList expList, int level ) {
    while( expList != null ) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  /* Assign Expression */
  public void visit( AssignExp exp, int level ) {
    indent( level );

    System.out.println( "AssignExp:" );
    absTreeToString.append("AssignExp:\n");

    level++;
    
    exp.lhs.accept( this, level );

    level++;
    indent(level);

    System.out.println(" = ");
    absTreeToString.append(" = ");

    exp.rhs.accept( this, level );
  }


  public void visit( IfExp exp, int level ) {
    indent( level );

    System.out.println( "IfExp:" );
    absTreeToString.append("IfExp:");
    level++;
    exp.test.accept( this, level );
    exp.thenpart.accept( this, level );
    if (exp.elsepart != null )
       exp.elsepart.accept( this, level );
  }


  public void visit( IntExp exp, int level ) {
    indent( level );

    System.out.println( "IntExp: "); 
    indentForAppend(level);
    absTreeToString.append("IntExp:\n");

    level++;
    indent( level );

    System.out.println(exp.value);
    indentForAppend(level);
    absTreeToString.append(exp.value + "\n");
  }

  /* Operation Expression */
  public void visit( OpExp exp, int level ) {
    indent( level );

    System.out.print( "OpExp:" );
    indentForAppend(level);
    absTreeToString.append("OpExp:");

    switch( exp.op ) {
      case OpExp.TIMES:
        System.out.println( " * " );
        absTreeToString.append(" * \n");
        break;
      case OpExp.PLUS:
        System.out.println( " + " );
        absTreeToString.append( " + \n");
        break;
      case OpExp.OVER:
        System.out.println( " / " );
        absTreeToString.append(" / \n");
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        absTreeToString.append(" - \n");
        break;
      case OpExp.LT:
        System.out.println( " < " );
        absTreeToString.append(" < \n" );
        break;
      case OpExp.LTE:
        System.out.println( " <= " );
        absTreeToString.append(" <= \n" );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        absTreeToString.append(" > \n");
        break;
      case OpExp.GTE:
        System.out.println( " >= " );
        absTreeToString.append( " >= \n");
        break;
      case OpExp.ASSIGN:
        System.out.println( " == " );
        absTreeToString.append(" == \n" );
        break;
      case OpExp.NEQ:
        System.out.println( " != " );
        absTreeToString.append(" != \n");
        break;
      case OpExp.UB:
        System.out.println( " ~ " );
        absTreeToString.append(" ~ \n");
        break;
      case OpExp.LOR:
        System.out.println( " || " );
        absTreeToString.append(" || \n");
        break;
      case OpExp.LAND:
        System.out.println( " && " );
        absTreeToString.append(" && \n");
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        absTreeToString.append(" = \n" );
        break;
      case OpExp.SEMI:
        System.out.println( " ; " );
        absTreeToString.append(" ; \n");
        break;
      case OpExp.LPAREN:
        System.out.println( " ( " );
        absTreeToString.append(" ( \n" );
        break;
      case OpExp.RPAREN:
        System.out.println( " ) " );
        absTreeToString.append(" ) \n" );
        break;
      case OpExp.COMMA:
        System.out.println( " , " );
        absTreeToString.append(" , \n");
        break;
      case OpExp.SQLPAREN:
        System.out.println( " [ " );
        absTreeToString.append( " [ \n" );
        break;
      case OpExp.SQRPAREN:
        System.out.println( " ] " );
        absTreeToString.append( " ] \n" );
        break;
      case OpExp.LCURLY:
        System.out.println( " { " );
        absTreeToString.append( " { \n" );
        break;
      case OpExp.RCURLY:
        System.out.println( " } " );
        absTreeToString.append( " } \n" );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
        absTreeToString.append("Unrecognized operator at line " + exp.row + " and column " + exp.col);
        break;
      }

    level++;
    if (exp.left != null){
      exp.left.accept( this, level );

    }
    exp.right.accept( this, level );
  }

  // made some changes
  public void visit( VarExp exp, int level ) {
    indent(level);
    level++;

    System.out.println("VarExp: ");
    indentForAppend(level);
    absTreeToString.append("VarExp: \n");

    exp.vr.accept(this, level);
  }


  public void visit( ArrayDec exp, int level ) {
    indent( level );
    level++;

    System.out.println("ArrDec: ");
    indentForAppend(level);
    absTreeToString.append("ArrDec: \n");

    exp.type.accept(this, level);
    
    indent( level );

    System.out.println("Array Name: " + exp.name);
    indentForAppend(level);
    absTreeToString.append("Array Name: " + exp.name +"\n");

    if(exp.size != null) {
      exp.size.accept(this, level); //print the size of the array
    }
  }


  public void visit( VarDec exp, int level ) {

    if(exp instanceof SimpleDec) {
      ((SimpleDec)exp).accept(this, level);
    } else if(exp instanceof ArrayDec) {
      ((ArrayDec)exp).accept(this, level);
    }
  }


  public void visit( NameTy exp, int level ) {
    if(exp.type == NameTy.INT) { 
      System.out.println("Type: INTEGER");
      indentForAppend(level);
      absTreeToString.append("Type: INTEGER\n");

    } else if (exp.type == NameTy.BOOL) {
      System.out.println("Type: BOOL");
      indentForAppend(level);
      absTreeToString.append("Type: BOOL\n");

    } else {
      System.out.println("Type: VOID");
      indentForAppend(level);
      absTreeToString.append("Type: VOID\n");

    }
  }


  public void visit( SimpleDec exp, int level ) {
    indent(level);

    System.out.println("SimpleDec: ");
    indentForAppend(level);
    absTreeToString.append("SimpleDec: \n");

    indent(level);
    exp.type.accept(this, level);
    level++;
    indent(level);

    System.out.println("SimpleDec name: " + exp.name);
    indentForAppend(level);
    absTreeToString.append("SimpleDec name: " + exp.name +"\n");
  }

  /* Function body */
  public void visit( CompoundExp exp, int level) {
    indent( level );

    System.out.println("CompoundExp: ");
    indentForAppend(level);
    absTreeToString.append("CompoundExp: \n");

    level++;

    visit(exp.decs, level);
    visit(exp.exps, level);
    //exp.decs.accept(this, level); // print variable declarations
    //exp.exps.accept(this, level); //print other expressions

  }


  public void visit( FunctionDec exp, int level ) {
    indent( level );

    System.out.println("FunctionDec: ");
    indentForAppend(level);
    absTreeToString.append("FunctionDec: \n");

    level++;
    visit(exp.type, level);
    indent( level );

    System.out.println("Function: " + exp.function);
    indentForAppend(level);
    absTreeToString.append("Function: " + exp.function + "\n");

    visit(exp.param_list, level);
    if (exp.body != null) {
      visit(exp.body, level);
    }
  }


  public void visit( DecList decList, int level) {
    
    while (decList != null) {
  
      if(decList.head != null){
        decList.head.accept(this, level); 
      }
  
      decList = decList.tail;
    }
  
  }


  public void visit( VarDecList exp, int level ) {
    while(exp != null) {
      if(exp.head != null) {
        exp.head.accept(this, level);
      }
      exp = exp.tail;
    }
  }


  public void visit( SimpleVar exp, int level ) {
    indent( level );

    System.out.println( "SimpleVar: ");
    indentForAppend(level);
    absTreeToString.append("SimpleVar: \n");

    level++;
    indent( level );

    System.out.println("SimpleVar name: " + exp.name);
    indentForAppend(level);
    absTreeToString.append("SimpleVar name: " + exp.name +"\n");
  }


  // Variable
  public void visit( Var exp, int level ) {
    if(exp instanceof IndexVar) {
      ((IndexVar)exp).accept(this, level);
    } else if(exp instanceof SimpleVar) {
      ((SimpleVar)exp).accept(this, level);
    }
  }


  // Expression
  public void visit( Exp exp, int level ) {
    
    if(exp instanceof CompoundExp) {
      visit((CompoundExp)exp, level);
    } else if(exp instanceof WhileExp) {
      visit((WhileExp)exp, level);
    } else if(exp instanceof IfExp) {
      visit((IfExp)exp, level);
    } else if(exp instanceof AssignExp) {
      visit((AssignExp)exp, level);
    } else if(exp instanceof OpExp) {
      visit((OpExp)exp, level);
    } else if(exp instanceof CallExp) {
      visit((CallExp)exp, level);
    } else if(exp instanceof IntExp) {
      visit((IntExp)exp, level);
    } else if(exp instanceof VarExp) {
      visit((VarExp)exp, level);
    } else {
      indent(level);
      
      System.out.println("Error on expression in the row " + exp.row + " and column " + exp.col);
      absTreeToString.append("Error on expression in the row " + exp.row + " and column " + exp.col);

    }

  }


  public void visit( IndexVar exp, int level ) {
    indent(level);

    System.out.println("IndexVar: ");
    indentForAppend(level);
    absTreeToString.append("IndexVar: ");

    level++;
    indent(level);

    System.out.println("IndexVarName: " + exp.name);
    indentForAppend(level);
    absTreeToString.append("IndexVarName: " + exp.name +"\n");

    exp.idx.accept(this, level);
  }

  /* Call Expression */
  public void visit( CallExp exp, int level ) {
    indent(level);

    System.out.println("CallExp: ");
    indentForAppend(level);
    absTreeToString.append("CallExp: ");

    level++;
    indent( level );

    System.out.println(exp.name);
    absTreeToString.append(exp.name);
    
    visit(exp.args, level); /* Print arguments */

  }
  
  
  public void visit( WhileExp exp, int level ) {
    indent( level );

    System.out.println("WhileExp: ");
    indentForAppend(level);
    absTreeToString.append("WhileExp: \n");

    level++;
    
    visit(exp.condition, level);
    
    visit(exp.body, level);

  }

  
  public void visit( NilExp exp, int level ) {
    indent( level );

    System.out.println("NilExp: ");
    absTreeToString.append("NilExp: ");

  }


  public void visit( ReturnExp exp, int level ) {

    indent( level );
    level++; 

    System.out.println("ReturnExp: ");
    absTreeToString.append("ReturnExp: ");

    if (exp.val != null) {

      exp.val.accept(this, level);

    }
    
  }


}
