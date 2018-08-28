
# Custom Fault Interceptor
The best practice of using a web service that it must always return a predictable response even if it is an exception. This project is a showcase to how to overwrite the thrown exception to a predefined custom fault exception using a CXF interceptor. 


### Project Dependences
* CXF


### Project Structure (Main Files)
`com.karim.examples.cxf.exceptions` contains two files represent the returned a custom @WebFault exception that must be returned in case of any exception thrown in the web service.

`com.karim.examples.cxf` contains the custom fault interceptor.


### Project Constraints
`CustomFaultOutInterceptor.java` must be registered as an outFaultInterceptor of the web service


### Custom Fault Pseudocode
```
IF the original fault exception, extracted from message body, not from my custom @WebFault exception
THEN
  Get the real cause of the exception (most inner cause)
  Intialize my custom @WebFault with the realException
  Build the custom @WebFault details DOM manually (to avoid CXF from wrapping myfault exception on another cxf fault exception)
  Update the message body to my custom @WebFault.
END
``` 
