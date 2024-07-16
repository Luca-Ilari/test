package org.example.errori;

public class ImpossibileSalvareSulFile extends Exception{
    public ImpossibileSalvareSulFile(String errorMessage){
        super(errorMessage);
    }
}
