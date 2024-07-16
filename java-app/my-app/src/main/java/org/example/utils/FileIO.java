package org.example.utils;

import org.example.conto.Banca;
import org.example.conto.Conto;
import org.example.errori.ImpossibileSalvareSulFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

public class FileIO {
    static HashMap<String, FileWriter> file = new HashMap<>();
    static HashMap<String, BufferedWriter> bw = new HashMap<>();
    static HashMap<String, PrintWriter> pr = new HashMap<>();

    static {
        for (Banca b : Banca.getBanche()) {
            try {
                String nomeB = b.getNomeBanca();
                file.put(nomeB, new FileWriter("./logs/"+b.getNomeBanca()+".txt"/*, true*/));
                bw.put(nomeB, new BufferedWriter(file.get(nomeB)));
                pr.put(nomeB, new PrintWriter(bw.get(nomeB)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void salvaDeposito(Conto conto, double ammontare) throws ImpossibileSalvareSulFile {
        try {
            PrintWriter printWriter = pr.get(conto.getIstitutoFinanziario());
            Date date = new Date();
            printWriter.println(date.getTime() + " + Deposito iban = " + conto.getIban() + " ammontare = " + ammontare  + " Saldo dopo l'operazione = " + conto.getSaldo());
            printWriter.flush();
        }
        catch (Exception e) {
            throw new ImpossibileSalvareSulFile("Salvataggio su file non riuscito. Operazione annullata");
        }
    }
    public static void salvaPrelievo(Conto conto, double ammontare) throws ImpossibileSalvareSulFile {
        try {
            PrintWriter printWriter = pr.get(conto.getIstitutoFinanziario());
            Date date = new Date();
            printWriter.println(date.getTime() + " - Prelievo iban = " + conto.getIban() + " ammontare = " + ammontare + " Saldo dopo l'operazione = " + conto.getSaldo());
            printWriter.flush();
        }
        catch (Exception e) {
            throw new ImpossibileSalvareSulFile("Salvataggio dell'operazione sul file non riuscito");
        }
    }
    public static void salvaBonifico(Conto destinatario, Conto sorgente, double ammontare) throws ImpossibileSalvareSulFile {
        try {
            PrintWriter printWriterD = pr.get(destinatario.getIstitutoFinanziario());
            PrintWriter printWriterS = pr.get(sorgente.getIstitutoFinanziario());
            Date date = new Date();
            printWriterD.println(date.getTime() + " + Bonifico ricevuto iban = " + destinatario.getIban() + " ammontare = " + ammontare + " Saldo dopo l'operazione = " + destinatario.getSaldo());
            printWriterS.println(date.getTime() + " - Bonifico inviato iban = " + sorgente.getIban() + " ammontare = " + ammontare + " Saldo dopo l'operazione = " + sorgente.getSaldo());
            printWriterD.flush();
            printWriterS.flush();
        }
        catch (Exception e) {
            throw new ImpossibileSalvareSulFile("Salvataggio dell'operazione sul file non riuscito");
        }
    }
}
