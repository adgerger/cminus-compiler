package absyn;

public class OpExp extends Exp {
  public final static int PLUS   = 0;
  public final static int MINUS  = 1;
  public final static int TIMES  = 2;
  public final static int OVER   = 3;
  public final static int LT     = 4;
  public final static int LTE     = 5;
  public final static int GT     = 6;
  public final static int GTE     = 7;
  public final static int ASSIGN    = 8;
  public final static int NEQ     = 9;
  public final static int UB     = 10;
  public final static int LOR     = 11;
  public final static int LAND     = 12;
  public final static int EQ     = 13;
  public final static int SEMI = 14;
  public final static int LPAREN = 15;
  public final static int RPAREN = 16;
  public final static int COMMA = 17;
  public final static int SQLPAREN = 18;
  public final static int SQRPAREN = 19;
  public final static int LCURLY = 20;
  public final static int RCURLY = 21;

  public Exp left;
  public int op;
  public Exp right;

  public OpExp( int row, int col, Exp left, int op, Exp right ) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.op = op;
    this.right = right;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

