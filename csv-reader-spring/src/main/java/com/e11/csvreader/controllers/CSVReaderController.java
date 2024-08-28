package com.e11.csvreader.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.e11.csvreader.service.CSVReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.e11.csvreader.gitData.GitDataRepo;
import com.e11.csvreader.gitData.GitData;

@RestController
public class CSVReaderController {

    @Autowired
    public GitDataRepo gitDataRepo;

    @Autowired
    public CSVReaderService csvReaderService;

    private int tempCounter = 2;
    private List<Integer> previousLinesSkipped = new ArrayList<>();
    private List<Integer> linesToSkip = new ArrayList<>();
    private List<String> fileNames = Arrays.asList("/data/mining1.csv","/data/mining2.csv","/data/mining3.csv","/data/mining4.csv","/data/mining5.csv");

    public CSVReaderController(){
        for(String file: fileNames){
            linesToSkip.add(1);
            previousLinesSkipped.add(0);
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void run(){
        for(int i = 0; i < fileNames.size(); i++){
            if(Objects.equals(linesToSkip.get(i), previousLinesSkipped.get(i))){
                System.out.println("No new lines to read from file " + fileNames.get(i));
            }
            else {
                List<GitData> results = csvReaderService.readCsvFile(linesToSkip.get(i),fileNames.get(i));
                previousLinesSkipped.set(i, linesToSkip.get(i));
                linesToSkip.set(i, linesToSkip.get(i)+results.size());
                results.forEach(e -> e.setId(String.valueOf(tempCounter++)));

                results.forEach(item -> {
                    System.out.println(item);
                    gitDataRepo.save(item);
                });
//            System.out.println("adding " + results.size() + " to linesToSkip");
            }
        }

    }
}
