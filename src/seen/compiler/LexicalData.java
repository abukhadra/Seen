package seen.compiler;

import static   seen.compiler.Category.OPERATOR;
import static   seen.compiler.Category.SEPARATOR;

import          java.io.IOException;
import          java.nio.file.Paths;
import          java.util.ArrayList;
import          java.util.HashMap;
import          java.util.List;
import          java.util.Map;
import          java.util.Scanner;


public class    LexicalData {
    
    public static final    String                   BOM                 = "\uFEFF";
    public static final    String                   LETTERS_FILE        = "./src/seen/letters.csv";
    public static final    List< String >           singleQuotes;
    public static final    List< String >           doubleQuotes;
    public static final    List< String >           dots;
    public static final    List< String >           semicolons;
    public static final    List< String >           commas;   
    public static final    List< String >           lineTerminators;
    public static final    List< String >           whitespaceChars;
    public static final    List< String >           mashreqDigits;
    public static final    List< String >           maghrebDigits;
    public static final    List< String >           digits;
    public static final    List< String >           letters;
    public static final    List< String >           lettersOrDigits;
    public static final    List< String >           separators;
    public static final    List< String >           operators;
    public static final    List< String >           keywords;
    public static final    Map< String , Category > separatorsOrOperators;

    public static enum      DigitType { MASHREQ    ,   MAGHREB }
    
    
 
//==============================================================================================
//  static init
//==============================================================================================  
    static {
 
        singleQuotes            = List.of( "’"  , "‘"  ,  "\'" );
        doubleQuotes            = List.of( "”"  , "“"  , "\"" );
        dots                    = List.of( "·"  , "۔"   ,  "." );
        semicolons              = List.of( ";"  , "؛"  );
        commas                  = List.of( "،"   , "٬"  ,   "٫"     ,   ","     );
        
        
        
        lineTerminators         = List.of( "\n" , "\r" , "\r\n" );      
        
        whitespaceChars         = new ArrayList<>( List.of( " " , "\u0009" , "\u000c" ) );
        for( var x : lineTerminators ) { whitespaceChars.add( x ); }       
        
        mashreqDigits           = List.of( "٠" , "١"  , "٢" , "٣" , "٤" , "٥"  , "٦"  , "٧" , "٨" ,  "٩"   );
        maghrebDigits           = List.of( "0" , "1" , "2" , "3" , "4" , "5" ,  "6" , "7" , "8" ,  "9"  ); 
        
        digits                  = new ArrayList<>( mashreqDigits );
        for( var x : maghrebDigits ) { digits.add( x ); }
        
        letters                 = new ArrayList<String>();          
        try (var scanner        = new Scanner( Paths.get( LETTERS_FILE ).toAbsolutePath()) ) {            
            
            scanner.useDelimiter(",");            
            
            while( scanner.hasNext() ) { 
                
                var value       = Integer.parseInt( scanner.next() );
                var c           = Character.toChars( value );                  
                letters.add( new String( c ) );
            }
            
        } catch( IOException e ) { e.printStackTrace(); System.exit( 0 ); }
         
        
        lettersOrDigits         = new ArrayList<>( letters );
        for( var x :  digits ) { lettersOrDigits.add( x ); } 
        
        keywords                = List.of(  "اختر"  ,   "طابق"  ,   "خذ"    ,   "دع"      ,    "نوع"    , 
                                            "و"   ,   "إستخدم"  ,   "مثل"   ,   "حتى"     ,    "إذا"     ,
                                            "لو"   ,   "كل"   ,   "نفذ"    ,   "إستورد"    ,    "استثتني"   ,
                                            "عطل"  ,   "ليس"  ,    "رد"                             );
        
        separators              = new ArrayList< String >( List.of( "(" , ")" , "{" , "}" , "[" , "]" , "..." , "@" , ":" ) );
        for( var x : semicolons )   {   separators.add( x );    }
        for( var x : dots )         {   separators.add( x );    }
        for( var x : commas )       {   separators.add( x );    }
        
        
        operators               = List.of(  "="     , ">"   , "<"   , "!"   , "~"   , "؟"   , ":"   , "->"  , "=="  ,  
                                            ">="    , "<="  , "!="  , "&&"  , "||"  , "++"  , "--"  , "+"   , "-"   ,  
                                            "*"     , "\\"  , "&"   , "|"   , "^"   , "%"   , "+="  , "-="  , "*="  , 
                                            "\\="   , "؟"   ,  "_"  , "->"  , ":="                                              );
        
        separatorsOrOperators   = new HashMap< String , Category >();
        for( var x : separators )    {   separatorsOrOperators.put( x , SEPARATOR );   }
        for( var x : operators )     {   separatorsOrOperators.put( x , OPERATOR );   }
       
    }

}
