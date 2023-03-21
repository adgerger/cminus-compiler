import java.util.ArrayList;
import absyn.*;


/**
 * SemanticAnalyzer
 To DO:
-Give zeynep to-do
-Mail professor song for extension tell him u will get a doctors note
-push code to git
 */
public class SemanticAnalyzer {
    
    public SymbolTable symtable;

    public int current_scope = 0;


    public SemanticAnalyzer(DecList parser) {
        
        current_scope = 0;

        symtable = new SymbolTable();
        
        visit(parser, 0);
    }

    public void printScope(int level) {
        symtable.printScope(level, current_scope);
        
    }

    public String getSymbolTableString() {

        this.printScope(0);

        symtable.tableToString.append("Leaving the global scope.");
        
        return symtable.tableToString.toString();
    }
    
    
    public void visit( DecList decList, int level) {
        
        // Create a new scope
        // do current_scope++ ?

        // Create two NodeType's with a function declaration one for input() one for output()
        
        // Traverse through all declarations in the program
        while (decList != null) {
            if(decList.head != null){
                visit(decList.head, level);
            }
            decList = decList.tail;
        }

        // Maybe: Check if the program contains a main function 
        
    }


    public void visit( Dec dec, int level) {
        // Check if the declaration is a variable or function declaration.
        if (dec instanceof VarDec) {
            visit((VarDec)dec, level);
        } else if (dec instanceof FunctionDec) {
            visit((FunctionDec)dec, level);
        }
    }


    public void visit( VarDec exp , int level) {
        
        // Check if the variable declaration is a single variable or an array.
        if (exp instanceof SimpleDec) {
            visit(((SimpleDec)exp), level);
        } else if(exp instanceof ArrayDec) {
            visit(((ArrayDec)exp), level);
        }
    }


    public void visit( SimpleDec exp, int level) {

        /* Check if a variable is declared as VOID. */
        if (exp.type.type == NameTy.VOID) {
            System.out.println("Error: variable" + exp.name +" defined as VOID on line "+ ((exp.row) + 1));
        }
        
        /* Check if the variable is redeclared. */
        if (symtable.isInSameScope(exp.name, current_scope) == true) {
			System.err.println("Error: Redeclaration of variable " + exp.name + " on line " + (exp.row + 1));
        }

        /* Add a global variable declaration */
        NodeType nd = new NodeType(exp.name, (Dec)exp, current_scope);
        symtable.addNode(nd);
    }

    public void visit( ArrayDec exp, int level) {

        /* Check if the variable is declared as VOID. */
        if (exp.type.type == NameTy.VOID) {
            System.out.println("Error: variable" + exp.name +" defined as VOID on line "+ ((exp.row) + 1));
        }
        
        /* Check if the variable is redeclared. */
        if (symtable.isInSameScope(exp.name, current_scope) == true) {
			System.err.println("Error: Redeclaration of variable " + exp.name + " on line " + (exp.row + 1));
            return;
        }

        /* Add a global variable declaration */
        NodeType nd = new NodeType(exp.name, (Dec)exp, current_scope);
        symtable.addNode(nd);
    }

    public void visit( FunctionDec exp, int level) {
       
        current_scope++; // Increase the current scope

        /* Print entry to function */
        symtable.indent(level + 1);
        symtable.tableToString.append("Entering the function scope for function " + exp.function + ":\n");

        /* Handle function parameters */
        if (exp.param_list != null) {
            visit(exp.param_list, level+1);
        }

        /* Check if the function was redeclared */
        if (symtable.isInSameScope(exp.function, 0) == true) {
			System.err.println("Error: Redeclaration of function " + exp.function + " on line " + (exp.row + 1));
        } else {
            /* Add the function to our symbol table. */
            NodeType nd = new NodeType(exp.function, (Dec)exp, current_scope-1);
            symtable.addNode(nd);
        }

        /* Handle function body. */
        if (exp.body != null) {
            visit(exp.body, level + 1);
        }
        
        printScope(level + 1);

        symtable.indent(level + 1);
        symtable.tableToString.append("Leaving the function scope\n");
        
        symtable.deleteScope(current_scope);
        current_scope = current_scope - 1;
        
        return;
    }



    public void visit( ExpList expList, int level ) {
        while( expList != null ) {
            if (expList.head != null) {
                visit(expList.head, level);
            }
            expList = expList.tail;
    } 
  }


    public void visit( AssignExp exp, int level) {    

        visit(exp.lhs, level);

        visit(exp.rhs, level);

    }


    public void visit( IfExp exp , int level) {
        //exp.test.accept( this, current_scope );
        visit(exp.test, level);
        //exp.thenpart.accept( this, current_scope );
        visit(exp.thenpart, level);
        if (exp.elsepart != null )
        //exp.elsepart.accept( this, current_scope );
        visit(exp.elsepart, level);
    }


    public void visit( IntExp exp, int level) {
        //System.out.println(exp.value);
    }


    public void visit( OpExp exp, int level) {
        visit(exp.left, level);
        visit(exp.right, level);
    }


    public void visit( VarExp exp, int level) {
        
        /* Check if the variable in the expression has been declared before usage. */
        visit(exp.vr, level);


    }


    public void visit( CompoundExp exp, int level) {
        if (exp == null) {
            return;
        }

        visit(exp.decs, level);
        
        visit(exp.exps, level);

    }





    public void visit( VarDecList exp , int level) {
        while(exp != null) {
        if(exp.head != null) {
            //exp.head.accept(this, current_scope);
            visit(exp.head, level);
        }
        exp = exp.tail;
        }
    }


    public void visit( SimpleVar exp, int level ) {
        if (symtable.isDeclared(exp.name, 0) == false) {
            System.err.println("Error: Undefined variable '" + exp.name + "' on line: " + exp.row); 
        }
        
    }


    public void visit( Var exp, int level ) {
        if(exp instanceof IndexVar) {
            visit(((IndexVar)exp), level);
        } else if(exp instanceof SimpleVar) {
            visit(((SimpleVar)exp), level);
        }
    }


    public void visit( Exp exp, int level ) {
        
    if(exp instanceof CompoundExp) {
        visit((CompoundExp)exp, level);
        } else if(exp instanceof WhileExp) {
        visit((WhileExp)exp, level);
        } else if(exp instanceof IfExp) {
        visit((IfExp)exp, level);
        } else if(exp instanceof AssignExp) {
        visit((AssignExp)exp, level);
        } else if(exp instanceof OpExp) {
        visit((OpExp)exp, level);
        } else if(exp instanceof CallExp) {
        visit((CallExp)exp, level);
        } else if(exp instanceof IntExp) {
        visit((IntExp)exp, level);
        } else if(exp instanceof VarExp) {
        visit((VarExp)exp, level);
        }

        }


    public void visit( IndexVar exp, int level) {
        visit(exp.idx, level);
    }


    public void visit( CallExp exp, int level) {
        visit(exp.args, level); /* Print arguments */
    }
    
    
    public void visit( WhileExp exp, int level ) {
        visit(exp.condition, level);
        visit(exp.body, level);
    }

    
    public void visit( NilExp exp, int level ) {

    }


    public void visit( ReturnExp exp, int level) {
        if (exp.val != null) {
        visit(exp.val, level);
        }
    }


}








