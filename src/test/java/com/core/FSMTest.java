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
        fsm.update("/start");

        Assert.assertTrue(fsm.isState(State.START));
    }

    /**
     * Проверяем состояние после начала диалога с неправильной команды
     */
    @Test
    public void testNotUseStart(){
        fsm.update("/add");

        Assert.assertFalse(fsm.isState(State.ADD));
        Assert.assertTrue(fsm.isState(State.EP));
    }

    /**
     * Проверяем состояние после добавления задачи
     */
    @Test
    public void testAddTask(){
        fsm.update("/start");
        fsm.update("/add");
        fsm.update("To");

        Assert.assertTrue(fsm.isState(State.LISTEN));
    }

    /**
     * Проверяем состояние после отмены добавления задачи
     */
    @Test
    public void testAddTaskCanceled(){
        fsm.update("/start");
        fsm.update("/add");
        fsm.update("/cancel");

        Assert.assertTrue(fsm.isState(State.START));
    }

    /**
     * Проверяем состояние после удаления задачи
     */
    @Test
    public void testDeleteTask(){
        fsm.update("/start");
        fsm.update("/add");
        fsm.update("A");
        fsm.update("/del");
        fsm.update("0");

        Assert.assertTrue(fsm.isState(State.LISTEN));
    }

    /**
     * Проверяем состояние после отмены удаления задачи
     */
    @Test
    public void testDelTaskCanceled(){
        fsm.update("/start");
        fsm.update("/del");
        fsm.update("/cancel");

        Assert.assertTrue(fsm.isState(State.START));
    }

    /**
     * Проверяем состояние после показа списка задач
     */
    @Test
    public void testShow(){
        fsm.update("/start");
        fsm.update("/show");

        fsm.update();

        Assert.assertTrue(fsm.isState(State.LISTEN));
    }

    /**
     * Проверяем состояние после показа help
     */
    @Test
    public void testHelp(){
        fsm.update("/start");
        fsm.update("/help");

        fsm.update();

        Assert.assertTrue(fsm.isState(State.LISTEN));
    }
}
