package org.example;

import org.example.conto.Banca;
import org.example.conto.Utente;
import org.example.errori.NessunaBancaTrovata;

public class SistemaBancario {
    public static void creaConto(Utente utente, String nomeBanca){
        try {
            System.out.println(utente.getNomeCompleto() + " ha creato un conto con iban->" + utente.creaConto(nomeBanca));
        } catch (NessunaBancaTrovata e) {
            e.printStackTrace();
        }
    }
    public static void main( String[] args )
    {
        Banca.getBanche().add(new Banca("Intesa"));
        Banca.getBanche().add(new Banca("JP Morgan"));

        Utente utente1 = new Utente("Luca", "Ilari");
        Utente utente2 = new Utente("Mario", "Rossi");

        creaConto(utente1, "Intesa");
        creaConto(utente2, "JP Morgan");
        creaConto(utente1, "JP Morgan");

        try {
            utente1.deposita(1, 100);
            utente2.deposita(2, 100);
            System.out.println("\nIl saldo dell'utente 1 è ->" + utente1.getSaldo(1));
            utente1.prelievo(1,44);
            System.out.println("Il saldo dell'utente 1 è ->" + utente1.getSaldo(1));
            utente1.deposita(3, 1230);
            System.out.println("Il saldo dell'utente 1 è ->" + utente1.getSaldo(3));
            utente2.prelievo(2,100);
            System.out.println("Il saldo dell'utente 2 è ->" + utente2.getSaldo(2));
            System.out.println("Iban 1 fa bonifico di 50 a iban 2 ");
            utente1.bonifico(1,2,33);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //utente1.deposita();
    }
}