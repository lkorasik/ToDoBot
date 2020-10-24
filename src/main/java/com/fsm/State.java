package com.fsm;

public class State {
    public String name;
    public boolean waitUser;

    public State(String name){
        this.name = name;
        waitUser = false;
    }

    public String getValue(){
        return name;
    }
}
