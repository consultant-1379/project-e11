package com.e11.csvreader.service;

import com.e11.csvreader.gitData.GitData;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CSVReaderService {
    public List<GitData> readCsvFile(int linesToSkip, String csvFilePath){
        try(BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

            String line;
            List<GitData> results = new ArrayList<>();
            List<String> linesSkipped = new ArrayList<>();
            for(int i = 0; i < linesToSkip; i++){
                String skippedLine = reader.readLine();
                linesSkipped.add(skippedLine);
            }
            while ((line = reader.readLine()) != null) {
                // Split the line into fields using the comma delimiter
                String[] fields = line.split(",");

                // Process each field as needed
                // For example, print the values of the first and second columns
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
                    String names = fields.length >= 11 ? fields[10] : "";
                    GitData commit = new GitData(
                            "0"
                            ,fields[0]
                            ,LocalDateTime.parse(fields[1],formatter),
                            Integer.parseInt(fields[2])
                            ,Integer.parseInt(fields[3])
                            ,Integer.parseInt(fields[4])
                            ,Integer.parseInt(fields[5])
                            ,Integer.parseInt(fields[6])
                            ,Double.parseDouble(fields[7]),
                            Integer.parseInt(fields[8]),
                            Integer.parseInt((fields[9]))
                            ,names);
                    results.add(commit);
            }
            System.out.println("Number of lines skipped" + linesSkipped.size());
            return results;
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return Collections.emptyList();
    }
}
