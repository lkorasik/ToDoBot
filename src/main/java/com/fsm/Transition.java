package com.fsm;

public class Transition {
    public State start;
    public String key;
    public State end;

    public Transition(State start, String key, State end){
        this.start = start;
        this.key = key;
        this.end = end;
    }
}
