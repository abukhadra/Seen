package         seen.tester;

import          java.util.List;

import          seen.compiler.Category;
import          seen.compiler.Lexer;

public class    LexerTest {


    private static final    int             LINE_FIELD_WIDTH        = 28;
    private static final    int             CATEGORY_FIELD_WIDTH    = 28;


    private static          String          fixWidth( String string , int width ) {
    
    if( string.length() == width ) {return string;}
    
    
    StringBuilder stringBuilder= new StringBuilder( string );
    
    for( int i = 0 ; i < width - string.length()  ; i++ ) {     stringBuilder.append( " " );    }
    
    return stringBuilder.toString();
    
    }

//==============================================================================================
//  run()
//==============================================================================================    
    public static           String          run( String source ) {
        
        var stringBuilder= new StringBuilder();
        var tokens= Lexer.exec( List.of( source ) );
        
        stringBuilder.append( fixWidth( "الموقع" , LINE_FIELD_WIDTH ) );
        stringBuilder.append( fixWidth( "فئة القطعة" , CATEGORY_FIELD_WIDTH ) );
        stringBuilder.append( "قيمة القطعة" );
        stringBuilder.append( "\n( سطر ، عمود )" );
        stringBuilder.append( "\n---------------------------------------------------------------------\n" );
        
        
        
        for( var token:tokens ) {
            
                var location    = fixWidth( token.getLocation().toString() , LINE_FIELD_WIDTH );       
                stringBuilder.append( location );
                        
                
        
                var category    = fixWidth( token.getCategory().toString() , CATEGORY_FIELD_WIDTH );
                stringBuilder.append( category );
                
                
                var value       = token.getCategory() == Category.LINE_TERMINATOR ? "" : token.getValue();
                stringBuilder.append( value );
                
                stringBuilder.append("\n");
        
        }
        
        return   stringBuilder.toString();
        
    }

}


