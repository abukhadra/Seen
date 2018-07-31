package seen.compiler;

import static seen.Error.readFileError;

import         java.io.IOException;
import         java.nio.file.Paths;
import         java.util.ArrayList;
import         java.util.Scanner;


public class Lexer {

    
    public static    ArrayList< Token >    run( String sourceFile ) {
        
        var tokens  = new ArrayList< Token >();        
        var path    = Paths.get( sourceFile ).toAbsolutePath();
                
        try( var scanner  = new Scanner( path ) ) {          

            
            while( scanner.hasNext() ) {
                
            }
            
        } catch( IOException e ) {    readFileError( sourceFile , e );     }
        
        return tokens;
        
    }
    
}
