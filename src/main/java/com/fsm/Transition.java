package com.fsm;

/**
 * Представление перехода в таблице переходов.
 * Т.е. есть начальное состояние, команда и конечное состояние
 * @author Lev
 */
public class Transition {
    //private State start;
    private String key;
    //private State end;
    private States start;
    private States end;

    //public Transition(State start, String key, State end){
    public Transition(States start, String key, States end){
        this.start = start;
        this.key = key;
        this.end = end;
    }

    /**
     * Получить стартовое состояние
     * @return состояние
     */
    //public State getStartState(){
    public States getStartState(){
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
    //public State getEndState(){
    public States getEndState(){
        return end;
    }
}
