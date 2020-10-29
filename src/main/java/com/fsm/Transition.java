package com.fsm;

/**
 * Представление перехода в таблице переходов.
 * Т.е. есть начальное состояние, команда и конечное состояние
 * @author Lev
 */
public class Transition {
    private State start;
    private String key;
    private State end;

    public Transition(State start, String key, State end){
        this.start = start;
        this.key = key;
        this.end = end;
    }

    /**
     * Получить стартовое состояние
     * @return состояние
     */
    public State getStartState(){
        return start;
    }

    /**
     * Получить ключевое слово
     * @return ключевое слово
     */
    public String getKey(){
        return key;
    }

    /**
     * Получить конечное состояние
     * @return состояние
     */
    public State getEndState(){
        return end;
    }
}
