package         seen;


import          java.io.IOException;
import          java.util.Properties;
import          java.util.ArrayList;
import          java.util.Arrays;
import          java.util.LinkedHashSet;
import          java.util.List;

import static   seen.Error.readFileError;


public class    Main {    
    
    private static          Properties      config;
    private static final    String          CONFIG_FILE     = "config.properties"; 
    private static final    String          HELP_MESSAGE    =  "Usage java -jar seen.jar <options> <source files>\n"        +
                                                                "where source files have extension .seen\n"                 +
                                                                "and options are : \n"                                      +                                                        
                                                                "  -v\t\tprint compiler version\n"                          +                                                    
                                                                "  -h\t\tprint this help message\n"                         ;       
    
    
//==============================================================================================
//  static init
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
//  main()
//==============================================================================================
    public static           void                    main( String    args[] ) {      
               
        var source = handleArguments( args );            
        Build.exec( source );
        
            
        }
           
//==============================================================================================
//  handleArguments()
//==============================================================================================
    public static           List< String >          handleArguments( String[]   args ) {
        
        // remove duplicates
        var argsSet = new LinkedHashSet<String>( Arrays.asList( args ) );
        var source  = new ArrayList< String >();

        boolean valid = true;
        
        for( var arg : argsSet ) {
            
            switch( arg ) {
            
            case "-v"     :     System.out.println( "Seen Bootstrapping Compiler version :  "     +
                                                    config.getProperty( "compiler.version" )         );
                                System.exit( 0 );                            

            case "-h"     :     System.out.println( HELP_MESSAGE );
                                System.exit( 0 );                                    
                            
            default        :    if( arg.endsWith( ".seen" ) || arg.endsWith( ".س" ) ) {     
                
                                    source.add( arg );     
                                    
                                }   else {     
                                    
                                    System.out.println( "unrecognized compiler option :  " + arg );
                                    System.exit( 0 );
                                    
                                }                                
            }
        }
        
        if( source.isEmpty() ) {        System.out.println( HELP_MESSAGE );     System.exit( 0 );        }
        
        return source;
        
    }


}