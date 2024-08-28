package com.e11.retriever.gitData;
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

    public GitData(){}

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public LocalDateTime getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(LocalDateTime commitDate) {
        this.commitDate = commitDate;
    }

    public int getNumInserts() {
        return numInserts;
    }

    public void setNumInserts(int numInserts) {
        this.numInserts = numInserts;
    }

    public int getNumDeletes() {
        return numDeletes;
    }

    public void setNumDeletes(int numDeletes) {
        this.numDeletes = numDeletes;
    }

    public int getNumFiles() {
        return numFiles;
    }

    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }

    public int getCodeChurn() {
        return codeChurn;
    }

    public void setCodeChurn(int codeChurn) {
        this.codeChurn = codeChurn;
    }

    public int getChangeSets() {
        return changeSets;
    }

    public void setChangeSets(int changeSets) {
        this.changeSets = changeSets;
    }

    public double getHunksCount() {
        return hunksCount;
    }

    public void setHunksCount(double hunksCount) {
        this.hunksCount = hunksCount;
    }

    public int getContributorCount() {
        return contributorCount;
    }

    public void setContributorCount(int contributorCount) {
        this.contributorCount = contributorCount;
    }

    public int getMinorContributors() {
        return minorContributors;
    }

    public void setMinorContributors(int minorContributors) {
        this.minorContributors = minorContributors;
    }

    public String getContributorNames() {
        return contributorNames;
    }

    public void setContributorNames(String contributorNames) {
        this.contributorNames = contributorNames;
    }

}
