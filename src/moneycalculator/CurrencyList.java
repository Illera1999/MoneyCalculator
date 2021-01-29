package moneycalculator;

import java.util.HashMap;
import java.util.Map;

public class CurrencyList {

    private Map<String,Currency> currencies;

    public CurrencyList() {
    
        currencies = new HashMap<>();
        insertar();
    }

    public void add(Currency currency){
        currencies.put(currency.getIsoCode(), currency);
    }
    
    public Currency get(String code){
        return currencies.get(code.toUpperCase());   
    }
    
    public void insertar(){
        add(new Currency("USD", "Dólar americano", "$"));
        add(new Currency("EUR", "Euros", "€"));
        add(new Currency("GBP", "Libras Esterlinas", "£"));
    }
    
}
