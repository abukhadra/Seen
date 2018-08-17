package seen.compiler;

import seen.compiler.Category;
import seen.compiler.Location;

public class Token {
    
    private     Location     location;
    private     Category     category;
    private     String       value;
    

//==============================================================================================
//  constructor
//==============================================================================================          
    public      Token(  Location    location    ,
                        Category    category    , 
                        String      value       ) {
        
            this.location   = location;
            this.category   = category;
            this.value      = value;
        
    }
    

//==============================================================================================
//  getters 
//==============================================================================================          
    public Location     getLocation()   {     return this.location;     }
    public Category     getCategory()   {     return this.category;     }
    public String       getValue()      {     return this.value;        }   
    
    
//==============================================================================================
//  toString()
//==============================================================================================
    public  String  toString()  {   return  this.location + " , " + this.category + " , " + this.value ;   }    
    
}
