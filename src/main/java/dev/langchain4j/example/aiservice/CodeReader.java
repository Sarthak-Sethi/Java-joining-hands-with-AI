package dev.langchain4j.example.aiservice;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class CodeReader {
    public static String readClass(Class<?> clazz) {
        String path = "/Users/sarthaksethi/intellij projects/langchain4j-examples/spring-boot-example/src/main/java/" + clazz.getName().replace('.', '/')  + ".java";
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            return "Could not read source for " + clazz.getName();
        }
    }
}