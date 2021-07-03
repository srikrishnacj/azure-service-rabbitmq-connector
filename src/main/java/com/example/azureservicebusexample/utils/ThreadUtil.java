package com.example.azureservicebusexample.utils;

public class ThreadUtil {
    public static void sleep(int sleepInMilliSeconds) {
        try {
            Thread.sleep(sleepInMilliSeconds);
        } catch (InterruptedException e) {

        }
    }
}
