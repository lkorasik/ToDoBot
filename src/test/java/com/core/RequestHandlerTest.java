package com.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 * @author Lev
 */
public class RequestHandlerTest {
    RequestHandler requestHandler;

    @Before
    public void setUp(){
        this.requestHandler = new RequestHandler();
    }

    @Test
    public void TestAddTask1(){
        String result = requestHandler.handle("/add Task1");

        Assert.assertEquals(result, Constants.TASK_ADDED_MSG + "Task1");
    }

    @Test
    public void TestAddTask2(){
        String result = requestHandler.handle("/add Go to");

        Assert.assertEquals(result, Constants.TASK_ADDED_MSG + "Go to");
    }

    @Test
    public void TestHelp(){
        String result = requestHandler.handle("/help");

        Assert.assertEquals(result, Constants.HELP_MSG);
    }

    @Test
    public void TestStart(){
        String result = requestHandler.handle("/start");

        Assert.assertEquals(result, Constants.START_MSG);
    }

    @Test
    public void TestDeleteTask1(){
        requestHandler.handle("/add Task1");
        String result = requestHandler.handle("/del 0");

        Assert.assertEquals(result, Constants.TASK_DELETED_MSG + "0");
    }

    @Test
    public void TestDeleteTask2(){
        String result = requestHandler.handle("/del");

        Assert.assertEquals(result, Constants.EMPTY_TASK_ID_MSG);
    }

    @Test
    public void TestDeleteTask3(){
        String result = requestHandler.handle("/del 123");

        Assert.assertEquals(result, Constants.NOT_EXISTING_TASK_ID_EXCEPTION_MSG + "123");
    }

    @Test
    public void TestDeleteTask4(){
        String result = requestHandler.handle("/del som");

        Assert.assertEquals(result, Constants.INCORRECT_TASK_ID_TYPE_EXCEPTION_MSG);
    }
}
