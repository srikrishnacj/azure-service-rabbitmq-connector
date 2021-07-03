package com.example.azureservicebusexample.utils;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class YmlUtil {
    public static List<Map<String, Object>> loadAsList(String classPathResource) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:" + classPathResource);

        InputStream stream = resource.getInputStream();
        return (List<Map<String, Object>>) new Yaml().load(stream);
    }
}
