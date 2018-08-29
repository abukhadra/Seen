package             seen.util;

public class        Number {
    
    
    
//==============================================================================================
//  toMashreqDigit()
//==============================================================================================       
    public static          String          toMashreqDigit( String digit ) {
        
        String result = null;
        
        switch( digit ) {
        
        case "0"    : result = "٠";    break;
        case "1"    : result = "١";    break;
        case "2"    : result = "٢";    break;
        case "3"    : result = "٣";    break;
        case "4"    : result = "٤";    break;
        case "5"    : result = "٥";    break;
        case "6"    : result = "٦";    break;
        case "7"    : result = "٧";    break;
        case "8"    : result = "٨";    break;
        case "9"    : result = "٩";    break; 
        default     : System.err.println( "toMashreqDigit : unexpected value!!!"); System.exit(0);
            
        }
        
        return result;
        
    }
    
    
    
//==============================================================================================
//  toMashreqDigit()
//==============================================================================================       
    public static          String           toMaghrebDigit( String digit ) {
        
        String result = null;
        
        switch( digit ) {
        
        case "٠"    : result = "0";    break;
        case "١"    : result = "1";    break;
        case "٢"    : result = "2";    break;
        case "٣"    : result = "3";    break;
        case "٤"    : result = "4";    break;
        case "٥"    : result = "5";    break;
        case "٦"    : result = "6";    break;
        case "٧"    : result = "7";    break;
        case "٨"    : result = "8";    break;
        case "٩"    : result = "9";    break;   
        default     : System.err.println( "toMagrebDigit : unexpected value!!!"); System.exit(0);
        
        }
        
        return result;
        
    }
    
    
    
//==============================================================================================
//  toMashreqNumber()
//==============================================================================================       
    public static           String      toMashreqNumber( String number ) { 
        
        String result = "";
        
        for( int i = 0 ; i < number.length() ; i++ ) {  result += toMashreqDigit( number.substring(  i , i + 1 ) );  }
        
        return result;
        
    }    
    

    
//==============================================================================================
//  toMaghrebNumber()
//==============================================================================================       
    public static           String      toMagrebNumber( String number ) {

        String result = "";
        
        for( int i = 0 ; i < number.length() ; i++ ) {  result += toMaghrebDigit( number.substring(  i , i + 1 ) );  }
        
        return result;
        
        
    }

}
