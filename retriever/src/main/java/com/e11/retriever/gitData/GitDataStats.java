package com.e11.retriever.gitData;

import java.util.List;

public class GitDataStats {
    List<GitData> gitData;
    Double maxChangeSets;
    Double avgChangeSets;
    Double maxCodeChurn;
    Double avgCodeChurn;

    public GitDataStats(){}

    public GitDataStats(List<GitData> gitData) {
        this.gitData = gitData;

        maxChangeSets = gitData.stream().mapToDouble(GitData::getChangeSets).max().getAsDouble();
        avgChangeSets = gitData.stream().mapToDouble(GitData::getChangeSets).average().getAsDouble();
        maxCodeChurn = gitData.stream().mapToDouble(GitData::getCodeChurn).max().getAsDouble();
        avgCodeChurn = gitData.stream().mapToDouble(GitData::getCodeChurn).average().getAsDouble();
    }

    public List<GitData> getGitData() {
        return gitData;
    }

    public void setGitData(List<GitData> gitData) {
        this.gitData = gitData;
    }

    public double getMaxChangeSets() {
        return maxChangeSets;
    }

    public void setMaxChangeSets(double maxChangeSets) {
        this.maxChangeSets = maxChangeSets;
    }

    public double getAvgChangeSets() {
        return avgChangeSets;
    }

    public void setAvgChangeSets(double avgChangeSets) {
        this.avgChangeSets = avgChangeSets;
    }

    public double getMaxCodeChurn() {
        return maxCodeChurn;
    }

    public void setMaxCodeChurn(double maxCodeChurn) {
        this.maxCodeChurn = maxCodeChurn;
    }

    public double getAvgCodeChurn() {
        return avgCodeChurn;
    }

    public void setAvgCodeChurn(double avgCodeChurn) {
        this.avgCodeChurn = avgCodeChurn;
    }
}
