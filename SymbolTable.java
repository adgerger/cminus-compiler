
import absyn.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;





/**
 * SymbolTable
 */
public class SymbolTable {

    private HashMap<String, ArrayList<NodeType>>table;
    
    public StringBuilder tableToString; // either change visibilty or remove getters and setters
    
    final static int SPACES = 4;

    public SymbolTable() {
    
        table = new HashMap<String, ArrayList<NodeType>>();
        
        tableToString = new StringBuilder();

        tableToString.append("The symbol table:\n");
        tableToString.append("Entering the global scope:\n");

        
    }

    public void indent(int level) {
        for( int i = 0; i < level * SPACES; i++ ) {
            tableToString.append(" ");   
        }
    }


    /*
     * Adds the table description with the given scope
     * and current level (tracked with level in SemanticAnalyzer)
     * to the tableToString string that contains Symbol Table info.
     */
    public void printScope(int level, int current_scope) {
        table.values().stream()
            .map(decs -> decs.get(decs.size() - 1))
            .filter(node -> node.level == current_scope)
            .forEach(tmp -> {
                indent(level + 1);
                tableToString.append(tmp.name + ": " + NodeToString(tmp) + "\n");
            });
    }


    /*
     * Converts a node in to it's string description.
     */
    private String NodeToString(NodeType nd) {
        if (nd.def instanceof SimpleDec) {
            SimpleDec e = (SimpleDec) nd.def;
            return getNameType(e.type.type);
        } else if (nd.def instanceof ArrayDec) {
            ArrayDec e = (ArrayDec) nd.def;
            return getNameType(e.type.type) + "[" + (e.size == null ? "" : e.size.value) + "]";
        } else if (nd.def instanceof FunctionDec) {
            FunctionDec e = (FunctionDec) nd.def;
            if (e.param_list == null) return "() -> " + getNameType(e.type.type);
            return "() -> " + getNameType(e.type.type);
            //return "(" + String.join(", ", e.param_list) + ") -> " + getNameType(e.type.type);
        }

        return "NoNode";
    }



    // Get the type
    private String getNameType(int type) {
        if(type == NameTy.VOID) {
            return "VOID";
        } else if (type == NameTy.BOOL){
            return "BOOL";
        } else {
            return "INT";
        }
    }

    /*
     * Adds the Node given to the list.
     */
    public void addNode(NodeType node) {
        ArrayList<NodeType> nodeList = table.get(node.name);
        if (nodeList == null) {
            nodeList = new ArrayList<>();
            table.put(node.name, nodeList);
        }
        nodeList.add(node);
      }

    /*
     * Deletes all the nodes at the given scope.
     */
    public void deleteScope(int scope) {
        Set<String> toRemove = new HashSet<>();
        
        for (Map.Entry<String, ArrayList<NodeType>> entry : table.entrySet()) {

            ArrayList<NodeType> tmp = entry.getValue();
            int lastIndex = tmp.size() - 1;
            NodeType last = tmp.get(lastIndex);
            
            if (last.level == scope) {
                tmp.remove(lastIndex);
                if (tmp.isEmpty()) {
                    toRemove.add(entry.getKey());
                }
            }
        }

        table.keySet().removeAll(toRemove);
    }

    /*
     * Checks if the variable has been declared before
     * Takes an argument, which represented by the variable type:
     *   0: for SimpleDec 
     *   1: for Array
     *   2: for Function
     * Takes another argument String id, which is the name of the
     * declaration to be checked.
     */
    public boolean isDeclared(String id, int type) {
        NodeType tmp = getNode(id);
        if (tmp != null) {
            switch (type) {
                case 0:
                    return ((Dec)tmp.def instanceof SimpleDec);
                case 1:
                    return ((Dec)tmp.def instanceof ArrayDec);
                case 2:
                    return ((Dec)tmp.def instanceof FunctionDec);
                default:
                    break;
            }
        }
        return false;
    }


    /*
     * If the variable is in the same scope as another variable
     * returns true, otherwise returns false.
     */
    public boolean isInSameScope(String name, int scope) {
        NodeType tmp = getNode(name);

        if (tmp != null) {
            if (tmp.level == scope) {
                return true;
            }
        }

        return false; 
    }

    /*
     * Returns the node with the given name. 
     * If there aren't any returns null.
     */
    private NodeType getNode(String id) {
        ArrayList<NodeType> list = table.get(id);
        if (list != null && !list.isEmpty()) {
            return list.get(list.size() - 1);
        }
        return null;
      }

    
}
