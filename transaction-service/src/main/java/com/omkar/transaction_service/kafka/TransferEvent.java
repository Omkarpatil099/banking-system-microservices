package com.omkar.transaction_service.kafka;

public class TransferEvent {

    private String fromAccountNumber;
    private String toAccountNumber;
    private Double amount;

    public TransferEvent() {
    }

    public TransferEvent(
            String fromAccountNumber,
            String toAccountNumber,
            Double amount) {

        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}