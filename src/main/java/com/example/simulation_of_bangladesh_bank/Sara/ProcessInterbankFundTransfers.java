package com.example.simulation_of_bangladesh_bank.Sara;

public class ProcessInterbankFundTransfers {
    private String request;
    private String sender;
    private String receiver;
    private String amount;

    public ProcessInterbankFundTransfers(String request, String sender, String receiver, String amount) {
        this.request = request;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ProcessInterbankFundTransfers{" +
                "request='" + request + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
