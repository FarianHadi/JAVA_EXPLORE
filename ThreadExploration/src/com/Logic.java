package com;

import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logic{
    private String token;


    public Logic(String token){
        this.token = token;
    }
    public String getFileContent() {

        // The name of the file to open.
        String fileName = "sample.csv";
        try {
            Scanner fileScanner = new Scanner(new File("test.csv"));
            ExecutorService threadService = Executors.newFixedThreadPool(Math.max(1, (Runtime.getRuntime().availableProcessors())));

            while (fileScanner.hasNextLine()) {
                int i = 1;
                String line = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");
                Map<String, String> map = new HashMap<>();
                while (lineScanner.hasNext()) {
                    String data = lineScanner.next();
                    map.put(""+i, data);
                    i++;
                }
                threadService.execute(new APIRequest(map, token));
                lineScanner.close();
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return null;
    }
}

