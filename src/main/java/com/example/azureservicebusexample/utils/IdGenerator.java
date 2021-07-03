package com.example.azureservicebusexample.utils;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

/*
    Will be useful if we are representing object id as string as opposed to integers.
    This class will generate time series based UUIDs. Will help database indexing faster.
    String Id are common in document databases.
 */
public class IdGenerator {
    public static String id() {
        return Generators.timeBasedGenerator().generate().toString();
    }

    public static String id(int length) {
        String temp = id();
        while (temp.length() < length) {
            temp += UUID.randomUUID().toString();
        }
        return temp.replaceAll("-", "").substring(0, length);
    }

    public static String testId() {
        String id = id();
        return "(TEST)" + IdGenerator.id().substring(id.length() - 25);
    }
}
