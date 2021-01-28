package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MoneyCalculator {
  
    private double exchangeRate;
    private Currency currencyTo;
    private Money dinero;
    private Map <String,Currency> lista =  new HashMap <>();;
    

    public static void main(String[] args) throws Exception {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }

    public MoneyCalculator() {
        lista.put("USD", new Currency("USD", "Dólar americano", "$"));
        lista.put("EUR", new Currency("EUR", "Euros", "€"));
        lista.put("GBP", new Currency("GBP", "Libras Esterlinas", "£"));
    }

    private void execute() throws Exception{
        input();
        process();
        output();
    }
    private void input(){
        System.out.println("Introduzca una cantidad: ");
        Scanner scanner = new Scanner(System.in);
        double amount = Double.parseDouble(scanner.next());
        
        System.out.println("Introduzca divisa origen: ");
        Currency currencyFrom = lista.get(scanner.next().toUpperCase());
        //toUpperCase():Convertir todos los caracteres de una cadena dada a mayúsculas
        
        dinero = new Money(amount,currencyFrom);
        
        System.out.println("Introduzca divisa destino: ");
        currencyTo = lista.get(scanner.next().toUpperCase());
    }
    private void process()throws Exception{
        exchangeRate = getExchangeRate(dinero.getCurrency().getIsoCode(),currencyTo.getIsoCode());
    }
    private void output(){
        System.out.println(dinero.getAmount() + dinero.getCurrency().getSymbol() + " equivalen a " + dinero.getAmount()*exchangeRate + currencyTo.getSymbol());
    }

    public double getExchangeRate(String from, String to) throws IOException {   
        URL url = 
            new URL("https://api.exchangeratesapi.io/latest?base=" + from + "&symbols=" + to + "&compact=y");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to)+5, line.indexOf("}"));
            return Double.parseDouble(line1);

        }
    }   
}