package io.kubemq.sdk.Exceptions;

import java.io.IOException;

public class TransactionException extends IOException {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TransactionException(){
        super("Transaction response is null");
    }


    public TransactionException(String error){
        super(error);
    }
   
}
