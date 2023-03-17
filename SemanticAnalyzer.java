import java.util.ArrayList;
import absyn.*;


/**
 * SemanticAnalyzer
 */
public class SemanticAnalyzer {
    
    public SemanticAnalyzer(DecList parser) {
        System.out.println("Created a SemanticAnalyzer object. ");

        visit(parser);
    }
    
    
    public void visit( DecList decList) {
        
        // Create a new scope
        // do level++

        // Create two NodeType's with a function declaration one for input() one for output()
        
        // Traverse through all declarations in the program
        while (decList != null) {
            if(decList.head != null){
                visit(decList.head);
            }
            decList = decList.tail;
        }

        // Maybe: Check if the program contains a main function 
    
    }


    public void visit( Dec dec) {
        // Check if the declaration is a variable or function declaration.
        if (dec instanceof VarDec) {
            visit((VarDec)dec);
        } else if (dec instanceof FunctionDec) {
            visit((FunctionDec)dec);
        }
    }


    public void visit( VarDec exp ) {
        
        // Check if the variable declaration is a single variable or an array.
        if (exp instanceof SimpleDec) {
            visit(((SimpleDec)exp));
        } else if(exp instanceof ArrayDec) {
            visit(((ArrayDec)exp));
        }
    }


    public void visit( SimpleDec exp) {
        System.out.println("SimpleDec name: " + exp.name);

        // Get the type number of the variable exp.type.type
        // Get the name of the variable 

        if (exp.type.type == NameTy.VOID) {
            System.out.println("Error: variable" + exp.name +" defined as VOID on line "+ ((exp.row) + 1));
        }

        // check if the variable is redeclared by calling a function that checks if this variable name is defined in the current scope.

        // create a node type
        // probably: NodeType nd = new NodeType(exp.name, (Dec)exp, 0);
        // add it to the symbol table.
    }


    public void visit( ArrayDec exp) {
        System.out.println("ArrayDec name: " + exp.name);

        // Get the type number of the variable exp.type.type
        // Get the name of the variable 

        if (exp.type.type == NameTy.VOID) {
            System.out.println("Error: variable" + exp.name +" defined as VOID on line "+ ((exp.row) + 1));
        }

        // check if the variable is redeclared by calling a function that checks if this variable name is defined in the current scope.

        // create a node type
        // probably: NodeType nd = new NodeType(exp.name, (Dec)exp, level);
        // add it to the symbol table.
    }




    public void visit( ExpList expList ) {
        while( expList != null ) {
            if (expList.head != null) {
                visit(expList.head);
            }
            expList = expList.tail;
    } 
  }


    public void visit( AssignExp exp) {    
        //exp.lhs.accept( this, level );
        visit(exp.lhs);
        //exp.rhs.accept( this, level );
        visit(exp.rhs);
    }


    public void visit( IfExp exp ) {
        //exp.test.accept( this, level );
        visit(exp.test);
        //exp.thenpart.accept( this, level );
        visit(exp.thenpart);
        if (exp.elsepart != null )
        //exp.elsepart.accept( this, level );
        visit(exp.elsepart);
    }


    public void visit( IntExp exp) {
        System.out.println(exp.value);
    }


    public void visit( OpExp exp) {
        visit(exp.left);
        visit(exp.right);
    }


    public void visit( VarExp exp) {
        //System.out.println("VarExp: ");
        //exp.vr.accept(this, level);
        visit(exp.vr);
    }


    public void visit( CompoundExp exp) {

        visit(exp.decs);
        visit(exp.exps);

    }


    public void visit( FunctionDec exp) {
    
        visit(exp.type);

        visit(exp.param_list);
        if (exp.body != null) {
        visit(exp.body);
        }
    }


    public void visit( VarDecList exp ) {
        while(exp != null) {
        if(exp.head != null) {
            //exp.head.accept(this, level);
            visit(exp.head);
        }
        exp = exp.tail;
        }
    }


    public void visit( SimpleVar exp ) {
    }


    public void visit( Var exp ) {
        if(exp instanceof IndexVar) {
        //((IndexVar)exp).accept(this, level);
        visit(((IndexVar)exp));
        } else if(exp instanceof SimpleVar) {
            visit(((SimpleVar)exp));
        //((SimpleVar)exp).accept(this, level);
        }
    }


    public void visit( Exp exp ) {
        
    if(exp instanceof CompoundExp) {
        visit((CompoundExp)exp);
        } else if(exp instanceof WhileExp) {
        visit((WhileExp)exp);
        } else if(exp instanceof IfExp) {
        visit((IfExp)exp);
        } else if(exp instanceof AssignExp) {
        visit((AssignExp)exp);
        } else if(exp instanceof OpExp) {
        visit((OpExp)exp);
        } else if(exp instanceof CallExp) {
        visit((CallExp)exp);
        } else if(exp instanceof IntExp) {
        visit((IntExp)exp);
        } else if(exp instanceof VarExp) {
        visit((VarExp)exp);
        }

        }


    public void visit( IndexVar exp) {
        visit(exp.idx);
    }


    public void visit( CallExp exp) {
        visit(exp.args); /* Print arguments */
    }
    
    
    public void visit( WhileExp exp ) {
        visit(exp.condition);
        visit(exp.body);
    }

    
    public void visit( NilExp exp ) {

    }


    public void visit( ReturnExp exp) {
        if (exp.val != null) {
        visit(exp.val);
        }
    }


}








