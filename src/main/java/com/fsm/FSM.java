package com.fsm;

import com.core.Constants;

import java.util.ArrayList;

/**
 * Класс, который реализцует
 */
public class FSM {
    private final ArrayList<State> states = new ArrayList<>();
    private final TransitionTable trans = new TransitionTable();
    private final ArrayList<String> commands = new ArrayList<>();
    private State currentState;

    public FSM(){
        State ep = new State(Constants.ENTRY_POINT_STATE);
        State start = new State(Constants.START_STATE);
        State listen = new State(Constants.LISTENING_STATE);
        State add = new State(Constants.ADD_STATE);
        State del = new State(Constants.DEL_STATE);
        State show = new State(Constants.SHOW_STATE);
        State help = new State(Constants.HELP_STATE);

        states.add(ep);
        states.add(start);
        states.add(listen);
        states.add(add);
        states.add(del);
        states.add(show);
        states.add(help);

        trans.addTransition(new Transition(ep, Constants.START_COMMAND, start));
        trans.addTransition(new Transition(start, null, listen));
        trans.addTransition(new Transition(listen, Constants.ADD_TASK_COMMAND, add));
        trans.addTransition(new Transition(add, Constants.CANCEL_COMMAND, listen));
        trans.addTransition(new Transition(add, null, listen));
        trans.addTransition(new Transition(listen, Constants.DELETE_TASK_COMMAND, del));
        trans.addTransition(new Transition(del, Constants.CANCEL_COMMAND, listen));
        trans.addTransition(new Transition(del, null, listen));
        trans.addTransition(new Transition(listen, Constants.SHOW_TASKS_COMMAND, show));
        trans.addTransition(new Transition(show, null, listen));
        trans.addTransition(new Transition(listen, Constants.HELP_COMMAND, help));
        trans.addTransition(new Transition(help, null, listen));

        currentState = ep;

        commands.add(Constants.ADD_TASK_COMMAND);
        commands.add(Constants.DELETE_TASK_COMMAND);
        commands.add(Constants.SHOW_TASKS_COMMAND);
        commands.add(Constants.START_COMMAND);
        commands.add(Constants.HELP_COMMAND);
        commands.add(Constants.CANCEL_COMMAND);
    }

    public void update(String line){
        if(!commands.contains(line)){
            line = null;
        }

        State end = trans.getEndState(currentState, line);

        if(end != null) {
            currentState = end;
        }
    }

    public void update(){
        update("");
    }

    public String getCurrentStateName(){
        return currentState.getName();
    }
}
