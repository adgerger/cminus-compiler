package absyn;

public class VarExp extends Exp {
  public Var vr;

  public VarExp( int row, int col, Var vr ) {
    this.row = row;
    this.col = col;
    this.vr = vr;
    System.out.println("We are declaring an expression with var " + vr);
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }

}
