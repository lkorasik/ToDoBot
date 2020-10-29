package com.core;

import com.authentication.Authenticator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Тестирование класса Core
 * @author Dmitry
 */
public class CoreTest {
    private Core core;
    private String user;


    @Before
    public void setUp(){
        core = new Core();
        Authenticator auth = new Authenticator();
        auth.authenticate("user");
        user = auth.getUserId();
    }

    /**
     * Попытка удаления существующей задачи
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test
    public void deleteExistingTask() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.addTask(user,"First");
        core.deleteTask(user,"0");
        String result = core.getTasks(user);
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    /**
     * Попытка удаления задачи, Id которой не записан в списке задач
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test(expected = NotExistingTaskIndexException.class)
    public void deleteNotExistingTask() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.addTask(user,"Do");
        core.deleteTask(user,"2");
    }

    /**
     * Попытка удаления задачи с указанием некорректного Id
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test(expected = IncorrectTaskIdTypeException.class)
    public void deleteTaskWithIncorrectArgument() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.deleteTask(user,"do");
    }

    /**
     * Проверка списка задач без добавления в него чего-либо
     */
    @Test
    public void showEmptyTaskList(){
        String result = core.getTasks(user);
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    /**
     * Проверка списка задач после добавления одной задачи
     */
    @Test
    public void showNotEmptyTaskList(){
        core.addTask(user,"Do something");
        String result = core.getTasks(user);
        Assert.assertEquals("Id\tОписание\n0\tDo something", result);
    }

    /**
     * Проверка того, что у каждого пользователя свои и только свои задачи
     */
    @Test
    public void separatedTasksByUsers(){
        core.addTask(user, "Do work");
        core.addTask("newUser", "Do another work");
        String firstUserResult = core.getTasks(user);
        String secondUserResult = core.getTasks("newUser");
        Assert.assertEquals("Id\tОписание\n0\tDo work", firstUserResult);
        Assert.assertEquals("Id\tОписание\n0\tDo another work", secondUserResult);
    }
}
