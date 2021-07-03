package com.example.azureservicebusexample.utils;

import java.util.*;

public class EnumUtil {
    public static List values(Class... collection) {
        List<Class> list = Arrays.asList(collection);
        return values(list);
    }

    public static List values(Collection<Class> collection) {
        List values = new LinkedList();
        for (Class cls : collection) {
            try {
                for (Object enumObj : EnumSet.allOf(cls)) {
                    values.add(enumObj);
                }
            } catch (Exception exception) {
            }
        }
        return values;
    }
}
