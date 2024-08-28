package com.e11.retriever.gitData;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GitDataRepo extends MongoRepository<GitData, String> {

    @Query("{repoName:'?0'}")
    List<GitData> findGitDataByRepoName(String repoName);
}
