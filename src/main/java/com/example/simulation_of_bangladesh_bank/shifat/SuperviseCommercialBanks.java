package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;

public class SuperviseCommercialBanks implements Serializable {
    @javafx.fxml.FXML
    private String bankId;
    private String name;
    private int licensenumber;
    private String type;
    private String status;

    public SuperviseCommercialBanks(String bankId, String name, int licensenumber, String type, String status) {
        this.bankId = bankId;
        this.name = name;
        this.licensenumber = licensenumber;
        this.type = type;
        this.status = status;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLicensenumber() {
        return licensenumber;
    }

    public void setLicensenumber(int licensenumber) {
        this.licensenumber = licensenumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SuperviseCommercialBanks{" +
                "bankId='" + bankId + '\'' +
                ", name='" + name + '\'' +
                ", licensenumber=" + licensenumber +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
