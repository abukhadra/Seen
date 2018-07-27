package seen.compiler;

import static seen.Error.readFileError;

import         java.io.IOException;
import         java.nio.file.Paths;
import         java.util.ArrayList;
import         java.util.Scanner;


public class Lexer {

    
    public static    ArrayList< Token >    run( String sourceFile ) {
        
        var tokens = new ArrayList< Token >();
        
        try{
           
            var path     = Paths.get( sourceFile ).toAbsolutePath();            
            var scanner  = new Scanner( path );            
                        
        } catch( IOException e ) {    readFileError( sourceFile , e );     }
        
        return tokens;
        
    }
    
}
