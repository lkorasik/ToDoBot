package com.fsm;

public enum States {
    START("START"), EP("EP"), LISTEN("LISTEN"), ADD("ADD"), DEL("DEL"), SHOW("SHOW"), HELP("HELP");
    private final String state;

    States(String state){
        this.state = state;
    }

    private String getState(){
        return state;
    }


    public boolean equals(States state){
        return state.getState().equals(this.state);
    }

    @Override
    public String toString() {
        return state;
    }
}
