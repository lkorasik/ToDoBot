package com.fsm;

import java.util.ArrayList;

/**
 * Таблица переходов
 * @author Lev
 */
public class TransitionTable {
    private final ArrayList<Transition> transitions = new ArrayList<>();

    /**
     * Добавляем переход
     * @param transition переход
     */
    public void addTransition(Transition transition){
        if(transition != null)
            transitions.add(transition);
    }

    /**
     * Возвращает конечное состояние по начальному состоянию и ключевому слову.
     * Если же состояние не было найдено будет возвращен null.
     * @param start начальное состояние
     * @param key Ключевое слово
     * @return конечное состояние
     */
    public States getEndState(States start, String key){
        for(int i = 0; i < transitions.size(); i++){
            Transition transition = transitions.get(i);

            String m_key = transition.getKey();
            States m_start = transition.getStartState();
            States m_end = transition.getEndState();

            if (key == null && m_start.equals(start) && m_key == null){
                return m_end;
            }
            else if(m_key != null && m_start.equals(start) && m_key.equals(key)){
                return m_end;
            }
        }

        return null;
    }
}
