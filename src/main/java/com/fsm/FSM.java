package com.fsm;

import com.core.Constants;

import java.util.ArrayList;

public class FSM {
    ArrayList<State> states = new ArrayList<>();
    TransitionTable trans = new TransitionTable();
    ArrayList<String> commands = new ArrayList<>();
    public State cur;

    public FSM(){
        State ep = new State(Constants.ENTRY_POINT_STATE);
        State start = new State(Constants.START_STATE);
        State add = new State(Constants.ADD_STATE);
        State del = new State(Constants.DEL_STATE);
        State show = new State(Constants.SHOW_STATE);
        State help = new State(Constants.HELP_STATE);

        states.add(ep);
        states.add(start);
        states.add(add);
        states.add(del);
        states.add(show);
        states.add(help);

        trans.add(new Transition(ep, Constants.START_COMMAND, start));
        trans.add(new Transition(start, Constants.ADD_TASK_COMMAND, add));
        trans.add(new Transition(add, Constants.CANCEL_COMMAND, start));
        trans.add(new Transition(add, null, start));
        trans.add(new Transition(start, Constants.DELETE_TASK_COMMAND, del));
        trans.add(new Transition(del, Constants.CANCEL_COMMAND, start));
        trans.add(new Transition(del, null, start));
        trans.add(new Transition(start, Constants.SHOW_TASKS_COMMAND, show));
        trans.add(new Transition(show, null, start));
        trans.add(new Transition(start, Constants.HELP_COMMAND, help));
        trans.add(new Transition(help, null, start));

        cur = ep;

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

        State end = trans.get(cur, line);

        if(end != null) {
            cur = end;
        }
    }

    public void update(){
        update("");
    }
}
