package org.example.conto;

import java.util.ArrayList;
import java.util.HashMap;

public class Banca {
    private static int iban;
    private static ArrayList<Banca> banche = new ArrayList<>();
    private String nomeBanca;
    HashMap<Integer, Conto> conti = new HashMap<Integer, Conto>();

    public Banca(String nomeBanca){
        this.nomeBanca = nomeBanca;
        //crea file della banca
    }

    public int createConto(Utente utente){
        Conto nuovoConto = new Conto(utente);
        nuovoConto.setIstitutoFinanziario(this);
        conti.put(nuovoConto.getIban(), nuovoConto);
        return nuovoConto.getIban();
    }
    public HashMap<Integer, Conto> getConti() {
        return conti;
    }

    public static int getNewIban(){
        iban += 1;
        return iban;
    }
    public String getNomeBanca() {
        return nomeBanca;
    }

    public static ArrayList<Banca> getBanche() {
        return banche;
    }

}
