
import absyn.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * SymbolTable
 */
public class SymbolTable {

    private HashMap<String, ArrayList<NodeType>>table;
    
    // in this hashmap, does the each index of the array list represent a scope in the program, and if it does, why does it map a string to a scope, isn't it supposed to map a string to a NodeType instead ? 


    public SymbolTable() {
    
        table = new HashMap<String, ArrayList<NodeType>>();
    
    }

    public void add_symbol(String id, String sym) {

    }
    
}
