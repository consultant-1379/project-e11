package com.e11.retriever;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.e11.retriever.gitData.GitData;
import com.e11.retriever.gitData.GitDataRepo;

@EnableMongoRepositories(basePackageClasses = GitDataRepo.class)
@Configuration
public class MongoConfig {

    /*
     * Class for populating mongo with sample data
     * Useful for testing, will probably remove by end of project
     */
    

    @Bean
    CommandLineRunner commandLineRunner(GitDataRepo gitDataRepo) throws ParseException {

        return strings -> {
            gitDataRepo.save(new GitData("0","repo-name",LocalDateTime.of(2022,11,11,11,11),0,0,0,0,0,0,0,0,""));
            gitDataRepo.save(new GitData("1","repo-name",LocalDateTime.of(2021,11,11,11,11),0,0,0,0,0,0,0,0,""));
        };
    }

    
}