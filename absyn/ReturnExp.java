package absyn;

public class ReturnExp extends Exp {
    public Exp val;
  
    public ReturnExp( int row, int col, Exp val ) {
      this.row = row;
      this.col = col;
      this.val = val;
    }
  
    public void accept( AbsynVisitor visitor, int level ) {
      visitor.visit( this, level );
    }
}
