package org.example.errori;

public class NessunaBancaTrovata extends Exception{
    public NessunaBancaTrovata(String errorMessage) {
        super(errorMessage);
    }
}
