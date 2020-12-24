package com.core;

import com.fsm.FSM;
import com.fsm.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Тестируем функцональность, которую предоставляет FSM
 *
 * @author Lev
 */
public class FSMTest {
    private FSM fsm;

    @Before
    public void setUp(){
        fsm = new FSM();
    }

    /**
     * Проверям состояние после начала диалога
     */
    @Test
    public void testStartDialog(){
        fsm.updateState("/start");

        Assert.assertTrue(fsm.isState(State.START));
    }

    /**
     * Проверяем состояние после начала диалога с неправильной команды
     */
    @Test
    public void testNotUseStart(){
        fsm.updateState("/add");

        Assert.assertFalse(fsm.isState(State.ADD));
        Assert.assertTrue(fsm.isState(State.ENTRY_POINT));
    }

    /**
     * Проверяем состояние после добавления задачи
     */
    @Test
    public void testAddTask(){
        fsm.updateState("/start");
        fsm.updateState("/add");
        fsm.updateState("To");

        Assert.assertTrue(fsm.isState(State.LISTEN));
    }

    /**
     * Проверяем состояние после отмены добавления задачи
     */
    @Test
    public void testAddTaskCanceled(){
        fsm.updateState("/start");
        fsm.updateState("/add");
        fsm.updateState("/cancel");

        Assert.assertTrue(fsm.isState(State.START));
    }

    /**
     * Проверяем состояние после удаления задачи
     */
    @Test
    public void testDeleteTask(){
        fsm.updateState("/start");
        fsm.updateState("/add");
        fsm.updateState("A");
        fsm.updateState("/del");
        fsm.updateState("0");

        Assert.assertTrue(fsm.isState(State.LISTEN));
    }

    /**
     * Проверяем состояние после отмены удаления задачи
     */
    @Test
    public void testDelTaskCanceled(){
        fsm.updateState("/start");
        fsm.updateState("/del");
        fsm.updateState("/cancel");

        Assert.assertTrue(fsm.isState(State.START));
    }

    /**
     * Проверяем состояние после показа списка задач
     */
    @Test
    public void testShow(){
        fsm.updateState("/start");
        fsm.updateState("/show");

        fsm.setListenState();

        Assert.assertTrue(fsm.isState(State.LISTEN));
    }

    /**
     * Проверяем состояние после показа help
     */
    @Test
    public void testHelp(){
        fsm.updateState("/start");
        fsm.updateState("/help");

        fsm.setListenState();

        Assert.assertTrue(fsm.isState(State.LISTEN));
    }
}
