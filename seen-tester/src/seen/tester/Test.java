package         seen.tester;
   
public enum     Test {
    
    LEXICAL_ANALYSIS        ( 0 , "التحليل اللغوي" )    ,
    SYNTAX_ANALYSIS         ( 1 , "التحليل النحوي" )    ,
    SEMANTIC_ANALYSIS       ( 2 , "التحليل الدلالي" )    ,
    INTERMEDIATE_LANGUAGE   ( 3 , "اللغة الوسيطة" )     ;
    
    
    private final         int           index;
    private final        String         arabic;
    
    

//==============================================================================================
//  constructor()
//==============================================================================================        
    private                                 Test( int index , String arabic ) {        
        
        this.index      = index;     
        this.arabic     = arabic;    
        
    }
    


//==============================================================================================
//  getIndex()
//==============================================================================================
    public                    int           getIndex() {        return this.index;        }
    
    
//==============================================================================================
//  override toString()
//==============================================================================================    
    @Override public        String          toString() {        return this.arabic;        }
    
    
}