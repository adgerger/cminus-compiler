package absyn;

public class WhileExp extends Exp {
  
  public Exp condition;
  public Exp body;

  public WhileExp( int row, int col, Exp condition, Exp body) {
    this.row = row;
    this.col = col;
    this.condition = condition;
    this.body = body;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
