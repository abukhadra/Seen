package seen;


import            java.io.IOException;
import            java.util.Properties;

import            java.util.Arrays;
import            java.util.LinkedHashSet;

import static     seen.Error.readFileError;;


public class Main {    
    
    private static          Properties    config;
    private static final    String        CONFIG_FILE = "config.properties"; 
    
    
//==============================================================================================
//                                         static init
//==============================================================================================    

// load the config file
    static {
        
          config = new Properties();
          
          try ( var inputStream = Main.class.getResourceAsStream( CONFIG_FILE ) ) {
              
              config.load( inputStream );
              
          } catch( IOException e ) {                  
              readFileError( CONFIG_FILE , e );
              System.exit( 0 );
              
          }
          
    }    
    

//==============================================================================================
//                                         main()
//==============================================================================================
    
    public static void main( String args[] ) {        
        
        if( handleArguments( args ) ) {            
            
            seen.test.compiler.Test1.run();            
            
        }
        
    }
    


    
//==============================================================================================
//                                         handleArguments()
//==============================================================================================
    
    public static boolean handleArguments( String[] args ) {
        
        // remove duplicates
        var argsSet = new LinkedHashSet<String>( Arrays.asList( args ) );

                
        boolean valid = true;
        
        for( var arg : argsSet ) {
            
            switch( arg ) {
            
            case "-v"     :     System.out.println( "Seen Bootstrapping Compiler version :  "     +
                                                    config.getProperty("compiler.version")         );
                                System.exit(0);                            

            case "-h"     :     System.out.println( "-v\t\tprint compiler version\n"    +
                                                    "-h\t\tprint this help message\n"   );
                                System.exit(0);                                    
                            
            default        :    System.out.println( "unrecognized compiler option :  " + arg );
                                valid = false;
            }
            
            
        }
        
        return valid;
        
    }
 

    
    
}