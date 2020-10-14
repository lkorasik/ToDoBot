package com.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoreTest {
    private Core core;

    @Before
    public void setUp(){ core = new Core(); }


    @Test
    public void deleteExistingTaskTest() throws incorrectTaskIdTypeException, notExistingTaskIndexException {
        core.addTask("First");
        core.deleteTask("0");
    }

    @Test(expected = notExistingTaskIndexException.class)
    public void deleteNotExistingTaskTest() throws incorrectTaskIdTypeException, notExistingTaskIndexException {
        core.addTask("Do");
        core.deleteTask("2");
    }

    @Test(expected = incorrectTaskIdTypeException.class)
    public void deleteTaskWithIncorrectDEscription() throws incorrectTaskIdTypeException, notExistingTaskIndexException {
        core.deleteTask("do");
    }

    @Test
    public void showEmptyTaskListTest(){
        String result = core.showTasks();
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    @Test
    public void showNotEmptyTaskList(){
        core.addTask("Do something");
        String result = core.showTasks();
        Assert.assertEquals("Id\tОписание\n0\tDo something", result);
    }
}
