package com.analysis.json.parsing.model;

import java.util.List;

public class UserDetail {
    private List<BankAccount> bankAccountList;
    private List<Address> addressList;

    public UserDetail(List<BankAccount> bankAccountList, List<Address> addressList) {
        this.bankAccountList = bankAccountList;
        this.addressList = addressList;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                ", bankAccountList=" + bankAccountList +
                ", addressList=" + addressList +
                '}';
    }
}
