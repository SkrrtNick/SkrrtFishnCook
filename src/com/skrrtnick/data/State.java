package com.skrrtnick.data;

public enum State {
    FISHING("Fishing"),
    COOKING("Cooking"),
    BANKING("Banking"),
    WALKING("Walking"),
    STARTING("Starting");

    private String name;
    State(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
