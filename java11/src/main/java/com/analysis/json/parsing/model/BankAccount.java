package com.analysis.json.parsing.model;

import java.util.Objects;

public class BankAccount {
    private Integer bankId;
    private String bankName;

    public BankAccount(Integer bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(bankId, that.bankId) && Objects.equals(bankName, that.bankName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankId, bankName);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
