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

  public void visit( AssignExp exp, int level ) {
    indent( level );
    System.out.println( "AssignExp:" );
    level++;
    
    exp.lhs.accept( this, level );

    level++;
    indent(level);
    System.out.println(" = ");

    exp.rhs.accept( this, level );
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
      case OpExp.OVER:
        System.out.println( " / " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.LTE:
        System.out.println( " <= " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        break;
      case OpExp.GTE:
        System.out.println( " >= " );
        break;
      case OpExp.ASSIGN:
        System.out.println( " == " );
        break;
      case OpExp.NEQ:
        System.out.println( " != " );
        break;
      case OpExp.UB:
        System.out.println( " ~ " );
        break;
      case OpExp.LOR:
        System.out.println( " || " );
        break;
      case OpExp.LAND:
        System.out.println( " && " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.SEMI:
        System.out.println( " ; " );
        break;
      case OpExp.LPAREN:
        System.out.println( " ( " );
        break;
      case OpExp.RPAREN:
        System.out.println( " ) " );
        break;
      case OpExp.COMMA:
        System.out.println( " , " );
        break;
      case OpExp.SQLPAREN:
        System.out.println( " [ " );
        break;
      case OpExp.SQRPAREN:
        System.out.println( " ] " );
        break;
      case OpExp.LCURLY:
        System.out.println( " { " );
        break;
      case OpExp.RCURLY:
        System.out.println( " } " );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
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
    exp.vr.accept(this, level);
  }




  /* Our functions */


  public void visit( ArrayDec exp, int level ) {
    indent( level );
    level++;

    System.out.println("ArrDec: ");

    exp.type.accept(this, level);
    
    indent( level );

    System.out.println("Array Name: " + exp.name);
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
    } else if (exp.type == NameTy.BOOL) {
      System.out.println("Type: BOOL");
    } else {
      System.out.println("Type: VOID");
    }
  }


  public void visit( SimpleDec exp, int level ) {
    indent(level);
    System.out.println("SimpleDec: ");
    indent(level);
    exp.type.accept(this, level);
    level++;
    indent(level);
    System.out.println("SimpleDec name: " + exp.name);
  }

  /* Function body */
  public void visit( CompoundExp exp, int level) {
    indent( level );
    System.out.println("CompoundExp: ");
    level++;

    visit(exp.decs, level);
    visit(exp.exps, level);
    //exp.decs.accept(this, level); // print variable declarations
    //exp.exps.accept(this, level); //print other expressions

  }

  /* Make changes to stepping of the levels */
  public void visit(FunctionDec exp, int level) {
    indent( level );
    System.out.println("FunctionDec: ");
    level++;

    exp.type.accept(this, level);
    indent( level );

    visit(exp.param_list, level);

    exp.body.accept(this, level);
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
    level++;
    indent( level );
    System.out.println("SimpleVar name: " + exp.name);
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
    
    if(exp instanceof VarExp) {
      ((VarExp)exp).accept(this, level);

    } else if (exp instanceof AssignExp) {
      ((AssignExp)exp).accept(this, level);

    }  else if(exp instanceof IntExp) {
      ((IntExp)exp).accept(this, level);

    }  else if(exp instanceof CallExp) {
      ((CallExp)exp).accept(this, level);

    }

  }

  public void visit( IndexVar exp, int level ) {
    indent(level);
    System.out.println("IndexVar: ");
    level++;
    indent(level);
    System.out.println("IndexVarName: " + exp.name);
    exp.idx.accept(this, level);
  }


  public void visit( CallExp exp, int level ) {
    indent(level);
    System.out.println("CallExp: ");
    
    /* Print function name */
    level++;
    indent( level );
    System.out.println(exp.name);
    
    
    visit(exp.args, level); /* Print arguments */

  }




}
