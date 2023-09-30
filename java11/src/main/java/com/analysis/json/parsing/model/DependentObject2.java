package com.analysis.json.parsing.model;

public class DependentObject2 {
    private Integer object2Id;
    private String object2Name;

    public Integer getObject2Id() {
        return object2Id;
    }

    public void setObject2Id(Integer object2Id) {
        this.object2Id = object2Id;
    }

    public String getObject2Name() {
        return object2Name;
    }

    public void setObject2Name(String object2Name) {
        this.object2Name = object2Name;
    }

    @Override
    public String toString() {
        return "DependentObject2{" +
                "object2Id=" + object2Id +
                ", object2Name='" + object2Name + '\'' +
                '}';
    }
}
