# Seen   س 

An Arabic based programming language                              
 

## Status
### Phase 1
Currently,the focus is on developing a bootsrapping compiler that is capable of compiling "Seen" source code to .class files that runs on the JVM   

Following is an overall task list  
- [x] Lexer
- [ ] Parser
- [ ] Intermediate Language ( JVM instructions )
- [ ] Class File generation 

### Phase 2
- [ ] re-write the compiler in the seen language. 

## Bootsrapping compiler
**source**  : **/src/**
**JAR file** : a precompiled JAR file can be found at **/seen.jar**
**command line** : go to the repository home directory and run : 
 **java -jar seen.jar "test\hello world.seen"**
 make sure that your console supports the Arabic.
 
   

##Seen Tester
A lot of tools do not support the Arabic language , a basic GUI based tool was created to simplify the process involved in viewing Arabic source files

**source** : **/seen-tester/src**  
**JAR file**  **/seem-tester/seen-tester.jar**
**command line**:
 **java -jar SeenTester.jar**
 
 ![Tester screenshot](https://github.com/abukhadra/Seen/blob/master/images/seen_tester.jpg)
 
##The Language

###Name 
**س** ( pronunciation sīn , like "Seen" ) is the twelvth letter of the Arabic alphabet, there are several reasons this name was given to the language, two of which are simplicity , and its usage in  mathematics, the letter س is used by default to indicate unknown values and variables, the equivalent of "x" in English.
 

###Design
It is still not decided yet which programming paradigm(s) the language will support. 
The intention is to develop a statically typed, class based object oriented language with type inference.
  
  
###Syntax  
a sample file showing the syntax of the language is included in the tester tool, you can run the tester tool or look directly at the file [here](https://github.com/abukhadra/Seen/blob/master/seen-tester/src/seen/tester/test1.seen). please not that you need Arabic  support and to set the text direction from Right To Left to be able to view the file properly.

###Source Files
-**Encoding** : All files need to be saved in **UTF-8** encoding
-**Extension** : Files must have the extension : **.seen** or the Arabic extension **.س**



##Technology
The applications are written using **Java 10**



##License
The Seen bootsrapping compiler source code and the Seen Tester tool are both licensed under an **MIT license**.  

