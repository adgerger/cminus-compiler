package absyn;

public class NodeType {
    int level;
    String name;
    Dec def;

  public NodeType( String name, Dec def, int level ) {
    this.name = name;
    this.def = def;
    this.level = level;
  }

  public void accept(AbsynVisitor visitor, int level) {
        visitor.visit(this, level);
    }
}
