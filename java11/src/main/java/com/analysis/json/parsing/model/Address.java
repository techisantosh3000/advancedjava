package com.analysis.json.parsing.model;

public class Address {
    private Integer addressId;
    private String addressCode;

    public Address(Integer addressId, String addressCode) {
        this.addressId = addressId;
        this.addressCode = addressCode;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", addressCode='" + addressCode + '\'' +
                '}';
    }
}
