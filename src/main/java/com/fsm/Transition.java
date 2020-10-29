package com.fsm;

public class Transition {
    private State start;
    private String key;
    private State end;

    public Transition(State start, String key, State end){
        this.start = start;
        this.key = key;
        this.end = end;
    }

    public State getStartState(){
        return start;
    }

    public String getKey(){
        return key;
    }

    public State getEndState(){
        return end;
    }
}
