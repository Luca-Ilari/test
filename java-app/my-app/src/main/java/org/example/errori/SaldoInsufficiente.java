package org.example.errori;

public class SaldoInsufficiente extends Exception {
    public SaldoInsufficiente(String errorMessage){
        super(errorMessage);
    }
}
