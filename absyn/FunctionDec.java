

package absyn;

public class FunctionDec extends Dec {
  public NameTy type; 
  public String function; 
  public VarDecList param_list; 
  public CompoundExp body; 

  public FunctionDec( int row, int col, NameTy type, String function, VarDecList param_list, CompoundExp body ) {
    this.row = row;
    this.col = col;
    this.type = type;
    this.function = function; 
    this.param_list = param_list;
    this.body = body;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }

  public int get_param_count() {
    VarDecList exp = param_list;
    int num=0;

    while(exp != null) {
      num++;
      exp = exp.tail;
    }

    return num;
  }
}




