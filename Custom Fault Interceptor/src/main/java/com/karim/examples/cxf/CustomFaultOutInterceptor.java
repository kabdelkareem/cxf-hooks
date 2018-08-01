package com.karim.examples.cxf;


import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.FaultOutInterceptor;
import org.apache.cxf.message.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.karim.examples.cxf.exceptions.MyFaultException;

public class CustomFaultOutInterceptor extends FaultOutInterceptor {

	@Override
	public void handleMessage(Message message) throws Fault {
		// Get the original exception from the fault exception
		Throwable callingServiceException = message.getContent(Exception.class);

		// Override the fault body if thrown exception not from type MyFaultException
		if (callingServiceException.getCause() == null || !(callingServiceException.getCause() instanceof MyFaultException)) {
			//Get the actual exception
			while(callingServiceException.getCause() != null) { 
				callingServiceException = callingServiceException.getCause();
			}
			
			// Construct the return Fault
			MyFaultException newException = new MyFaultException(callingServiceException.getMessage(), callingServiceException);
			Fault newfault = new Fault(newException);
			
			// Build Fault Message Detail Manually
			Document newDoc = DOMUtils.createDocument();
			Element exceptionDetail = newDoc.createElement("detail");
  	      		
			Element myFaultNode = newDoc.createElement(newException.getClass().getSimpleName());
			exceptionDetail.appendChild(myFaultNode);
  	      	
			Element faultTypeBeanNode = newDoc.createElement("ErrorType");
			faultTypeBeanNode.setTextContent("INTERNAL_SERVER_ERROR");
			Element faultCodeBeanNode = newDoc.createElement("ErrorCode");
			faultCodeBeanNode.setTextContent("510");
			Element faultMessageBeanNode = newDoc.createElement("ErrorMessage");
			faultMessageBeanNode.setTextContent(callingServiceException.getMessage());
  	      	
			myFaultNode.appendChild(faultTypeBeanNode);
			myFaultNode.appendChild(faultCodeBeanNode);
			myFaultNode.appendChild(faultMessageBeanNode);
  	    
	  	      
			// Set the fault detail
			newfault.setDetail(exceptionDetail);
		    
			// Overwrite old exception
			message.setContent(Exception.class, newfault);
		}
	}
}
