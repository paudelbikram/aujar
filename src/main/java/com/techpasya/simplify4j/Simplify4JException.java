package com.techpasya.simplify4j;

public class Simplify4JException extends Exception{


    public Simplify4JException(){
        super();
    }

    public Simplify4JException(String errorMessage){
        super(errorMessage);
    }

    public Simplify4JException(Throwable cause){
        super(cause);
    }

    public Simplify4JException(String errorMessage, Throwable cause){
        super(errorMessage, cause);
    }

    public Throwable getUnderlyingException(){
        return super.getCause();
    }

    @Override
    public String toString() {
        Throwable cause = getUnderlyingException();
        if (cause == null || cause == this) {
            return super.toString();
        } else {
            return super.toString() + " [See nested exception: " + cause + "]";
        }
    }






}
