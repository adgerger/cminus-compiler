package absyn;

public class NameTy extends Absyn {
    public static int VOID = 0; 
    public static int INT = 1; 
    public static int BOOL = 2; 
    
    public int type; 

    public NameTy(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public void accept( AbsynVisitor visitor, int level ) {
		visitor.visit( this, level );
	}
}

