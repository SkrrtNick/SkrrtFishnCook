package com.skrrtnick.data;

public enum Fish {
    SHRIMP("Shrimp", 1),
    ANCHOVIES("Anchovies", 15);

    private String name;
    private int reqLevel;

    Fish(String name, int reqLevel) {
        this.name = name;
        this.reqLevel = reqLevel;
    }
    public String getName() {
        return name;
    }
    public int getReqLevel() {
        return reqLevel;
    }
}
