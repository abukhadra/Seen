package     seen.test.compiler;

import        java.io.File;
import        java.io.IOException;
import        java.nio.file.Files;
import        java.nio.file.Paths;
import        java.util.Scanner;
import        java.util.stream.Collectors;

import        seen.compiler.Compiler;
import static    seen.Error.readFileError;



public class Test1 {
    
    private static final String    SOURCE_FILE = "src/seen/test/compiler/test1.seen";
    
    public static void run() {    new Compiler().run( SOURCE_FILE);    }

        

}
