package com.example.azureservicebusexample.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExceptionUtil {

    public interface RunnableWithException {
        void run() throws Exception;
    }

    public static void suppress(RunnableWithException runnableWithException) {
        try {
            runnableWithException.run();
        } catch (Exception e) {
            // do nothing
        }
    }

    public static List<String> formatToPrettyString(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);

        return Arrays.stream(sw.toString().split("\\r?\\n")).collect(Collectors.toList());
    }
}