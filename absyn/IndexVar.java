package absyn;

public class IndexVar extends Var {
    public String name;
    public int row; 
    public int col; 
    public Exp idx;


    public IndexVar(int row, int col, String name, Exp idx) {
        this.row = row;
        this.col = col;
        this.name = name;
        this.idx = idx;
    }

    public void accept(AbsynVisitor visitor, int level) {
        visitor.visit(this, level);
    }
}
