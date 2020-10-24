package com.fsm;

import java.util.ArrayList;

public class TransitionTable {
    public ArrayList<Transition> transitions = new ArrayList<>();

    public void add(Transition transition){
        transitions.add(transition);
    }

    public State get(State start, String key){
        for(int i = 0; i < transitions.size(); i++){
            Transition transition = transitions.get(i);

            if(key == null){
                if(transition.start.equals(start) && transition.key == null){
                    return transition.end;
                }
            }
            else {
                if(transition.start.equals(start) && transition.key.equals(key)){
                    return transition.end;
                }
            }
        }

        return null;
    }
}
