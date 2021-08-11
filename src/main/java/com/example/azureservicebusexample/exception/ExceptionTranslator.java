package com.example.azureservicebusexample.exception;

public interface ExceptionTranslator {
    RuntimeException translate(Throwable exception);
}