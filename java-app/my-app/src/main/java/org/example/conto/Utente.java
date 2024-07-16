package org.example.conto;

import org.example.errori.*;
import org.example.utils.FileIO;

import static org.example.utils.FileIO.salvaDeposito;
import static org.example.utils.FileIO.salvaPrelievo;

public class Utente {
    private String nome;
    private String cognome;
    public Utente (String nome, String cognome){
        this.nome  = nome;
        this.cognome = cognome;
    }

    /**Questo metodo ritorna l'iban del conto appena creato*/
    public int creaConto(String nomeBanca) throws NessunaBancaTrovata{
        for(Banca b : Banca.getBanche()){
            if(b.getNomeBanca().equals(nomeBanca)){
                return b.createConto(this);//Ritorna l'iban del conto appena creato
            }
        }
        throw new NessunaBancaTrovata("Non ci sono banche con questo nome ");
    }

    /**Questo metodo trova il conto tramite iban e verifica che il proprietario del conto si l'utente che ha chiamato il metodo*/
    private Conto trovaConto(int iban) throws NessunIbanTrovato {
        for(Banca b : Banca.getBanche()) {
            Conto c = b.getConti().get(iban); //ritorna l'oggetto conto con key iban
            if (c != null) {//Sarà null se nell'arrayList della banca non c'è l'iban
                return c;
            }
        }
        throw new NessunIbanTrovato("Prelievo non effettuato: Iban non trovato");
    }

    public void prelievo(int iban, double ammontare) throws NessunIbanTrovato, AccessoAlContoNegato, SaldoInsufficiente, ImpossibileSalvareSulFile {
        Conto c = trovaConto(iban); // Ritorna l'ggetto conto e lancia un NessunIbanTrovato nel caso l'iban non esista

        if (c.getInterstario() != this) {// Verifica che l'utente possegga il conto
            throw new AccessoAlContoNegato("Non sei il propretario di questo conto. Prelievo non effettuato");
        }

        if (c.getSaldo() < ammontare){// Verifica che abbia abbastanza soldi sul conto
            throw new SaldoInsufficiente("Impossibile prelevare. Saldo < " + ammontare );
        }

        c.prelievo(ammontare);
        try {
            salvaPrelievo(c, ammontare);
        } catch (ImpossibileSalvareSulFile e) {
            c.deposito(ammontare); //Ripristina il saldo precedente se l'operazione non va a buon fine
            throw new ImpossibileSalvareSulFile("Impossibile prelevare. Operazione annullata");
        }
        //Se tutte le operazione precedenti sono andate a buon fine effettua il prelievo effettivo
    }

    public void deposita(int iban, double ammontare) throws NessunIbanTrovato, AccessoAlContoNegato, ImpossibileSalvareSulFile {
        Conto c = trovaConto(iban); //Ritorna l'ggetto conto e lancia un NessunIbanTrovato nel caso l'iban non esista

        if (c.getInterstario() != this) {// verifica che l'utente possegga il conto
            throw new AccessoAlContoNegato("Non sei il propretario di questo conto. Deposito non effettuato");
        }

        c.deposito(ammontare);
        try{
            salvaDeposito(c, ammontare);
        } catch (ImpossibileSalvareSulFile e) {
            c.prelievo(ammontare);//Se il salvataggio su file non va a buon fine ripristina il saldo precedente
            throw new ImpossibileSalvareSulFile("Impossibile depositare. Operazione annullata");
        }
    }

    public void bonifico(int ibanSorgente, int ibanDestinazione, double ammontare) throws NessunIbanTrovato, SaldoInsufficiente, ImpossibileSalvareSulFile {
        Conto contoS;
        Conto contoD;
        try{
            contoS = trovaConto(ibanSorgente);
        }catch (NessunIbanTrovato e){
            throw new NessunIbanTrovato("Bonifico annullato Iban " + ibanSorgente + " non trovato.");
        }
        try{
            contoD = trovaConto(ibanDestinazione);
        }catch (NessunIbanTrovato e){
            throw new NessunIbanTrovato("Bonifico annullato Iban " + ibanSorgente + " non trovato.");
        }

        if (contoS.getSaldo() < ammontare){
            throw new SaldoInsufficiente("Impossibile effettuare il Bonifico. Saldo < " + ammontare );
        }
        contoS.prelievo(ammontare);
        contoD.deposito(ammontare);
        try{
            FileIO.salvaBonifico(contoD,contoS,ammontare);
        }catch (ImpossibileSalvareSulFile e){
            contoS.deposito(ammontare);
            contoD.prelievo(ammontare);
            throw new ImpossibileSalvareSulFile("Operazione annullata.");
        }
    }
    public double getSaldo(int iban) throws NessunIbanTrovato, AccessoAlContoNegato {
        Conto c = trovaConto(iban); //Ritorna l'ggetto conto e lancia NessunIbanTrovato nel caso l'iban non esista
        if (c.getInterstario() == this) {// verifica che l'utente possegga il conto
            return c.getSaldo();
        }else{
            throw new AccessoAlContoNegato("Non sei il propretario di questo conto");
        }
    }

    public String getNomeCompleto() {
        return nome + " " + cognome;
    }
}
