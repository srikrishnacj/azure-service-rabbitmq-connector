package com.example.azureservicebusexample.samples.exception;

public interface ExceptionTranslator {
    RuntimeException translate(Throwable exception);
}