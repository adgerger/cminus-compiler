package absyn;

public class NodeType {
    public int level;
    public String name;
    public Dec def;

  public NodeType( String name, Dec def, int level ) {
    this.name = name;
    this.def = def;
    this.level = level;
  }
  public String getNodeName(){
    return name;
  }

  public int getLevel() {
    return level;
  }

}
