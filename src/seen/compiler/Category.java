package         seen.compiler;

public enum     Category {
    
            LINE_TERMINATOR     ( "فاصل_أسطر" )         
    ,       WHITESPACE          ( "مسافة_بيضاء" )             
    ,       KEYWORD             ( "كلمة_أساسبة" )         
    ,       IDENTIFIER          ( "معرّف" )            
    ,       MASHREQ_INTEGER     ( "عدد_صحيح_مشرقي" )      
    ,       MAGHREB_INTEGER     ( "عدد_صحيح_مغربي" )      
    ,       MASHREQ_FLOAT       ( "عدد_فاصل_عائم _مشرقي" )   
    ,       MAGHREB_FLOAT       ( "عدد_فاصل_عائم_مغربي" )    
    ,       BOOLEAN             ( "بوليانِيّ" )            
    ,       CHARACTER           ( "محرف" )               
    ,       STRING              ( "سلسلة" )            
    ,       NULL                ( "خالي" )            
    ,       SEPARATOR           ( "فاصل" )           
    ,       OPERATOR            ( "مؤثر" )            
    ,       ERROR               ( "خطأ" )                 
    ;          
    
    private final       String      arabic;
    
//==============================================================================================
//  constructor()
//==============================================================================================        
    private                             Category( String arabic ) {     this.arabic = arabic;   }    
    
    
//==============================================================================================
//  override toString()
//==============================================================================================    
    @Override public        String      toString() {       return this.arabic;     }    
    
}