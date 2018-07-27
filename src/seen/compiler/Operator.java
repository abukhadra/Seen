package seen.compiler;

public class Operator {
    
    
    private final String            symbol;
    private final int               precedence;
    private final Associativity     associativity;
    private final int               arity;    
    
//==============================================================================================
//                                         enum Associativity
//==============================================================================================    
    public enum Associativity {
    	
        RIGHT    ,
        LEFT
        
    }    
    
//==============================================================================================
//                                         builder pattern
//==============================================================================================    
    public static class    Builder {        
        
        private String          symbol;
        private int             precedence;
        private Associativity   associativity;
        private int             arity;            
        
        public Operator build() {
            return new Operator( this );
        }    
    
        public     Builder    setSymbol( String symbol )                 { this.symbol         = symbol;                                     return this; }
        public     Builder    setPrecedence( String precedence )         { this.precedence     = Integer.valueOf( precedence );              return this; }
        public     Builder    setAssociativity( String associativity )   { this.associativity  = Associativity.valueOf( associativity );     return this; }
        public     Builder    setArity( String arity )                   { this.arity          = Integer.valueOf( arity );                   return this; }
        
    }
    
    public     static    Builder builder() { return new Builder(); }
    
//==============================================================================================
//                                         constructor
//==============================================================================================        
    private Operator( Builder builder ) {
    	
        this.symbol         = builder.symbol;
        this.precedence     = builder.precedence;
        this.associativity  = builder.associativity;
        this.arity          = builder.arity;
        
    }
    
    

//==============================================================================================
//                                         getters
//==============================================================================================    
    public    String          getSymbol()           {    return this.symbol;           }    
    public    int             getPrecedence()       {    return this.precedence;       }        
    public    int             getArity()            {    return this.arity;            }
    public    Associativity   getAssociativity()    {    return this.associativity;    }    
    
    
}
