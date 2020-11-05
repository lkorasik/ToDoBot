package com.fsm;

import com.core.Constants;

import java.util.ArrayList;

/**
 * Класс, который реализцует конечный автомат
 * @author Lev
 */
public class FSM {
    private final TransitionTable trans = new TransitionTable();
    private final ArrayList<String> commands = new ArrayList<>();
    private States currentState;

    public FSM(){
        initTransitions();
        initCommands();

        currentState = States.EP;
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
     * Инициализация таблицы переходов
     */
    private void initTransitions(){
        trans.addTransition(new Transition(States.EP, Constants.START_COMMAND, States.START));
        trans.addTransition(new Transition(States.START, null, States.LISTEN));
        trans.addTransition(new Transition(States.LISTEN, Constants.ADD_TASK_COMMAND, States.ADD));
        trans.addTransition(new Transition(States.ADD, Constants.CANCEL_COMMAND, States.LISTEN));
        trans.addTransition(new Transition(States.ADD, null, States.LISTEN));
        trans.addTransition(new Transition(States.LISTEN, Constants.DELETE_TASK_COMMAND, States.DEL));
        trans.addTransition(new Transition(States.DEL, Constants.CANCEL_COMMAND, States.LISTEN));
        trans.addTransition(new Transition(States.DEL, null, States.LISTEN));
        trans.addTransition(new Transition(States.LISTEN, Constants.SHOW_TASKS_COMMAND, States.SHOW));
        trans.addTransition(new Transition(States.SHOW, null, States.LISTEN));
        trans.addTransition(new Transition(States.LISTEN, Constants.HELP_COMMAND, States.HELP));
        trans.addTransition(new Transition(States.HELP, null, States.LISTEN));
    }

    /**
     * Обновить состояние автомата
     * @param line строка с сообщением от пользователя
     */
    public void update(String line){
        if(!commands.contains(line)){
            line = null;
        }

        States end = trans.getEndState(currentState, line);

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
    public States getCurrentState(){
        return currentState;
    }

    /**
     * Сравнить состояние с текйщим состоянием
     */
    public boolean isState(States state){
        return currentState.equals(state);
    }
}
