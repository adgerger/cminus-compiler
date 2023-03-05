import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  /* Defined in the sample program */
  
  public void visit( ExpList expList, int level ) {
    while( expList != null ) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

/* Previous declaration
  public void visit( AssignExp exp, int level ) {
    indent( level );
    System.out.println( "AssignExp:" );
    level++;
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );
  }
 */

 public void visit( AssignExp exp, int level ) {
  indent(level);
  System.out.println("AssignExp: ");
  level++;
  visit(exp.lhs, level);
  level++;
  indent(level);
  System.out.println(" = ");
  visit(exp.rhs, level);
 }

  public void visit( IfExp exp, int level ) {
    indent( level );
    System.out.println( "IfExp:" );
    level++;
    exp.test.accept( this, level );
    exp.thenpart.accept( this, level );
    if (exp.elsepart != null )
       exp.elsepart.accept( this, level );
  }


  public void visit( IntExp exp, int level ) {
    indent( level );
    System.out.println( "IntExp: "); 
    level++;
    indent( level );
    System.out.println(exp.value);
  }


  public void visit( OpExp exp, int level ) {
    indent( level );
    System.out.print( "OpExp:" ); 
    switch( exp.op ) {
      case OpExp.PLUS:
        System.out.println( " + " );
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        break;
      case OpExp.TIMES:
        System.out.println( " * " );
        break;
      case OpExp.OVER:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        break;
      case OpExp.UMINUS:
        System.out.println( " - " );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
    }
    level++;
    if (exp.left != null)
       exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  // made some changes
  public void visit( VarExp exp, int level ) {
    indent(level);
    level++;
    System.out.println("VarExp: ");
    visit(exp.vr, level);
  }




  /* Our functions */


  public void visit( ArrayDec exp, int level ) {
    indent( level );
    level++;

    System.out.println("ArrDec: ");
    
    visit(exp.type, level);
    
    indent( level );

    System.out.println("Array Name: " + exp.name);
    if(exp.size != null) {
      visit(exp.size, level); //print the size of the array
    }
  }


  public void visit( VarDec exp, int level ) {

    if(exp instanceof SingleDec) {
      visit((SingleDec)exp, level);
    } else if(exp instanceof ArrayDec) {
      visit((ArrayDec)exp, level);
    }
  }


  public void visit( NameTy exp, int level ) {
    if(exp.type == NameTy.INT) { 
      System.out.println("Type: INTEGER");
    } else if (exp.type == NameTy.BOOL) {
      System.out.println("Type: BOOL");
    } else {
      System.out.println("Type: VOID");
    }
  }


  public void visit( SingleDec exp, int level ) {
    indent(level);
    System.out.println("SimpleDec: ");
    indent(level);
    visit(exp.type, level);
    level++;
    indent(level);
    System.out.println("SimpleDec name: " + exp.name);
  }

  /* Function body */
  public void visit( CompoundExp exp, int level) {
    indent( level );
    System.out.println("CompoundExp: ");
    level++;
    visit(exp.decs, level); // print variable declarations
    visit(exp.exps, level); // print other expressions
  }

  /* Make changes to stepping of the levels */
  public void visit(FunctionDec exp, int level) {
    indent( level );
    System.out.println("FunctionDec: ");
    level++;
    visit(exp.type, level);
    indent( level );
    System.out.println("Function: " + exp.function);
    visit(exp.param_list, level);
    visit(exp.body, level); 
  }


  public void visit( DecList decList, int level) {
    while(decList != null) {
      if(decList.head != null){
        decList.head.accept(this, level); 
      }
      decList = decList.tail;
    }
  }

  /* Make changes */
  public void visit( VarDecList exp, int level ) {
    while(exp != null) {
      if(exp.head != null) {
        visit(exp.head, level);
      }
      exp = exp.tail;
    }
  }

  public void visit( SimpleVar exp, int level ) {
    indent( level );
    System.out.println( "SimpleVar: ");
    level++;
    indent( level );
    System.out.println("SimpleVar name: " + exp.name);
  }


  // Variable
  public void visit( Var exp, int level ) {
    if(exp instanceof IndexVar) {
      visit((IndexVar)exp, level);
    } else if(exp instanceof SimpleVar) {
      visit((SimpleVar)exp, level);
    }
  }

  // Expression
  public void visit( Exp exp, int level ) {
    
    if(exp instanceof VarExp) {
      visit((VarExp)exp, level);
    } else if (exp instanceof AssignExp) {
      visit((AssignExp)exp, level);
    }  else if(exp instanceof IntExp) {
      visit((IntExp)exp, level);
    }

  }

  public void visit( IndexVar exp, int level ) {
    indent(level);
    System.out.println("IndexVar: ");
    level++;
    indent(level);
    System.out.println("IndexVarName: " + exp.name);
    visit(exp.idx, level);
  }






}
