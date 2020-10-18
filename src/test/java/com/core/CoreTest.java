package com.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoreTest {
    private Core core;

    @Before
    public void setUp(){ core = new Core(); }


    @Test
    public void deleteExistingTaskTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.addTask("First");
        core.deleteTask("0");
    }

    @Test(expected = NotExistingTaskIndexException.class)
    public void deleteNotExistingTaskTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.addTask("Do");
        core.deleteTask("2");
    }

    @Test(expected = IncorrectTaskIdTypeException.class)
    public void deleteTaskWithIncorrectDescription() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.deleteTask("do");
    }

    @Test
    public void showEmptyTaskListTest(){
        String result = core.getTasks();
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    @Test
    public void showNotEmptyTaskList(){
        core.addTask("Do something");
        String result = core.getTasks();
        Assert.assertEquals("Id\tОписание\n0\tDo something", result);
    }
}
