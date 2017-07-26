package com.kevinyin.lnetty.tesjava;

import org.junit.Test;

/**
 * Created by kevinyin on 2017/7/26.
 */
public class TestJ1 {

    @Test
    public void test1(){
        A c1 = new A();
        A c2 = new A();
        System.out.println(c1 == c2);
        System.out.println(c1.equals(c2));
    }

    class A{

    }
}
