package absyn;

public class CallExp extends Exp {
    
    public ExpList args; 
    public String name; 

    public CallExp(int row, int col, String name, ExpList args) {
        this.row = row;
        this.col = col;
        this.name = name;
        this.args = args; 
    }

    public void accept( AbsynVisitor visitor, int level ) {
    
        visitor.visit( this, level );
    
    }

    public int get_num_params() {
        
        int num = 0; 
        ExpList expl = this.args; 
        
        while (expl != null) {
            expl = expl.tail; 
            num++; 
        }
        
        return num; 
    }
      
}
