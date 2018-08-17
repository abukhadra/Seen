package seen.compiler;


import              java.util.ArrayList;
import             java.util.HashMap;
      


public class Compiler {
        
    
//==============================================================================================
//                                     run()
//==============================================================================================    
    public    void run( String source ) {
    	
        ArrayList< Token >  tokens      = Lexer.run( source ); 
        
        tokens.forEach( t -> System.out.println( t ) );
        
        SyntaxTree  syntaxTree          = Parser.run( tokens );
        
        

    }
    

}
