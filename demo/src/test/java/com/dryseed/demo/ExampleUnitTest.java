package com.dryseed.demo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int MODULE_ID_QYPAGE = 1 << 22;
        System.out.println(Integer.toBinaryString(MODULE_ID_QYPAGE)); //10000000000000000000000
        System.out.println(MODULE_ID_QYPAGE); //4194304
        assertEquals(4, 2 + 2);
    }
}