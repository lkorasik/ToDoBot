package com.fsm;

/**
 * Класс, который представляет состояние автомата
 * @author Lev
 */
public class State {
    private final String name;

    public State(String name){
        this.name = name;
    }

    /**
     * Получить имя сосотояния
     * @return имя
     */
    public String getName(){
        return name;
    }
}
