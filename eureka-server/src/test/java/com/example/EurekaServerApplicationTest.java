package com.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class EurekaServerApplicationTests extends TestCase {

    public EurekaServerApplicationTests(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(EurekaServerApplicationTests.class);
    }

    public void testAppStartsSuccessfully(){
        try{
            EurekaServerApplicationTests.main(new String[]{});
            assertTrue(true);
        } catch (Exception e){
            fail("A aplicação não iniciou corretamente.");
        }
    }
}
