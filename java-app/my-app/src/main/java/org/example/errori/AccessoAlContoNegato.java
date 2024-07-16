package org.example.errori;

public class AccessoAlContoNegato extends Exception{
    public AccessoAlContoNegato (String errorMessage) {
        super(errorMessage);
    }
}
