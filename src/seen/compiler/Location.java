package seen.compiler;

public class   Location {
    
    private int      line;
    private int      column;
    
    
//==============================================================================================
//  constructor
//==============================================================================================        
    public      Location( int line , int column ) {
        
        this.line   = line;
        this.column = column;
        
    }
    
    public      Location( Location  location ) {
        
        this.line   = location.line;
        this.column = location.column;
        
    }    
    
//==============================================================================================
//  getters / setters
//==============================================================================================        
    public  int     getLine()               {       return  this.line;      }
    public  void    setLine( int line )     {       this.line   = line;     }
    public  int     getColumn()             {       return  this.column;    }
    public  void    setColumn( int column ) {       this.column = column;   }
      
//==============================================================================================
//  nextLine()
//==============================================================================================    
    public  void    nextLine()  { this.line++;  this.column = 0; }
    
//==============================================================================================
//  nextColumn()
//==============================================================================================   
    public  void    nextColumn()  { this.column++; }

    
//==============================================================================================
//  toString()
//==============================================================================================
    public  String  toString()  {   return  "( " + this.line + " , "   + this.column + " )";   }
    
    
    
}