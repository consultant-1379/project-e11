package com.e11.csvreader.gitData;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class GitData {
    @Id
    private String id;
    private String repoName;
    private LocalDateTime commitDate;
    private int numInserts;
    private int numDeletes;
    private int numFiles;
    private int changeSets;
    private int codeChurn;
    private double hunksCount;
    private int contributorCount;
    private int minorContributors;
    private String contributorNames;

    public GitData(String id, String repoName, LocalDateTime commitDate, int numInserts, int numDeletes, int numFiles, int codeChurn, int changeSets, double hunksCount, int contributorCount, int minorContributors, String contributorNames) {
        this.id = id;
        this.repoName = repoName;
        this.commitDate = commitDate;
        this.numInserts = numInserts;
        this.numDeletes = numDeletes;
        this.numFiles = numFiles;
        this.codeChurn = codeChurn;
        this.changeSets = changeSets;
        this.hunksCount = hunksCount;
        this.contributorCount = contributorCount;
        this.minorContributors = minorContributors;
        this.contributorNames = contributorNames;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "GitData{" +
                "id='" + id + '\'' +
                ", repoName='" + repoName + '\'' +
                ", commitDate=" + commitDate +
                ", numInserts=" + numInserts +
                ", numDeletes=" + numDeletes +
                ", numFiles=" + numFiles +
                ", codeChurn=" + codeChurn +
                ", changeSets=" + changeSets +
                ", hunksCount=" + hunksCount +
                ", contributorCount=" + contributorCount +
                ", minorContributors=" + minorContributors +
                ", contributorNames='" + contributorNames + '\'' +
                '}';
    }
}
