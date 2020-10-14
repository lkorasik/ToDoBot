package com.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class RequestHandlerTest {
    RequestHandler requestHandler;

    @Before
    public void setUp(){
        this.requestHandler = new RequestHandler();
    }

    @Test
    public void splitCommandWithArgTest(){
        var pc = requestHandler.split("/add Task1");

        Assert.assertEquals(pc.getCommand(), "/add");
        Assert.assertEquals(pc.getBody(), "Task1");
    }

    @Test
    public void splitCommandWithArgsTest(){
        var pc = requestHandler.split("/add Go to");

        Assert.assertEquals(pc.getCommand(), "/add");
        Assert.assertEquals(pc.getBody(), "Go to");
    }

    @Test
    public void splitCommandWithoutArgsTest(){
        var pc = requestHandler.split("/show");

        Assert.assertEquals(pc.getCommand(), "/show");
        Assert.assertNull(pc.getBody());
    }

    @Test
    public void requestAddCorrectTaskHandlingTest(){
        Core core = new Core();
        String response = requestHandler.handle(core, requestHandler.split("/add Task0"));
        Assert.assertEquals("Added task: Task0", response);
    }

    @Test
    public void requestAddTaskWithEmptyDescriptionTest(){
        Core core = new Core();
        String response = requestHandler.handle(core, requestHandler.split("/add    "));
        Assert.assertEquals("Please enter not empty task description", response);
    }

    @Test
    public void requestDeleteCorrectTaskTest(){
        Core core = new Core();
        core.addTask("Task0");
        String response = requestHandler.handle(core, requestHandler.split("/del 0"));
        Assert.assertEquals("Successfully deleted task with id: 0", response);
    }

    @Test
    public void requestDeleteTaskWithEmptyArgument(){
        Core core = new Core();
        String response = requestHandler.handle(core, requestHandler.split("/del    "));
        Assert.assertEquals("Please enter not empty task id", response);
    }
}
