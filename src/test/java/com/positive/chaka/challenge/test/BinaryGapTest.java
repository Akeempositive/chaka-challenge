package com.positive.chaka.challenge.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryGapTest {

    BinaryGap binaryGap = new BinaryGap();

    @Test
    public void accuracyTest(){
        List<Integer> results = new ArrayList<>();
        List<Integer> expected = Arrays.asList(new Integer []{0,0,0,0,1,0,0,0,2,1,1,0,1,0,0,0,3,2,2,1,1,1,1,0,2,1,1,0,1,0,0,0,4,3,3,2,2,2,2,1,2,1,1,1,1,1,1,0,3,2,2,1,1,1,1,0,2,1,1,0,1,0,0,0,5,4,4,3,3,3,3,2,2,2,2,2,2,2,2,1,3,2,2,1,1,1,1,1,2,1,1,1,1,1,1,0,4,3,3,2});
        int i =1;
        while(i<= 100){
            results.add(BinaryGap.maxBinaryGap(i));
            i++;
        }
        Assert.assertTrue("Solution did not pass accuracy tests",results.equals(expected));
    }

    @Test
    public void efficiencyTest(){
        int i =1;
        Long timerStart = System.currentTimeMillis();
        while(i<= 10000 && System.currentTimeMillis() - timerStart < 200){
            BinaryGap.maxBinaryGap(i);
            i++;
        }
        Assert.assertTrue("Efficiency test failed", i > 10000);
    }

    @Test
    public void versatilityTest(){

    }

    @Test
    public void robustnessTest(){
        int i =1;
        while(i< 10000000){
            System.out.println(binaryGap.maxBinaryGap(i));
            i++;
        }
    }
}
