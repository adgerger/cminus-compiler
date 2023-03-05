package absyn;

public class ArrayDec extends VarDec {
	public IntExp size;

	public ArrayDec(int row, int col, Type type, String name, IntExp size){
		this.row = row;
		this.col = col;
		this.type = type;
		this.name = name;
		this.size = size; //size of the array
	}

    public void accept( AbsynVisitor visitor, int level ) {
		visitor.visit( this, level );
	}
}