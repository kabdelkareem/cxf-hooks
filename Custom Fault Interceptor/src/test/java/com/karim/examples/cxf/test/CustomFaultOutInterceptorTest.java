package com.karim.examples.cxf.test;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.karim.examples.cxf.CustomFaultOutInterceptor;
import com.karim.examples.cxf.exceptions.MyFaultException;

public class CustomFaultOutInterceptorTest extends Assert  {
	private CustomFaultOutInterceptor customFaultOutInterceptor;
	

	@Before public void initialize() {
        customFaultOutInterceptor = new CustomFaultOutInterceptor();
	}
	
    
    @Test
    public void GivenIOExceptionWhenCallingMyCustomFaultInterceptorThenMyFaultExceptionReturnedOnMessage() throws Exception {
        Message message = new MessageImpl();
        message.setExchange(new ExchangeImpl());
        
        String errorMessage = "testing error message";
        Fault newfault = new Fault(new IOException(errorMessage));
        message.setContent(Exception.class, newfault);
        
        customFaultOutInterceptor.handleMessage(message);
        
        Throwable returnedException = message.getContent(Exception.class);
    	assertTrue(returnedException instanceof org.apache.cxf.interceptor.Fault);
    	assertTrue(returnedException.getCause() instanceof MyFaultException);
    	
    	Fault returnedFault = (Fault) returnedException;
    	assertNotNull(returnedFault.getDetail());
    	
    	// Getting the detail body
    	TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(returnedFault.getDetail());
        StreamResult result = new StreamResult(new StringWriter());


        transformer.transform(source, result);
        String returnedDetail = result.getWriter().toString();
        
        String expectedReturnedDetail = 
        		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        		+ "<detail>"
        			+ "<MyFaultException>"
        				+ "<ErrorType>INTERNAL_SERVER_ERROR</ErrorType>"
        				+ "<ErrorCode>510</ErrorCode>"
        				+ "<ErrorMessage>"+errorMessage+"</ErrorMessage>"
        			+ "</MyFaultException>"
        		+ "</detail>";
        
        assertEquals(expectedReturnedDetail, returnedDetail);
    }
}
