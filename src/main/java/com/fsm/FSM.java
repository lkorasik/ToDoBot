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
    private State currentState;

    public FSM(){
        initTransitions();
        initCommands();

        currentState = State.ENTRY_POINT;
    }

    /**
     * Инициализация списка команд
     */
    private void initCommands(){
        commands.add(Constants.ADD_TASK_COMMAND);
        commands.add(Constants.DELETE_TASK_COMMAND);
        commands.add(Constants.COMPLETE_TASK_COMMAND);
        commands.add(Constants.SHOW_TODO_TASKS_COMMAND);
        commands.add(Constants.SHOW_COMPLETED_TASKS_COMMAND);
        commands.add(Constants.START_COMMAND);
        commands.add(Constants.HELP_COMMAND);
        commands.add(Constants.CANCEL_COMMAND);
        commands.add(Constants.CLEAR_COMMAND);
        commands.add(Constants.NOTIF_COMMAND);
    }

    /**
     * Инициализация таблицы переходов
     */
    private void initTransitions(){
        trans.addTransition(new Transition(State.ENTRY_POINT, Constants.START_COMMAND, State.START));
        trans.addTransition(new Transition(State.START, null, State.LISTEN));

        trans.addTransition(new Transition(State.LISTEN, Constants.ADD_TASK_COMMAND, State.ADD));
        trans.addTransition(new Transition(State.ADD, Constants.CANCEL_COMMAND, State.LISTEN));
        trans.addTransition(new Transition(State.ADD, null, State.LISTEN));

        trans.addTransition(new Transition(State.LISTEN, Constants.DELETE_TASK_COMMAND, State.DEL));
        trans.addTransition(new Transition(State.DEL, Constants.CANCEL_COMMAND, State.LISTEN));
        trans.addTransition(new Transition(State.DEL, null, State.LISTEN));

        trans.addTransition(new Transition(State.LISTEN, Constants.COMPLETE_TASK_COMMAND, State.DONE));
        trans.addTransition(new Transition(State.DONE, Constants.CANCEL_COMMAND, State.LISTEN));
        trans.addTransition(new Transition(State.DONE, null, State.LISTEN));

        trans.addTransition(new Transition(State.LISTEN, Constants.SHOW_TODO_TASKS_COMMAND, State.SHOW_TODO));
        trans.addTransition(new Transition(State.SHOW_TODO, null, State.LISTEN));

        trans.addTransition(new Transition(State.LISTEN, Constants.SHOW_COMPLETED_TASKS_COMMAND, State.SHOW_COMPLETED));
        trans.addTransition(new Transition(State.SHOW_COMPLETED, null, State.LISTEN));

        trans.addTransition(new Transition(State.LISTEN, Constants.HELP_COMMAND, State.HELP));
        trans.addTransition(new Transition(State.HELP, null, State.LISTEN));

        trans.addTransition(new Transition(State.LISTEN, Constants.CLEAR_COMMAND, State.CLEAR));
        trans.addTransition(new Transition(State.CLEAR, null, State.LISTEN));

        trans.addTransition(new Transition(State.LISTEN, Constants.NOTIF_COMMAND, State.NOTIFICATION));
        trans.addTransition(new Transition(State.NOTIFICATION, Constants.CANCEL_COMMAND, State.LISTEN));
        trans.addTransition(new Transition(State.NOTIFICATION, null, State.LISTEN));
    }

    /**
     * Обновить состояние автомата
     * @param stateKey ключ перехода к новому состоянию
     */
    public void updateState(String stateKey){
        if(!commands.contains(stateKey)){
            stateKey = null;
        }

        State end = trans.getEndState(currentState, stateKey);

        if(end != null) {
            currentState = end;
        }
    }

    public void setListenState(){
        currentState = State.LISTEN;
    }

    /**
     * Установить состояние fsm
     * @param state Новое состояние
     */
    public void setState(State state){
        currentState = state;
    }


    /**
     * Получить текущее сосотояние автмата
     * @return состояние
     */
    public State getCurrentState(){
        return currentState;
    }

    /**
     * Сравнить состояние с текйщим состоянием
     */
    public boolean isState(State state){
        return currentState.equals(state);
    }
}
