package com.e11.csvreader.gitData;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GitDataRepo extends MongoRepository<GitData, String> {

    @Query("{'date' : { $gt : ?0, $lt : ?1}}")
    List<GitData> findGitDataInPeriod(LocalDateTime fromDate, LocalDateTime toDate);
}
