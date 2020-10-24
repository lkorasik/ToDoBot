package com.fsm;

public class State {
    private final String name;
    //public boolean waitUser;

    public State(String name){
        this.name = name;
        //waitUser = false;
    }

    public String getName(){
        return name;
    }
}
