package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MoneyCalculator {
  
    private ExchangeRate exchangeRate;
    private Currency currencyTo;
    private Currency currencyFrom;
    private Money dinero;
    private CurrencyList lista;
    

    public static void main(String[] args) throws Exception {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.execute();
    }

    public MoneyCalculator() {
        lista = new CurrencyList();
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
        currencyFrom = lista.get(scanner.next().toUpperCase());
        //toUpperCase():Convertir todos los caracteres de una cadena dada a mayúsculas
        
        dinero = new Money(amount,currencyFrom);
        
        System.out.println("Introduzca divisa destino: ");
        currencyTo = lista.get(scanner.next());
    }
    private void process()throws Exception{
        exchangeRate = getExchangeRate(currencyFrom,currencyTo);
    }
    private void output(){
        System.out.println(dinero.getAmount() + dinero.getCurrency().getSymbol() + " equivalen a " + dinero.getAmount()*exchangeRate.getRate() + currencyTo.getSymbol());
    }

    public ExchangeRate getExchangeRate(Currency from, Currency to) throws IOException {   
        URL url = 
            new URL("https://api.exchangeratesapi.io/latest?base=" + from.getIsoCode() + "&symbols=" + to.getIsoCode() + "&compact=y");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to.getIsoCode())+5, line.indexOf("}"));
            return new ExchangeRate( from, to, LocalDate.of(2018,Month.SEPTEMBER,26),Double.parseDouble(line1));
        }
    }   
}