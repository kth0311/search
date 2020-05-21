package com.book.search.exeception;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;

public abstract class SearchException extends RuntimeException {

    public SearchException(){
        super();
    }

    public SearchException(String message){
        super(message);
    }

    public SearchException(String message, Throwable cause){
        super(message, cause);
    }
    public SearchException(Throwable cause){
        super(cause);
    }

    public abstract Integer getCode();

    public abstract HttpStatus getStatus();

    public String getMessageCode(){
        return getClass().getSimpleName();
    }

    public Object[] getMessageArguments(){
        return ArrayUtils.toArray();
    }


}
