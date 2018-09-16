package com.karim.examples.cxf.test.groovy;

import spock.lang.Specification
import spock.lang.Shared
import org.apache.cxf.interceptor.Fault
import org.apache.cxf.message.MessageImpl
import com.karim.examples.cxf.CustomFaultOutInterceptor
import com.karim.examples.cxf.exceptions.MyFaultException

class BasicTest extends Specification {
	@Shared 
	def customFaultOutInterceptor = new CustomFaultOutInterceptor()

	def "intercepting of a MyFaultException should not overwrite the repsonse"() {
		
		given: "encapsulates MyFaultException in a fault"
			def inputFault = new Fault(new MyFaultException())
			
		and: "cosntructing message to be intercepted"
			def message = new MessageImpl()
		
		and: "setting the message content's fault to MyFaultException"
			message.setContent(Exception.class, inputFault)
			
		when: "overwrite the returned fault exception"
			customFaultOutInterceptor.handleMessage(message)
		
		then: "no change occurs to the message content's fault"
			def returnedException = message.getContent(Exception.class)
			assert inputFault.getCause().is(returnedException.getCause())
	}
	
	def "intercepting of an IOException should overwrite the response"() {
		
		given: "encapsulates IOException in a fault"
			def inputFault = new Fault(new IOException())
			
		and: "cosntructing message to be intercepted"
			def message = new MessageImpl()
			
		and: "setting the message content's fault to IOException"
			message.setContent(Exception.class, inputFault)
			
		when: "overwrite the returned fault exception"
			customFaultOutInterceptor.handleMessage(message)
		
		then: "message content's fault is overwritten to be from type MyFaultException"
			def returnedException = message.getContent(Exception.class)
			assert !inputFault.getCause().is(returnedException.getCause())
			assert returnedException.getCause() instanceof MyFaultException
	}
}  
