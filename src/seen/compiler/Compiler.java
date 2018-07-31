package seen.compiler;

import             java.io.IOException;
import             java.io.InputStream;
import             java.util.Map;
import java.util.ArrayList;
import             java.util.HashMap;
import            java.util.Scanner;
import static    seen.Error.readFileError;      


public class Compiler {
    
    private static final    String                         OPERATORS_FILE = "data/operators.txt";
    private static final    HashMap<String , Operator>     operators;
    
    
//==============================================================================================
//                                          static init          
//==============================================================================================
//     load operators data from operators.txt
    static {
        
        operators = new HashMap<String, Operator >();
        
        try ( var inputStream = seen.Main.class.getResourceAsStream( OPERATORS_FILE ) ) {
        
            var scanner = new Scanner( inputStream , "UTF-8" );
            scanner.useDelimiter( "\\s+" );
            scanner.nextLine();                // skip table header;
            
            while( scanner.hasNext() ) {
                
                
                String symbol = scanner.next();
                            
                if( symbol.startsWith( "##" ) ) {               // use ## to comment entries in the data files
                    
                    scanner.nextLine();
                    
                } else {
                    
                    Operator operator = Operator.builder()
                                                .setSymbol( symbol )
                                                .setPrecedence( scanner.next() )
                                                .setAssociativity( scanner.next() )
                                                .setArity( scanner.next() )
                                                .build();
                    
                    operators.put( operator.getSymbol() , operator );                
                }
    
            }
        
        } catch( IOException e ) {         	
            readFileError( OPERATORS_FILE , e );                          
            System.exit( 0 );    
        }

    }    
    
//==============================================================================================
//                                     run()
//==============================================================================================    
    public    void run( String source ) {
    	
        ArrayList< Token >  tokens      = Lexer.run( source );       
        SyntaxTree          syntaxTree  = Parser.run( tokens );
        
        

    }
    

}
