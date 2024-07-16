package org.example.errori;

public class NessunIbanTrovato extends Exception{
    public NessunIbanTrovato(String errorMessage) {
        super(errorMessage);
    }
}
