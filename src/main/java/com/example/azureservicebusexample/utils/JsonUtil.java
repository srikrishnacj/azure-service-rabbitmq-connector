package com.example.azureservicebusexample.utils;

import com.google.gson.Gson;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Example for Type def:
 * private static final Type REVIEW_TYPE = new TypeToken<List<Review>>() { }.getType();
 */
public class JsonUtil {
    private static final Gson gson = new Gson();

    public static <T> T loadFromClasspath(String classPathResource, Type type) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:" + classPathResource);

        InputStream stream = resource.getInputStream();
        Reader reader = new InputStreamReader(stream, "UTF-8");
        return gson.fromJson(reader, type); // contains the whole reviews list
    }
}
