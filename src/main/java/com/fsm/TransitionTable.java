package com.fsm;

import java.util.ArrayList;

public class TransitionTable {
    private final ArrayList<Transition> transitions = new ArrayList<>();

    public void addTransition(Transition transition){
        if(transition != null)
            transitions.add(transition);
    }

    public State getEndState(State start, String key){
        for(int i = 0; i < transitions.size(); i++){
            Transition transition = transitions.get(i);

            State m_start = transition.getStartState();
            String m_key = transition.getKey();
            State m_end = transition.getEndState();

            if(key == null && m_start.equals(start) && m_key == null){
                return m_end;
            }
            else if(m_start.equals(start) && m_key.equals(key)){
                return m_end;
            }
        }

        return null;
    }
}
