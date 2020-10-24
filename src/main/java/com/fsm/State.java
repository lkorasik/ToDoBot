package com.fsm;

public class State {
    public String name;
    public boolean waitUser;

    public State(String name, boolean waitUser){
        this.name = name;
        this.waitUser = waitUser;
    }

    public String getValue(){
        return name;
    }
}
