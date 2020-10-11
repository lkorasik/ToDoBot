package com.core;

import org.junit.Assert;
import org.junit.Test;

public class CommandSplitterTest {
    @Test
    public void splitCommandWithArgTest(){
        var pc = CommandSplitter.split("/add Task1");

        Assert.assertEquals(pc.getCommand(), "/add");
        Assert.assertEquals(pc.getBody(), "Task1");
    }

    @Test
    public void splitCommandWithArgsTest(){
        var pc = CommandSplitter.split("/add Go to");

        Assert.assertEquals(pc.getCommand(), "/add");
        Assert.assertEquals(pc.getBody(), "Go to");
    }

    @Test
    public void splitCommandWithoutArgsTest(){
        var pc = CommandSplitter.split("/show");

        Assert.assertEquals(pc.getCommand(), "/show");
        Assert.assertNull(pc.getBody());
    }
}
