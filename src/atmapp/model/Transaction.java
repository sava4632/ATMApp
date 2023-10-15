package atmapp.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;

/**
 *
 * @author Samuel
 */
public class Transaction implements Serializable{

    private String id;
    private String accountId;
    private LocalDate date; // fecha de la transacción
    private double amount; // cantidad de la transacción
    private String type; // tipo de transacción (por ejemplo, "depósito", "retiro")
    private String description;
    
    //Vacio
    public Transaction() {    
    }

    //Constructor para crear una transaccion completa
    public Transaction(String id, String accountId, LocalDate date, double amount, String type, String description) {
        this.id = id;
        this.accountId = accountId;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedAmount = decimalFormat.format(amount);
        return id + "," +  accountId + "," + date + "," + amount + "," + type + "," + description;
    }
    
    
}
