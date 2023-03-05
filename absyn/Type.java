package absyn;

public class Type extends Absyn {
    public static int VOID = 0; 
    public static int INT = 1; 
    public static int BOOL = 2;
    
    public int type; 

    public Type(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
        System.out.println("Created a type integer - in the constructor right now");
    }

    public void accept( AbsynVisitor visitor, int level ) {
		
        System.out.println("Accepting a type integer - in the accept of Type right now");
        
        visitor.visit( this, level );
	}
}
