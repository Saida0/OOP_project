package com.example.simulation_of_bangladesh_bank.Sara;

public class ManageRealTimeGrossSettlement {
    private String rtgs;
    private String sender;
    private String receiver;
    private String status;

    public ManageRealTimeGrossSettlement(String rtgs, String sender, String receiver, String status) {
        this.rtgs = rtgs;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    public String getRtgs() {
        return rtgs;
    }

    public void setRtgs(String rtgs) {
        this.rtgs = rtgs;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManageRealTimeGrossSettlement{" +
                "rtgs='" + rtgs + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
