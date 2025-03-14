package com.example.foobarquix.batch;

import com.example.foobarquix.service.FooBarQuixService;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileProcessor implements CommandLineRunner {

    private final FooBarQuixService fooBarQuixService;

    public FileProcessor(FooBarQuixService fooBarQuixService) {
        this.fooBarQuixService = fooBarQuixService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java -jar foobarquix.jar <inputFile> <outputFile>");
            return;
        }

        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        List<String> lines = FileUtils.readLines(inputFile, StandardCharsets.UTF_8);
        List<String> results = new java.util.ArrayList<>();

        for (String line : lines) {
            try {
                int number = Integer.parseInt(line.trim());
                results.add(line + " -> " + fooBarQuixService.transformerNombre(number));
            } catch (NumberFormatException e) {
                results.add("Erreur : '" + line + "' n'est pas un nombre valide.");
            }
        }

        FileUtils.writeLines(new File(args[1]), results);
        System.out.println("Fichier de sortie généré : " + args[1]);
    }
}
