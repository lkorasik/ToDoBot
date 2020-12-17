package com.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicBoolean;

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
        this.requestHandler = new RequestHandler("testfile.json");
    }

    /**
     * Начало общения с ботом
     */
    @Test
    public void testStartChat() throws ParseException {
        var uid = "0";

        var result = requestHandler.handle(uid, "0", "/start", null);
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
        var uid = "1";

        var result = requestHandler.handle(uid, "0", "/start", null);
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
        var uid = "2";

        requestHandler.handle(uid, "0", "/start", null);
        var result = requestHandler.handle(uid, "0", "/help", null);

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
        var uid = "3";

        requestHandler.handle(uid, "0", "/start", null);
        var result = requestHandler.handle(uid, "0", "/add", null);

        Assert.assertEquals("Please enter task description", result);
    }

    /**
     * Проверяем, что можено отказаться от добавления задачи
     */
    @Test
    public void testAddTaskCanceled() throws ParseException{
        var uid = "4";

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/add", null);
        var result = requestHandler.handle(uid, "0", "/cancel", null);

        Assert.assertEquals("I'm waiting your commands", result);
    }

    /**
     * Удаляем задачу с существующим идентификатором
     */
    @Test
    public void testDeleteTaskSuccessful() throws ParseException{
        var uid = "5";

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/add", null);
        requestHandler.handle(uid, "0", "Nothing", null);
        requestHandler.handle(uid, "0", "/del", null);
        var result = requestHandler.handle(uid, "0", "0", null);

        Assert.assertEquals("Successfully deleted task with id: 0", result);
    }

    /**
     * Отменяем удаление задачи
     */
    @Test
    public void testDeleteTaskCanceled() throws ParseException{
        var uid = "6";

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/del", null);
        var result = requestHandler.handle(uid, "0", "/cancel", null);

        Assert.assertEquals("I'm waiting your commands", result);
    }

    /**
     * Пробуем удалить задачу с не тем id
     */
    @Test
    public void testDeleteTaskAnotherID() throws ParseException{
        var uid = "7";
        
        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/del", null);
        var result = requestHandler.handle(uid, "0", "48", null);

        Assert.assertEquals("There is no task with id: 48", result);
    }

    /**
     * Пробуем удалить задачу с некорректным типом идентификатора
     */
    @Test
    public void testDeleteTaskWithIncorrectTypeOfID() throws ParseException{
        var uid = "8";

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/del", null);
        var result = requestHandler.handle(uid, "0", "FEA15", null);

        Assert.assertEquals("Please enter tasks id, not description", result);
    }

    /**
     * Пытаемся посмотреть пустой список задач
     */
    @Test
    public void testGetEmptyTaskList() throws ParseException {
        var uid = "9";

        requestHandler.handle(uid, "0", "/start", null);
        var result = requestHandler.handle(uid, "0", "/show", null);

        Assert.assertEquals("Incorrect command", result);
    }

    /**
     * Смотрим список задач
     */
    @Test
    public void testGetTaskList() throws ParseException {
        var uid = "10";

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/add", null);
        requestHandler.handle(uid, "0", "Something", null);
        var result = requestHandler.handle(uid, "0", "/showtodo", null);

        Assert.assertEquals("Id\tОписание\n" +
                "0\tSomething", result);
    }

    /**
     * Пытаемся перескочить через одно состояние
     */
    @Test
    public void testEPToAdd() throws ParseException {
        var uid = "11";

        var result = requestHandler.handle(uid, "0", "/add", null);
        Assert.assertEquals("Enter /start", result);
    }

    @Test
    public void testSetTimer() throws ParseException, InterruptedException {
        var uid = "12";

        AtomicBoolean called = new AtomicBoolean(false);

        requestHandler.handle(uid, "0", "/start", null);
        requestHandler.handle(uid, "0", "/add", null);
        requestHandler.handle(uid, "0", "Test", null);
        requestHandler.handle(uid, "0", "/setnotif", null);
        var result = requestHandler.handle(uid, "0", "0 1 sec", (a, b) -> called.set(true));
        Assert.assertEquals("Notification has been added", result);
        Thread.sleep(1500);
        Assert.assertTrue(called.get());
    }

    @Test
    public void testDualStart() throws ParseException {
        var uid1 = "13";
        var uid2 = "14";

        var result1 = requestHandler.handle(uid1, "0", "/start", null);
        var result2 = requestHandler.handle(uid2, "1", "/start", null);

        Assert.assertEquals("Hello, I'm telegram bot that can help to manage your tasks. There is all commands that you can type to operate with me:\n" +
                "/add - You can add task. In next message send your task.\n" +
                "/del - You can delete task. In next message send task's number\n" +
                "/done - You can mark task as completed. In next message send task's number\n" +
                "/showtodo - You can see all tasks that you need to solve\n" +
                "/showdone - You can see all tasks that you have done\n" +
                "/help - You will see this message\n" +
                "/clear - You can clear your both task lists\n" +
                "/cancel - You can use this command if you want to cancel action such as add task or delete task.\n", result1);
        Assert.assertEquals("Hello, I'm telegram bot that can help to manage your tasks. There is all commands that you can type to operate with me:\n" +
                "/add - You can add task. In next message send your task.\n" +
                "/del - You can delete task. In next message send task's number\n" +
                "/done - You can mark task as completed. In next message send task's number\n" +
                "/showtodo - You can see all tasks that you need to solve\n" +
                "/showdone - You can see all tasks that you have done\n" +
                "/help - You will see this message\n" +
                "/clear - You can clear your both task lists\n" +
                "/cancel - You can use this command if you want to cancel action such as add task or delete task.\n", result2);
    }

    @Test
    public void testDualAdd() throws ParseException{
        var uid1 = "15";
        var uid2 = "16";

        requestHandler.handle(uid1, "0", "/start", null);
        requestHandler.handle(uid2, "1", "/start", null);

        requestHandler.handle(uid1, "0", "/add", null);
        requestHandler.handle(uid2, "1", "/add", null);

        requestHandler.handle(uid2, "1", "Task2", null);
        requestHandler.handle(uid1, "0", "Task1", null);

        var result1 = requestHandler.handle(uid1, "0", "/showtodo", null);
        var result2 = requestHandler.handle(uid2, "1", "/showtodo", null);

        Assert.assertEquals("Id\tОписание\n0\tTask2", result2);
        Assert.assertEquals("Id\tОписание\n0\tTask1", result1);
    }
}
