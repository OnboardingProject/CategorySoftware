package com.catalog.exception;




import org.springframework.http.HttpStatus;



/**
* This is the custom exception class which throws exception when empty list is
* given
*
* @author
*
*/
public class ListEmptyException extends RuntimeException {



   private static final long serialVersionUID = 1L;
    String msg;
    HttpStatus httpStatus;



   public ListEmptyException(String msg, HttpStatus httpStatus) {
        super();
        this.msg = msg;
        this.httpStatus = httpStatus;
    }



   public String getMsg() {
        return msg;
    }



   public HttpStatus getHttpStatus() {
        return httpStatus;
    }



}