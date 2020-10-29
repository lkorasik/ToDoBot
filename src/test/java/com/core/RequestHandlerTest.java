package com.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import javax.xml.transform.sax.SAXResult;

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
     * Начало общения с ботом
     */
    @Test
    public void testStartChat(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        String result = requestHandler.handle(uid, "/start");
        Assert.assertEquals("Hello, I'm telegram bot that can help to manage your tasks. There is all commands that you can type to operate with me:\n" +
                "/add - You can add task.\n" +
                "\ttext - task's text\n" +
                "/del - You can delete task.\n" +
                "/show - You can see all tasks\n" +
                "/help - You will see this message", result);
    }

    /**
     * Попытка начать диалог два раза
     */
    @Test
    public void testStartChatTwice(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        String result = requestHandler.handle(uid, "/start");
        Assert.assertEquals("Hello, I'm telegram bot that can help to manage your tasks. " +
                "There is all commands that you can type to operate with me:\n" + "/add - You can add task.\n\ttext - task's text\n" +
                "/del - You can delete task.\n" +
                "/show - You can see all tasks\n" +
                "/help - You will see this message", result);

        result = requestHandler.handle(uid, "/start");
        Assert.assertEquals("Incorrect command", result);
    }

    /**
     * Получаем помощь по командам
     */
    @Test
    public void testGetHelp(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        String result = requestHandler.handle(uid, "/help");

        Assert.assertEquals(result, "/add - You can add task.\n\ttext - task's text\n" +
                "/del - You can delete task.\n" +
                "/show - You can see all tasks\n" +
                "/help - You will see this message");
    }

    /**
     * Проверяем, что можно добавить задачу
     */
    @Test
    public void testAddTaskSuccessful(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        String result = requestHandler.handle(uid, "/add");

        Assert.assertEquals("Please enter task description", result);
    }

    /**
     * Проверяем, что можено отказаться от добавления задачи
     */
    @Test
    public void testAddTaskCanceled(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        requestHandler.handle(uid, "/add");
        String result = requestHandler.handle(uid, "/cancel");

        Assert.assertEquals("I'm waiting your commands", result);
    }

    /**
     * Удаляем задачу с существующим идентификатором
     */
    @Test
    public void testDeleteTaskSuccessful(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        requestHandler.handle(uid, "/add");
        requestHandler.handle(uid, "Nothing");
        requestHandler.handle(uid, "/del");
        String result = requestHandler.handle(uid, "0");

        Assert.assertEquals("Successfully deleted task with id: 0", result);
    }

    /**
     * Отменяем удаление задачи
     */
    @Test
    public void testDeleteTaskCanceled(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        requestHandler.handle(uid, "/del");
        String result = requestHandler.handle(uid, "/cancel");

        Assert.assertEquals("I'm waiting your commands", result);
    }

    /**
     * Пробуем удалить задачу с не тем id
     */
    @Test
    public void testDeleteTaskAnotherID(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        requestHandler.handle(uid, "/del");
        String result = requestHandler.handle(uid, "48");

        Assert.assertEquals("There is no task with id: 48", result);
    }

    /**
     * Пробуем удалить задачу с некорректным типом идентификатора
     */
    @Test
    public void testDeleteTaskWithIncorrectTypeOfID(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        requestHandler.handle(uid, "/del");
        String result = requestHandler.handle(uid, "FEA15");

        Assert.assertEquals("Please enter tasks id, not description", result);
    }

    /**
     * Пытаемся посмотреть пустой список задач
     */
    @Test
    public void testGetEmptyTaskList(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        String result = requestHandler.handle(uid, "/show");

        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    /**
     * Смотрим список задач
     */
    @Test
    public void testGetTaskList(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "/start");
        requestHandler.handle(uid, "/add");
        requestHandler.handle(uid, "Something");
        String result = requestHandler.handle(uid, "/show");

        Assert.assertEquals("Id\tОписание\n" +
                "0\tSomething", result);
    }

    /**
     * Пытаемся перескочить через одно состояние
     */
    @Test
    public void testEPToAdd(){
        String uid = String.valueOf((int)(Math.random() * 1000));

        String result = requestHandler.handle(uid, "/add");
        Assert.assertEquals("Enter /start", result);
    }
}
