package com.karim.examples.cxf.exceptions;


import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "MyFaultException")
public class MyFaultException extends Exception {
	  
    private SimpleExceptionBean exception;

    public MyFaultException() {
        super();
    }
    
    public MyFaultException(String message) {
        super(message);
    }
    
    public MyFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyFaultException(String message, SimpleExceptionBean exception) {
        super(message);
        this.exception = exception;
    }

    public MyFaultException(String message, SimpleExceptionBean exception, Throwable cause) {
        super(message, cause);
        this.exception = exception;
    }

    public SimpleExceptionBean getFaultInfo() {
        return this.exception;
    }
}