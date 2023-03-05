package absyn;

public class BoolExp extends Exp {
    
    public boolean value;
    public int pos;

    public BoolExp(int pos, boolean value) {
        this.pos = pos;
        this.value = value;
    }

    public void accept ( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}