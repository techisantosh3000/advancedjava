package com.analysis.json.parsing.model;

public class User {
    private Long id;
    private Address userAddress;

    public User(){
        //
    }

    public User(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(Address userAddress) {
        this.userAddress = userAddress;
    }
}
