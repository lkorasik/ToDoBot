package com.fsm;

import com.core.Constants;

import java.util.ArrayList;

/**
 * Класс, который реализцует конечный автомат
 * @author Lev
 */
public class FSM {
    private final ArrayList<State> states = new ArrayList<>();
    private final TransitionTable trans = new TransitionTable();
    private final ArrayList<String> commands = new ArrayList<>();
    private State currentState;

    private final State ep = new State(Constants.ENTRY_POINT_STATE);
    private final State start = new State(Constants.START_STATE);
    private final State listen = new State(Constants.LISTENING_STATE);
    private final State add = new State(Constants.ADD_STATE);
    private final State del = new State(Constants.DEL_STATE);
    private final State show = new State(Constants.SHOW_STATE);
    private final State help = new State(Constants.HELP_STATE);

    public FSM(){
        initStates();
        initTransitions();
        initCommands();

        currentState = ep;
    }

    /**
     * Инициализация списка команд
     */
    private void initCommands(){
        commands.add(Constants.ADD_TASK_COMMAND);
        commands.add(Constants.DELETE_TASK_COMMAND);
        commands.add(Constants.SHOW_TASKS_COMMAND);
        commands.add(Constants.START_COMMAND);
        commands.add(Constants.HELP_COMMAND);
        commands.add(Constants.CANCEL_COMMAND);
    }

    /**
     * Инициализация списка состояний
     */
    private void initStates(){
        states.add(ep);
        states.add(start);
        states.add(listen);
        states.add(add);
        states.add(del);
        states.add(show);
        states.add(help);
    }

    /**
     * Инициализация таблицы переходов
     */
    private void initTransitions(){
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
    }

    /**
     * Обновить состояние автомата
     * @param line строка с сообщением от пользователя
     */
    public void update(String line){
        if(!commands.contains(line)){
            line = null;
        }

        State end = trans.getEndState(currentState, line);

        if(end != null) {
            currentState = end;
        }
    }

    /**
     * Обновить состояние автомата
     */
    public void update(){
        update("");
    }

    /**
     * Получить текущее сосотояние автмата
     * @return состояние
     */
    public String getCurrentStateName(){
        return currentState.getName();
    }
}
