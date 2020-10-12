package com.core;

import org.junit.Assert;
import org.junit.Test;

public class TaskTest {
    @Test
    public void taskDescriptionTest(){
        Task task = new Task("some description");
        Assert.assertEquals("some description", task.getDescription());
    }
}
