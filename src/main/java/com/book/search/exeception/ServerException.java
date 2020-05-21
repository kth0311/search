package com.book.search.exeception;

public abstract class ServerException extends SearchException{

    public ServerException(){
        super();
    }

    public ServerException(String message){
        super(message);
    }

    public ServerException(String message, Throwable cause){
        super(message, cause);
    }
    public ServerException(Throwable cause){
        super(cause);
    }
}
