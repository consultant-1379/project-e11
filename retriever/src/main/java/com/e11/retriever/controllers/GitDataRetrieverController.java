package com.e11.retriever.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.e11.retriever.gitData.GitDataRepo;
import com.e11.retriever.gitData.GitDataStats;
import com.e11.retriever.gitData.GitData;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class GitDataRetrieverController {

    @Autowired
    GitDataRepo gitDataRepo;

    @GetMapping(value = "/gitData", produces = "application/json")
    public ResponseEntity<GitDataStats> getAllStats() {

        return ResponseEntity.status(HttpStatus.OK).body(new GitDataStats(gitDataRepo.findAll()));
        
    }

    @GetMapping(value = "/gitData/repoNames", produces = "application/json")
    public ResponseEntity<List<String>> getAllRepoNames() {

        List<String> repoNames = gitDataRepo.findAll()
                .stream()
                .map(GitData::getRepoName)
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(repoNames);
        
    }

    @GetMapping(value = "/gitData/{id}", produces = { "application/json" })
    public ResponseEntity<GitData> getById(@PathVariable String id) {

        GitData gd = gitDataRepo.findById(id).orElse(null);

        if (gd == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(gd);
    }

    @GetMapping(value = "/gitData/withDates", produces = "application/json")
    public ResponseEntity<GitDataStats> getAllInPeriod(@RequestParam("from") String fromDate,
                                                        @RequestParam("to") String toDate) {

        LocalDateTime ldtFromDate = LocalDateTime.parse(fromDate+"T00:00:00");
        LocalDateTime ldtToDate = LocalDateTime.parse(toDate+"T23:59:59");
        List<GitData> result = gitDataRepo.findAll()
                .stream()
                .filter(x -> x.getCommitDate() != null
                        && x.getCommitDate().isAfter(ldtFromDate)
                        && x.getCommitDate().isBefore(ldtToDate))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new GitDataStats(result));

    }

    @GetMapping(value = "/gitData/byRepo/{repoName}", produces = { "application/json" })
    public ResponseEntity<GitDataStats> getByRepoName(@PathVariable String repoName) {

        List<GitData> gd = gitDataRepo.findGitDataByRepoName(repoName);

        if (gd == null || gd.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new GitDataStats(gd));
    }

    @GetMapping(value = "/gitData/byRepo/{repoName}/withDates", produces = { "application/json" })
    public ResponseEntity<GitDataStats> getByRepoName(@PathVariable String repoName, 
                                                        @RequestParam("from") String fromDate,
                                                        @RequestParam("to") String toDate) {

        List<GitData> gd = gitDataRepo.findGitDataByRepoName(repoName);
        LocalDateTime ldtFromDate = LocalDateTime.parse(fromDate+"T00:00:00");
        LocalDateTime ldtToDate = LocalDateTime.parse(toDate+"T23:59:59");
        List<GitData> result = gd.stream()
                .filter(commit -> commit.getCommitDate() != null
                        && commit.getCommitDate().isAfter(ldtFromDate)
                        && commit.getCommitDate().isBefore(ldtToDate))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new GitDataStats(result));
    }

}
