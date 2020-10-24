package com.fsm;

import java.util.ArrayList;

public class FSM {
    ArrayList<State> states = new ArrayList<>();
    TransitionTable trans = new TransitionTable();
    public State cur;

    public FSM(){
        State ep = new State("EP", true);
        State start = new State("START", true);
        State add = new State("ADD", true);
        State del = new State("DEL", true);
        State show = new State("SHOW", false);
        State help = new State("HELP", false);

        states.add(ep);
        states.add(start);
        states.add(add);
        states.add(del);
        states.add(show);
        states.add(help);

        trans.add(new Transition(ep, "/start", start));
        trans.add(new Transition(start, "/add", add));
        trans.add(new Transition(add, "/cancel", start));
        trans.add(new Transition(add, null, start));
        trans.add(new Transition(start, "/del", del));
        trans.add(new Transition(del, "/cancel", start));
        trans.add(new Transition(del, null, start));
        trans.add(new Transition(start, "/show", show));
        trans.add(new Transition(show, null, start));
        trans.add(new Transition(start, "/help", help));
        trans.add(new Transition(help, null, start));

        cur = ep;
    }

    public void next(String line){
        String res;

        if(!line.equals("/start") && !line.equals("/add")
                && !line.equals("/cancel") && !line.equals("/del")
                && !line.equals("/show") && !line.equals("/help")){
            line = null;
        }

        State end = trans.get(cur, line);

        if(end != null) {
            cur = end;
        }
    }
}
