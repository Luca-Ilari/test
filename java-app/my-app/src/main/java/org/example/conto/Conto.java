package org.example.conto;

public class Conto {
    private int iban;
    private double saldo;
    private Utente interstario;
    private Banca istitutoFinanziario;
    public Conto(Utente intestatario){
        this.interstario = intestatario;
        this.saldo = 0;
        this.iban = Banca.getNewIban();
    }
    public void deposito(double ammontare){
        saldo += ammontare;
    }
    public void prelievo(double ammontare){
        saldo -= ammontare;
    }
    public int getIban(){
        return this.iban;
    }
    public double getSaldo() {
       return saldo;
    }
    public Utente getInterstario() {
        return interstario;
    }

    public void setIstitutoFinanziario(Banca istitutoFinanziario) {
        this.istitutoFinanziario = istitutoFinanziario;
    }

    public String getIstitutoFinanziario(){
        return istitutoFinanziario.getNomeBanca();
    }
}
