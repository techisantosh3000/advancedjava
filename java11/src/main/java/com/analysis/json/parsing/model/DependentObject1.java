package com.analysis.json.parsing.model;

public class DependentObject1 {
    private Integer object1Id;
    private String object1Name;

    public Integer getObject1Id() {
        return object1Id;
    }

    public void setObject1Id(Integer object1Id) {
        this.object1Id = object1Id;
    }

    public String getObject1Name() {
        return object1Name;
    }

    public void setObject1Name(String object1Name) {
        this.object1Name = object1Name;
    }

    @Override
    public String toString() {
        return "DependentObject1{" +
                "object1Id=" + object1Id +
                ", object1Name='" + object1Name + '\'' +
                '}';
    }
}
