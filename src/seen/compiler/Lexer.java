package         seen.compiler;

import          java.io.IOException;

import          java.nio.file.Paths;

import          java.util.ArrayList;
import          java.util.Locale;
import          java.util.Scanner;
import          java.util.HashSet;
import          java.util.List;

import static   seen.compiler.LexicalData.*;
import static   seen.compiler.Category.*;
import static   seen.compiler.LexicalData.DigitType.*;
import static   seen.Error.readFileError;

public class    Lexer {
    
    private static          ArrayList< String >     errorTable;
    private static          String                  buffer;
    private static          String                  lookaheadValue;
    private static          Location                startLocation;
    private static          Location                endLocation;
    private static          Scanner                 scanner;
    private static          ArrayList< Token >      tokens;

    
//==============================================================================================
//  constructor
//==============================================================================================    
    private                         Lexer() {}

      
//==============================================================================================
//  createToken()
//==============================================================================================    
    private static      void        createToken( Category category ) {
        
 
        var token   = new Token( new Location( startLocation ) , category , buffer );
        buffer      = "";        
        tokens.add( token );
        if( category == LINE_TERMINATOR ) {     endLocation.nextLine();   }        

    }
          
//==============================================================================================
//  isLineTerminator()
//==============================================================================================
    private static     boolean      isLineTerminator( String x ) {      
        
        return  lineTerminators.contains( x );      
        
    }
    
//==============================================================================================
//  setTokenLocation()
//==============================================================================================     
    private static      void        setTokenLocation() {    
        
        startLocation.setLine( endLocation.getLine() );
        startLocation.setColumn( endLocation.getColumn() );   
        
    }    
        
    
//==============================================================================================
//  next()
//==============================================================================================    
    private static      String      next() {              
        
        if( ! lookaheadValue.equals( "" ) ) {
            
            buffer += lookaheadValue ;
            lookaheadValue = "";
            endLocation.nextColumn();
            return current();            
            
        } else  if( lookaheadValue.equals( "" ) && ! isEndOfFile() ) {
            
            buffer += scanner.next();
            endLocation.nextColumn();
            return current();
        }
        
        return "";
        
    }
   
//==============================================================================================
//  ignore()
//==============================================================================================    
    private static      void        ignore() {      buffer  =   "";        }
    
//==============================================================================================
//  current()
//==============================================================================================       
    private static      String      current() {   
        
        return  buffer.isEmpty() ?   ""     :   buffer.substring( buffer.length() - 1 ) ;   
        
    }    
    
    
//==============================================================================================
//  lookahead()
//==============================================================================================       
    private static      String      lookahead( ) {      
        
        if( lookaheadValue.equals( "" )  &&  ! isEndOfFile() ) {               
            
            lookaheadValue = scanner.next();
            
        } 

        return lookaheadValue;
    }
                    
//==============================================================================================
//  isEndOfFile()
//==============================================================================================       
    private static      boolean     isEndOfFile() {  
        
        if( lookaheadValue.equals( "" ) ) {      return  ! scanner.hasNext( );     }
        
        return false;
        
    }

//==============================================================================================
//  ignoreBOM()
//==============================================================================================        
    private static      void        ignoreBOM() {  
        
        while( scanner.hasNext( BOM ) ) {    scanner.next();    }
        
    }    
    
    
//==============================================================================================
//  inLineComment()
//==============================================================================================    
    private static      void    inLineComment() {
        
        while( ! isLineTerminator( lookahead() ) ) {      next();       }
        
        ignore();
                
    }
    
//==============================================================================================
//  blockComment()
//==============================================================================================    
    private static      void    blockComment() {
        
        next();
        
        int level = 1;

        do {            
            
                    if( lookahead().equals( "{" ) ) {   next();     if( lookahead().equals( "#" ) )     {   next(); level++;        }   }
            else    if( lookahead().equals( "#" ) ) {   next();     if( lookahead().equals( "}" ) )     {   next(); level--;        }   }
            else    {
                
                next();    
                
                if( isLineTerminator( current() ) ) {  
                    
                    handleCRLF();
                    
                    endLocation.nextLine(); 
                
                }   
            }
                      
        } while( ! isEndOfFile()  &&  level > 0 );       
        
        if( level > 0 ) {   error( "unclosed comment" );    }
                
        ignore(); 

    }  

//==============================================================================================
//  isSeparatorOrOperator()
//==============================================================================================    
    private static      boolean isSeparatorOrOperator( String c ) {

        for( var x  :   separatorsOrOperators.keySet() ) {
            
            if( x.startsWith( c ) ) {       return  true;       }
            
        }
        
        return false;               
        
    }
       
    
//==============================================================================================
//  separatorOrOperator()
//==============================================================================================    
    private static      void    separatorOrOperator() {        
        
        boolean     match           = true;
        var         matchedSymbols  = separatorsOrOperators.keySet();   
        
        while( match )  {           
                
            var newMatchedSymbols   = new HashSet< String >();
            
            for( var x  :   matchedSymbols ) {  
                
                if( x.startsWith( buffer + lookahead() )) {       newMatchedSymbols.add( x );     }     
                
            }
            
            if( newMatchedSymbols.isEmpty() )   {       match = false;      }
            else                                
            {
                
                matchedSymbols  = newMatchedSymbols;     
                next();
                        
            }

        }
        
        Category category       = separatorsOrOperators.get( buffer );
        
        if( category == null ) {    error( "unrecognized symbol : " + buffer );          }        
        
        createToken( category ); 
        
    }    
    
    
//==============================================================================================
//  isFloatingPoint()
//==============================================================================================    
    private static      boolean isFloatingPoint() {     return digits.contains( lookahead() );      }    
        

//==============================================================================================
//  isFloatingPoint()
//==============================================================================================    
    private static      boolean isDigit( String x ) {    return digits.contains( x );    }
    
//==============================================================================================
//  isMixedNumber()
//==============================================================================================      
    private static      boolean isMixedNumber( String   number) {
                
        boolean mashreq   = false;
        boolean maghreb   = false;
        
        for( int i = 0; i < number.length(); i++  ) {
            
            String x = number.substring( i, i+1 );
            if( maghrebDigits.contains( x ) )   {   maghreb = true; }
            if( mashreqDigits.contains( x ) )   {   mashreq = true; }
            
        }        
         
        if( maghreb && mashreq ) { 
            
            error( "numbers can only have one type of digits; mashreq or maghreb but not both : " + number );
            return false;
        
        } 

        return true;
        
    }
    
//==============================================================================================
//  getDigitType()
//==============================================================================================
    private static      DigitType   getDigitType( String    digit ) {
        
        if( mashreqDigits.contains( digit ) )  {        return MASHREQ;     }           
            return MAGHREB;            

    }
    
//==============================================================================================
//  floatingPointLiteral()
//==============================================================================================    
    private static      void    floatingPointLiteral( DigitType digitType ) {        
        
        while( digits.contains( lookahead() ) ) {       next();     }   
        
        if( ! isMixedNumber( buffer ) ) { 
                    
            if( digitType == MAGHREB ) { createToken( MAGHREB_FLOAT ); }
            else                       { createToken( MASHREQ_FLOAT ); }
        
        }         
        
    }       
    
//==============================================================================================
//  decimalLiteral()
//==============================================================================================    
    private static      void    decimalLiteral( DigitType   digitType ) {      
        
       
        if( ! isMixedNumber( buffer ) ) {
            
            if( digitType == MAGHREB )  { createToken( MAGHREB_INTEGER ); }
            else                        { createToken( MASHREQ_INTEGER ); }
            
        }
            
    
    }    


//==============================================================================================
//  decimalOrfloatingPointLiteral()
//==============================================================================================    
    private static      void    decimalOrfloatingPointLiteral( DigitType digitType ) {
               
        while( digits.contains( lookahead() ) ) {       next();     }
        
        if( lookahead().equals( "." ) ) {       next(); floatingPointLiteral( digitType );     }
        else                            {               decimalLiteral( digitType );           }
        
        
        
                
    }
    
    
//==============================================================================================
//  enclosedLiteral()
//==============================================================================================    
    private static      void    enclosedLiteral(    List< String >  symbols         , 
                                                    Category        category        , 
                                                    String          errorMessage    ) {
        
        while( ! isEndOfFile() ) {
            
            if( ! current().equals( "\\" ) && symbols.contains( lookahead() ) )  {   
                
                next();     
                createToken( category );  
                break;  
                
            } else {   
                
                next();  
                
            }
            
        }

        if( isEndOfFile()     &&  ! buffer.isEmpty() ) {      error( errorMessage );     }           
        
    }    
    
//==============================================================================================
//  characterLiteral()
//==============================================================================================    
    private static      void    characterLiteral() {    
        
        enclosedLiteral(    singleQuotes                    , 
                            CHARACTER                       , 
                            "unclosed character literal"    );
        
    }
    
    
//==============================================================================================
//  StringLiteral()
//==============================================================================================    
    private static      void    stringLiteral() {     
        
        enclosedLiteral(    doubleQuotes                    , 
                            STRING                          , 
                            "unclosed string literal"       );
        
    }            
    
//==============================================================================================
//  StringLiteralArabicQuotation()
//==============================================================================================    
    private static      void    stringLiteralArabicQuotation() {     
        
        enclosedLiteral(    List.of("»")                    , 
                            STRING                          , 
                            "unclosed string literal"       );
        
    }    
    
    
//==============================================================================================
//  handleCRLF()
//==============================================================================================    
    private static      void    handleCRLF() {    
        
        if( current().equals( "\r") && lookahead().equals("\n" ) ) {     next();     }  
        
    }
    
    
//==============================================================================================
//  lineTerminator()
//==============================================================================================    
    private static      void    lineTerminator() {
        
        handleCRLF();
        
        createToken( LINE_TERMINATOR );
        
    }    
    
    
//==============================================================================================
//  whitespace()
//==============================================================================================    
    private static      void    whitespace()    {       ignore();   }    
    

    
//==============================================================================================
//  booleanLiteral()
//==============================================================================================    
    private static      void    booleanLiteral() {      createToken( BOOLEAN );             }

//==============================================================================================
//  nullLiteral()
//==============================================================================================    
    private static      void    nullLiteral()   {     createToken( NULL );                  }
          
//==============================================================================================
//  keyword()
//==============================================================================================    
    private static      void    keyword()       {     createToken( KEYWORD );               }
        
//==============================================================================================
//  identifier()
//==============================================================================================    
    private static      void    identifier()    {       createToken( IDENTIFIER );          }  

//==============================================================================================
//  javaLetterOrDigit()
//==============================================================================================    
    private static      void    javaLetterOrDigit() {
        
        while( lettersOrDigits.contains( lookahead() ) ) {      next();     }
                
        switch( buffer ) {
        
        case    "صح"  :
        case    "خطأ"   :   booleanLiteral();   break;
        case    "خالي"   :   nullLiteral();      break;
        default        :   
            
            if( keywords.contains( buffer ) )   {   keyword();      }
            else                                {   identifier();   }

        }
        
    }    
      
//==============================================================================================
//  error()
//==============================================================================================    
    private static      void    error( String message ) {
        
        errorTable.add( startLocation +  " : " + message );
        
    }    
    
     
//==============================================================================================
//  exec()
//==============================================================================================            
    public static    ArrayList< Token >    exec( List< String > source ) {

        for( var filename : source ) {

            var path                = Paths.get( filename ).toAbsolutePath();
            
            tokens                  = new ArrayList<Token>();            
            buffer                  = "";
            lookaheadValue          = "";
            startLocation           = new Location( 1 , 0 );
            endLocation             = new Location( 1 , 0 );
            errorTable              = new ArrayList< String >();
                
            try {  
                
                    scanner         = new Scanner( path , "UTF-8"  );
                    scanner.useLocale( new Locale( "ar" , "AR" ) );
                    scanner.useDelimiter("");
                   
                    ignoreBOM();
                    
                    while( ! isEndOfFile() ) {                
    
                        String c        = next();
                        
                        setTokenLocation();
    
                        switch( c ) {
                        
                        case    "#"     :   inLineComment();        break;
                        case    "{"     :
                            
                            switch( lookahead() ) {
                            
                            
                            case    "#" :   blockComment();         break;
                            default     :   separatorOrOperator();  break;
                            
                            }
                            
                            break;
                                                    
                        case    "."     :
                            
                                if( isFloatingPoint( ) )                {   floatingPointLiteral( getDigitType( lookahead() ) );    }
                                else                                    {   separatorOrOperator();                                  }   
                            
                            break;
    
                            
                            
                        default         :
                                    if( isDigit( c ) )                  {   decimalOrfloatingPointLiteral( getDigitType( c ) );     }               
                            else    if( singleQuotes.contains( c ) )    {   characterLiteral();                                     }
                            else    if( doubleQuotes.contains( c ) )    {   stringLiteral();                                        }
                            else    if( c.equals( "«" )  )              {   stringLiteralArabicQuotation();                         }             
                            else    if( isLineTerminator( c) )          {   lineTerminator();                                       }
                            else    if( whitespaceChars.contains( c ) ) {   whitespace();                                           }
                            else    if( letters.contains( c ) )         {   javaLetterOrDigit();                                    }
                            else    if( isSeparatorOrOperator( c ) )    {   separatorOrOperator();                                  }
                            else    error( "invalid character!" + c );
                            }
    
    
                        }
    
                
            }   catch( IOException e )  {    readFileError( filename , e );       }
                finally                 {    scanner.close();                     }
        
        
        if( ! errorTable.isEmpty() ) {      errorTable.forEach( e -> System.out.println( e ) );     }
        
        }
        
        return tokens;
        
    }
    
}
