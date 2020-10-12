package com.core;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoreTest {
    private Core core;
    @Before
    public void setUp(){ core = new Core(); }

    @After
    public void tearDown(){ Task.id_counter = 0; }

    @Test
    public void addTaskWithNormalDescriptionTest(){
        String result = core.addTask("Do something useful");
        Assert.assertEquals("Added task: Do something useful", result);
        Assert.assertEquals(1, core.getTasks().size());
    }

    @Test
    public void addTaskWithEmptyDescriptionTest(){
        String result = core.addTask("   ");
        Assert.assertEquals("Please enter not empty task description", result);
        Assert.assertEquals(0, core.getTasks().size());
    }

    @Test
    public void deleteExistingTaskTest(){
        String firstTaskResult = core.addTask("First");
        String deleteResult = core.deleteTask("1");
        Assert.assertEquals(0, core.getTasks().size());
        Assert.assertEquals("Successfully deleted tasks with id: 1", deleteResult);
    }

    @Test
    public void deleteNotExistingTaskTest(){
        String firstTaskResult = core.addTask("Do");
        String deleteResult = core.deleteTask("2");
        Assert.assertEquals(1, core.getTasks().size());
        Assert.assertEquals("There is no tasks with id: 2", deleteResult);
    }

    @Test
    public void showEmptyTaskListTest(){
        String result = core.showTasks();
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    @Test
    public void showNotEmptyTaskList(){
        String firstTaskResult = core.addTask("Do something");
        String result = core.showTasks();
        Assert.assertEquals("Id\tОписание\n1\tDo something", result);
    }
}
