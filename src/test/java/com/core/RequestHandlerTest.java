package com.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 * Тестируем класс RequestHandler в связке с Core
 *
 * @author Lev
 */
public class RequestHandlerTest {
    RequestHandler requestHandler;

    /**
     * Подгтовка к тесту
     */
    @Before
    public void setUp(){
        this.requestHandler = new RequestHandler();
    }

    /**
     * Доавбление задачи. Задача без пробелов
     */
    @Test
    public void testAddTaskWithoutSpace(){
        String result = requestHandler.handle("/add Task1");

        Assert.assertEquals(result, "Added task: Task1");
    }

    /**
     * Добавление задачи. Задача с пробелом
     */
    @Test
    public void testAddTaskWithSpace(){
        String result = requestHandler.handle("/add Go to");

        Assert.assertEquals(result, "Added task: Go to");
    }

    /**
     * Получаем помощь по командам
     */
    @Test
    public void testGetHelp(){
        String result = requestHandler.handle("/help");

        Assert.assertEquals(result, "/add [text] - You can add task.\n\ttext - task's text\n" +
                "/del [task_id] - You can delete task.\n\ttask_id - Task's id\n" +
                "/show - You can see all tasks\n" +
                "/start - You can start chatting with bot\n" +
                "/help - You will see this message");
    }

    /**
     * Проверям, что бот корректно начинает диалог
     */
    @Test
    public void testStart(){
        String result = requestHandler.handle("/start");

        Assert.assertEquals(result, "I'm ready for work!");
    }

    /**
     * Удаляем задачу с существующим идентификатором
     */
    @Test
    public void testDeleteTaskWithExistingID(){
        requestHandler.handle("/add Task1");
        String result = requestHandler.handle("/del 0");

        Assert.assertEquals(result, "Successfully deleted task with id: 0");
    }

    /**
     * Пробуем удалить задачу без указания идентификатора
     */
    @Test
    public void testDeleteTaskWithoutID(){
        String result = requestHandler.handle("/del");

        Assert.assertEquals(result, "Please enter not empty task id");
    }

    /**
     * Пробуем удалить задачу с несуществующим идентификатором
     */
    @Test
    public void testDeleteTaskWithNotExistingID(){
        String result = requestHandler.handle("/del 123");

        Assert.assertEquals(result, "There is no task with id: 123");
    }

    /**
     * Пробуем удалить задачу с некорректным типом идентификатора
     */
    @Test
    public void testDeleteTaskWithIncorrectTypeOfID(){
        String result = requestHandler.handle("/del som");

        Assert.assertEquals(result, "Please enter tasks id, not description");
    }
}
