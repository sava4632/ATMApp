package atmapp.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Samuel
 */
public class Account implements Serializable {

    private String id;
    private String iban;
    private String holderId;
    private String holderName;
    private long code;
    private LocalDate creationDate;
    private LocalDate expirationDate;
    private double balance; // saldo de la cuenta
    private Currency currency; // moneda de la cuenta
    private boolean isActive;

    private List<Transaction> transactions;

    // Vacio
    public Account() {
    }

    // Constrictor para crear una cuenta nueva 
    public Account(String id, String iban, String holderId, String holderName, long code, LocalDate creationDate, LocalDate expirationDate, double balance, Currency currency, boolean isActive) {
        this.id = id;
        this.iban = iban;
        this.holderId = holderId;
        this.holderName = holderName;
        this.code = code;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.balance = balance;
        this.currency = currency;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getHolderId() {
        return holderId;
    }

    public void setHolderId(String holderId) {
        this.holderId = holderId;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedAmount = decimalFormat.format(balance);
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * hace el cambio de Euros a Dolares
     *
     * @return el saldo en dolares
     */
    public double getBalanceInDollars() {
        if (currency.getCode().equals("EUR")) {
            double balanceInDollars = balance * currency.getExchangeRate();
            // Reemplaza las comas con puntos antes de convertir la cadena a double
            balanceInDollars = Double.parseDouble(String.format("%.2f", balanceInDollars).replace(",", "."));
            return balanceInDollars;
        } else {
            return balance;
        }
    }

    /**
     * hace el cambio de Dolares a Euros
     *
     * @return el saldo en euros
     */
    public double getBalanceInEuros() {
        if (currency.getCode().equals("USD")) {
            return balance / currency.getExchangeRate();
        } else {
            return balance;
        }
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedAmount = decimalFormat.format(balance);
        return id + "," + iban + "," + holderId + "," + holderName + "," + code + "," + creationDate + ","
                + expirationDate + "," + balance + "," + currency.getCode() + "," + currency.getSymbol() + ","
                + currency.getExchangeRate() + "," + isActive;
    }

}
