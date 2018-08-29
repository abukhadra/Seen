package			seen.tester;

import			java.io.IOException;
import 			java.nio.file.Paths;
import 			java.util.Locale;
import 			java.util.Scanner;

import 			org.w3c.dom.Document;
import			org.w3c.dom.events.Event;
import			org.w3c.dom.events.EventListener;
import 			org.w3c.dom.events.EventTarget;
import 			org.w3c.dom.html.HTMLBodyElement;
import 			org.w3c.dom.html.HTMLButtonElement;
import 			org.w3c.dom.html.HTMLDivElement;
import 			org.w3c.dom.html.HTMLLabelElement;
import 			org.w3c.dom.html.HTMLOptionElement;
import 			org.w3c.dom.html.HTMLSelectElement;
import 			org.w3c.dom.html.HTMLTableElement;
import 			org.w3c.dom.html.HTMLTableRowElement;
import 			org.w3c.dom.html.HTMLTextAreaElement;

import          javafx.application.Application;
import 			javafx.concurrent.Worker.State;
import 			javafx.scene.Scene;
import 			javafx.scene.web.WebEngine;
import 			javafx.scene.web.WebView;
import 			javafx.stage.Stage;


public class	Main 			extends     Application {

	private static final 	String					NOT_READY_MESSAGE		= "قيد الانشاء!!!";
    private static final    String    				SOURCE_FILE 			= "src/seen/tester/test1.seen";
    private static final    String    				CSS_FILE 				= "tester.css";
    private static			String					source;	
    private static 			WebEngine				webEngine;
    private	static			Document				document;
	
//==============================================================================================
//	main()
//==============================================================================================    
	public static			void						main( String[] 	args ) {		launch( args );		}

//==============================================================================================
//  override start()
//==============================================================================================            
    @Override
    public                  void            			start( Stage    stage )        throws	Exception {

    	var webView 		= new WebView();
		webEngine 			= webView.getEngine();		
    	var	css				= getClass().getResource( CSS_FILE ).toString();
    	
    	webEngine.setUserStyleSheetLocation( css );	
		webEngine.loadContent( "<body/>" );
		
    	webEngine.getLoadWorker().stateProperty().addListener( ( observable , oldState , newState ) -> {			
    				    		
    	    if (newState == State.SUCCEEDED) {    	    	

    	        document 			= webEngine.getDocument();
    	        
    	        var body			= ( HTMLBodyElement		)	document.getElementsByTagName( "body" ).item( 0 );
    	    	
    	    	
    	    	body.appendChild( testsDiv() );
    	    	body.appendChild( editorTable() );
    	    	body.appendChild( buttonsDiv() );
    	    	
    	    	loadSource( getInput() );

    	    }
    	});    	

    	Scene 	scene 		= new Scene( webView );       
        stage.setTitle( "«فاحص «س" );
        stage.setScene( scene );      
        stage.show( );

    }    
    
//==============================================================================================
//  getInput()
//==============================================================================================
    private static			HTMLTextAreaElement			getInput() {
    	
    	return ( HTMLTextAreaElement ) document.getElementById( "input" ); 	
    	
    }
    
//==============================================================================================
//  getOutput()
//==============================================================================================
    private static			HTMLTextAreaElement			getOutput() {
    	
    	return ( HTMLTextAreaElement ) document.getElementById( "output" );
    	
    }
       
//==============================================================================================
//  getSelectedTest()
//==============================================================================================
    private static			Test						getSelectedTest() {
    	
    	var 	tests					= ( HTMLSelectElement ) document.getElementById( "tests" );
    	Test 	selectedTest			= null;
    	
		for( Test test : Test.values() ) {
			
			if( test.getIndex() == tests.getSelectedIndex() ) {		selectedTest = test;		}
			
		} 
		
		return selectedTest;
    	
    }
    
//==============================================================================================
//  testsDiv()
//==============================================================================================    
    private static			HTMLDivElement				testsDiv() {
    	
        var testsDiv		= ( HTMLDivElement 		) 	document.createElement( "div" );
        var testsLabel		= ( HTMLLabelElement 	) 	document.createElement( "label" );
        var tests			= ( HTMLSelectElement 	) 	document.createElement( "select" );    	    	
        tests.setId( "tests" );
        
        
    	testsLabel.setTextContent( "التجربة" );
    	
    	
    	for( var x 	:	Test.values( ) ) {
    		
    		var option 			= ( HTMLOptionElement ) document.createElement( "option" );
    		option.setTextContent( x.toString() );
    		tests.add( option , null );		 	    
    		
    	}    	        
        
        testsDiv.appendChild( testsLabel );
    	testsDiv.appendChild( tests );    	
    	
    	return testsDiv;
    }
    
    
//==============================================================================================
//  editorTable()
//==============================================================================================    
    private static			HTMLTableElement 			editorTable() {
    	
        var editor          = ( HTMLTableElement    )  	document.createElement( "table" );
        var header          = ( HTMLTableRowElement )  	document.createElement( "tr" );
        header.setId( "header" );
        
        var inputOutputRow  = ( HTMLTableRowElement )   document.createElement( "tr" );
        var inputCell     	=                           document.createElement( "td" );
        var outputCell		=                           document.createElement( "td" );
                
        editor.appendChild( tableHeader( header ) );

    	inputCell.appendChild( inputTextArea() );
    	outputCell.appendChild( outputTextArea() );    	    	
    	inputOutputRow.appendChild( inputCell );
    	inputOutputRow.appendChild( outputCell );   
    	
    	editor.appendChild( inputOutputRow ); 
    	
    	return editor;
    	
    	
    }
        
    
//==============================================================================================
//  tableHeader()
//==============================================================================================    
    private static			HTMLTableRowElement			tableHeader( HTMLTableRowElement row ) {

        var inputLabelCell  =                           document.createElement( "td" );
        var outputLabelCell =                           document.createElement( "td" );   	
        
        var inputLabel		= ( HTMLLabelElement 	) 	document.createElement( "label" );	
        inputLabel.setId( "inputLabel" );
        
        var outputLabel		= ( HTMLLabelElement 	) 	document.createElement( "label" );	
        outputLabel.setId( "outputLabel" );
        
    	inputLabel.setTextContent( "المصدر" );
    	           
        
        
        outputLabel.setTextContent( "النتيجة" ); 
        
        inputLabelCell.appendChild( inputLabel );
    	outputLabelCell.appendChild( outputLabel );
    	row.appendChild( inputLabelCell );
    	row.appendChild( outputLabelCell );
        
        return row;
    }
    
    
    
//==============================================================================================
//  inputTextArea()
//==============================================================================================    
    private static			HTMLTextAreaElement			inputTextArea() {
    	
        var input			= ( HTMLTextAreaElement ) 	document.createElement( "textarea" );   	    	 	    	
    	input.setId( "input" );
    	
    	input.setAttribute( "wrap" , "off" );
    	
    	return input;
    }
    
    
//==============================================================================================
//  outputTextArea()
//==============================================================================================    
    private static			HTMLTextAreaElement			outputTextArea() {
    	
    	var output			= ( HTMLTextAreaElement ) 	document.createElement( "textarea" );
    	output.setId( "output" );
    	
    	output.setAttribute( "wrap" , "off" );
    	
    	return output;
    	
    }
      
    
//==============================================================================================
//  buttonsDiv()
//==============================================================================================      
    private static			HTMLDivElement				buttonsDiv() {
    	
    	var buttonsDiv		= ( HTMLDivElement 		) 	document.createElement( "div" );
    	
    	buttonsDiv.appendChild( startButton() );
    	buttonsDiv.appendChild( resetButton() );    	
    	
    	return buttonsDiv;
    	
    }
    
    
//==============================================================================================
//  startButton()
//==============================================================================================    
    private static			HTMLButtonElement			startButton() {
    	
    	var start			= ( HTMLButtonElement 	) 	document.createElement( "button" );
    	start.setTextContent( "ابدء" ); 
    	var startClickHandler 	= new EventListener() {		
    		
    		public void handleEvent( Event event ) {    		
	    			    			
	    		executeTest( getSelectedTest()  , getOutput() );    			

    		} 
    		
    	}; 
        
    	( ( EventTarget ) start ).addEventListener( "click" , startClickHandler, false );  
    	
    	return start;
    	
    }
    
    
//==============================================================================================
//  resetButton()
//==============================================================================================    
    private static			HTMLButtonElement			resetButton() {    	
    	
    	var reset			= ( HTMLButtonElement 	)	document.createElement( "button" );
    	reset.setTextContent( "استبداء" );
    	
    	var resetClickHandler 	= new EventListener() {		
    		
    		public void handleEvent( Event event ) {		
    			
    				
    			getOutput().setValue("");	
    			loadSource( getInput() );	

    		}
    		
    	}; 
        
    	( ( EventTarget ) reset ).addEventListener( "click" , resetClickHandler, false );
    	
    	return reset;
    	    	
    }
    
    
    
//==============================================================================================
//  loadSource()
//==============================================================================================
    private static			void						loadSource( HTMLTextAreaElement	textArea ) {
    	
    	var path		= Paths.get( SOURCE_FILE ).toAbsolutePath();
    	var fileContent	= "";
    	
    	try ( var scanner	= new Scanner( path , "UTF-8" ) ) {
    		    		    		
    		scanner.useLocale( new Locale( "ar" , "AR" ) );   		
    		scanner.useDelimiter("");

    		while( scanner.hasNext() ) {		fileContent += scanner.next();	}

    	}   catch( IOException e )  {    e.printStackTrace();       }
    	
    	textArea.setValue( fileContent );    	        	

	}  	
    	
    
  
//==============================================================================================
//  executeTest()
//==============================================================================================              
    private static			void 						executeTest( Test test , HTMLTextAreaElement textArea ) {
    	    	
    	switch( test ) {
    	
    	case LEXICAL_ANALYSIS			:	textArea.setValue( LexerTest.run( SOURCE_FILE ) ); break;
    	case SYNTAX_ANALYSIS			:	
    	case SEMANTIC_ANALYSIS			:	
    	case INTERMEDIATE_LANGUAGE		:	textArea.setValue( test.toString() + " : " + NOT_READY_MESSAGE ); break;
    	default							: 	textArea.setValue( "unexpected value : " + test.toString() );
    	
    	} 
    	
    }
    

}