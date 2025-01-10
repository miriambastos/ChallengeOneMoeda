package Models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class ConversionData {
    private String moedaBase;
    private String moedaAlvo;
    private double taxa;
    private double valor;
    private String hora;
//    Timestamp data = new Timestamp(System.currentTimeMillis());
//    private SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");

    public ConversionData(String moedaBase, String moedaAlvo, double taxa, double valor, String hora) {
        this.moedaBase = moedaBase;
        this.moedaAlvo = moedaAlvo;
        this.taxa = taxa;
        this.valor = valor;
        this.hora = hora;
    }

//    String hist = ( " | Moeda original: " + getMoedaBase() +
//                    " | Moeda a ser convertida: " + getMoedaAlvo() + "\n" +
//                    " | Valor original: " + getValor() + "\n" +
//                    " | Valor convertido: " + (this.getValor() * this.getTaxa()) +  "\n" +
//                    " | Taxa de conversão: " + this.getTaxa() + "\n" +
//                    " | Conversão realizada em: " + formatDate.format(data) + "\n" + "\n");


    public String getMoedaBase() {
        return moedaBase;
    }

    public String getMoedaAlvo() {
        return moedaAlvo;
    }

    public double getTaxa() {
        return taxa;
    }

    public double getValor() {
        return valor;
    }

    public String getHora() {
        return hora;
    }
}
