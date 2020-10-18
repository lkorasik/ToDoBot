package com.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Lev
 */
public class ParsedCommandsTest {
    @Test
    public void parsedCommandsTest(){
        var pc = new ParsedCommand("A", "B");

        Assert.assertEquals(pc.getCommand(), "A");
        Assert.assertEquals(pc.getBody(), "B");
    }
}
