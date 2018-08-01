package com.karim.examples.cxf.exceptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleExceptionBean", propOrder = {
		"type"
		,"code"
		,"message"
})
public class SimpleExceptionBean {
    @XmlElement(name = "ErrorType", required = true)
	private String type;
    
    @XmlElement(name = "ErrorCode", required = true)
    private Integer code;
    
    @XmlElement(name = "ErrorMessage", required = true)
	private String message;

    public SimpleExceptionBean() {
    	
    }
    
	public SimpleExceptionBean(String type, Integer code, String message) {
		this.type = type;
		this.code = code;
		this.message = message;
	}
	

	public String getType() {
		return type;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}