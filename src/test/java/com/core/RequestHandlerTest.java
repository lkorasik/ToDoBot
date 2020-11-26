package com.core;

import org.glassfish.grizzly.PendingWriteQueueLimitExceededException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import javax.xml.transform.sax.SAXResult;
import java.text.ParseException;

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
    public void testStartChat() throws ParseException {
        String uid = String.valueOf((int)(Math.random() * 1000));

        String result = requestHandler.handle(uid, "0", "/start", null);
        Assert.assertEquals("Hello, I'm telegram bot that can help to manage your tasks. There is all commands that you can type to operate with me:\n" +
                        "/add - You can add task. In next message send your task.\n" +
                        "/del - You can delete task. In next message send task's number\n" +
                        "/done - You can mark task as completed. In next message send task's number\n" +
                        "/showtodo - You can see all tasks that you need to solve\n" +
                        "/showdone - You can see all tasks that you have done\n" +
                        "/help - You will see this message\n" +
                        "/clear - You can clear your both task lists\n" +
                        "/cancel - You can use this command if you want to cancel action such as add task or delete task.\n",
                result);
    }

    /**
     * Попытка начать диалог два раза
     */
    @Test
    public void testStartChatTwice() throws ParseException {
        String uid = String.valueOf((int)(Math.random() * 1000));

        String result = requestHandler.handle(uid, "0", "/start", null);
        Assert.assertEquals("Hello, I'm telegram bot that can help to manage your tasks. There is all commands that you can type to operate with me:\n" +
                "/add - You can add task. In next message send your task.\n" +
                "/del - You can delete task. In next message send task's number\n" +
                "/done - You can mark task as completed. In next message send task's number\n" +
                "/showtodo - You can see all tasks that you need to solve\n" +
                "/showdone - You can see all tasks that you have done\n" +
                "/help - You will see this message\n" +
                "/clear - You can clear your both task lists\n" +
                "/cancel - You can use this command if you want to cancel action such as add task or delete task.\n", result);

        result = requestHandler.handle(uid, "0", "/start", null);
        Assert.assertEquals("Incorrect command", result);
    }

    /**
     * Получаем помощь по командам
     */
    @Test
    public void testGetHelp() throws ParseException {
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        String result = requestHandler.handle(uid, "0", "/help", null);

        Assert.assertEquals(result, "/add - You can add task. In next message send your task.\n" +
                "/del - You can delete task. In next message send task's number\n" +
                "/done - You can mark task as completed. In next message send task's number\n" +
                "/showtodo - You can see all tasks that you need to solve\n" +
                "/showdone - You can see all tasks that you have done\n" +
                "/help - You will see this message\n" +
                "/clear - You can clear your both task lists\n" +
                "/cancel - You can use this command if you want to cancel action such as add task or delete task.\n");
    }

    /**
     * Проверяем, что можно добавить задачу
     */
    @Test
    public void testAddTaskSuccessful() throws ParseException {
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        String result = requestHandler.handle(uid, "0", "/add", null);

        Assert.assertEquals("Please enter task description", result);
    }

    /**
     * Проверяем, что можено отказаться от добавления задачи
     */
    @Test
    public void testAddTaskCanceled() throws ParseException{
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/add", null);
        String result = requestHandler.handle(uid, "0", "/cancel", null);

        Assert.assertEquals("I'm waiting your commands", result);
    }

    /**
     * Удаляем задачу с существующим идентификатором
     */
    @Test
    public void testDeleteTaskSuccessful() throws ParseException{
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/add", null);
        requestHandler.handle(uid, "0", "Nothing", null);
        requestHandler.handle(uid, "0", "/del", null);
        String result = requestHandler.handle(uid, "0", "0", null);

        Assert.assertEquals("Successfully deleted task with id: 0", result);
    }

    /**
     * Отменяем удаление задачи
     */
    @Test
    public void testDeleteTaskCanceled() throws ParseException{
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/del", null);
        String result = requestHandler.handle(uid, "0", "/cancel", null);

        Assert.assertEquals("I'm waiting your commands", result);
    }

    /**
     * Пробуем удалить задачу с не тем id
     */
    @Test
    public void testDeleteTaskAnotherID() throws ParseException{
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/del", null);
        String result = requestHandler.handle(uid, "0", "48", null);

        Assert.assertEquals("There is no task with id: 48", result);
    }

    /**
     * Пробуем удалить задачу с некорректным типом идентификатора
     */
    @Test
    public void testDeleteTaskWithIncorrectTypeOfID() throws ParseException{
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/del", null);
        String result = requestHandler.handle(uid, "0", "FEA15", null);

        Assert.assertEquals("Please enter tasks id, not description", result);
    }

    /**
     * Пытаемся посмотреть пустой список задач
     */
    @Test
    public void testGetEmptyTaskList() throws ParseException {
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        String result = requestHandler.handle(uid, "0", "/show", null);

        Assert.assertEquals("Incorrect command", result);
    }

    /**
     * Смотрим список задач
     */
    @Test
    public void testGetTaskList() throws ParseException {
        String uid = String.valueOf((int)(Math.random() * 1000));

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/add", null);
        requestHandler.handle(uid, "0", "Something", null);
        String result = requestHandler.handle(uid, "0", "/showtodo", null);

        Assert.assertEquals("Id\tОписание\n" +
                "0\tSomething", result);
    }

    /**
     * Пытаемся перескочить через одно состояние
     */
    @Test
    public void testEPToAdd() throws ParseException {
        String uid = String.valueOf((int)(Math.random() * 1000));

        String result = requestHandler.handle(uid, "0", "/add", null);
        Assert.assertEquals("Enter /start", result);
    }
}
