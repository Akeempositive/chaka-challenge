package com.positive.chaka.challenge.test;
import java.util.Scanner;

public class BinaryGap {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println(maxBinaryGap(Long.parseLong(sc.next())));
        sc.close();
    }

    public static int maxBinaryGap(long number){
        while(number % 2 ==0) number/=2;
        String convertToBase2 = Long.toBinaryString(number);
        String text = "0";
        int count =0;
        while(convertToBase2.contains(text)){
            text+="0";
            count++;
        }
        return count;
    }


}