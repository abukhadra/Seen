package seen;

public class Error {
    

//==============================================================================================
//                                        notImplementedError()
//==============================================================================================    
    public static void notImplementedError( String name ) {
    
        System.out.println( name + " : not implemented!!!" );

    }    


    
//==============================================================================================
//                                         readFileError()
//==============================================================================================    
    public static void readFileError( String file , Exception e ) {
        
        System.out.println("could not read file :  " + file );
        e.printStackTrace();
        
    }
    
    
}    

