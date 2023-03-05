import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( BoolExp exp, int level) {

  }

  
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
    System.out.println( "IntExp: " + exp.value ); 
  }

  public void visit( DecList decList, int level) {
    while(decList != null) {
      if(decList.head != null){
        decList.head.accept(this, level); 
      }
      decList = decList.tail;
    }
  }

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
    indent( level );
    System.out.println( "VarDec: " + level ); 
    visit((SimpleDec)exp, level);
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
    visit(exp.type, level);
    level++;
    indent(level);
    System.out.println("SimpleDec name: " + exp.name);
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


  /* Write compound exp handling */
  public void visit( CompoundExp exp, int level) {
    System.out.println("CompoundExp: ");

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
    }
    level++;
    if (exp.left != null)
       exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  public void visit( VarExp exp, int level ) {
    indent( level );
    System.out.println( "VarExp: " + exp.name );
  }


}
