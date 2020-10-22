package com.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Тестирование класса Core
 * @author Dmitry
 */
public class CoreTest {
    private Core core;

    @Before
    public void setUp(){ core = new Core(); }

    /**
     * Попытка удаления существующей задачи
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test
    public void deleteExistingTaskTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.addTask("First");
        core.deleteTask("0");
        String result = core.getTasks();
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    /**
     * Попытка удаления задачи, Id которой не записан в списке задач
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test(expected = NotExistingTaskIndexException.class)
    public void deleteNotExistingTaskTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.addTask("Do");
        core.deleteTask("2");
    }

    /**
     * Попытка удаления задачи с указанием некорректного Id
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test(expected = IncorrectTaskIdTypeException.class)
    public void deleteTaskWithIncorrectArgument() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.deleteTask("do");
    }

    /**
     * Проверка списка задач без добавления в него чего-либо
     */
    @Test
    public void showEmptyTaskListTest(){
        String result = core.getTasks();
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    /**
     * Проверка списка задач после добавления одной задачи
     */
    @Test
    public void showNotEmptyTaskList(){
        core.addTask("Do something");
        String result = core.getTasks();
        Assert.assertEquals("Id\tОписание\n0\tDo something", result);
    }
}
