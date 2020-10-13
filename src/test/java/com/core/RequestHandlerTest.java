package com.core;

import org.junit.Assert;
import org.junit.Test;

public class RequestHandlerTest {
    @Test
    public void splitCommandWithArgTest(){
        RequestHandler requestHandler = new RequestHandler();

        var pc = requestHandler.split("/add Task1");

        Assert.assertEquals(pc.getCommand(), "/add");
        Assert.assertEquals(pc.getBody(), "Task1");
    }

    @Test
    public void splitCommandWithArgsTest(){
        RequestHandler requestHandler = new RequestHandler();

        var pc = requestHandler.split("/add Go to");

        Assert.assertEquals(pc.getCommand(), "/add");
        Assert.assertEquals(pc.getBody(), "Go to");
    }

    @Test
    public void splitCommandWithoutArgsTest(){
        RequestHandler requestHandler = new RequestHandler();

        var pc = requestHandler.split("/show");

        Assert.assertEquals(pc.getCommand(), "/show");
        Assert.assertNull(pc.getBody());
    }
}
