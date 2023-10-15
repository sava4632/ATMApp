package atmapp.model;

import java.io.Serializable;

/**
 *
 * @author Samuel
 */
public class Currency implements Serializable{
    private String code; // código de la moneda (por ejemplo, "EUR", "USD")
    private String symbol; // símbolo de la moneda (por ejemplo, "€", "$")
    private double exchangeRate; // tasa de cambio a una moneda de referencia

    //Vacio
    public Currency() {
    }
    
    //Constructor para añadir una moneda
    public Currency(String code, String symbol, double exchangeRate) {
        this.code = code;
        this.symbol = symbol;
        this.exchangeRate = exchangeRate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String toString() {
        return code + "," + symbol + "," + exchangeRate;
    }
    
    
}
