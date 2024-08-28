package com.e11.csvreader;

import com.e11.csvreader.gitData.GitData;
import com.e11.csvreader.service.CSVReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVReaderServiceTests {

    private String path = System.getProperty("user.dir") + "/data/mining1.csv";

    @Test
    public void testReadCsvFile() throws Exception {
        CSVReaderService csvReaderService = new CSVReaderService();

        List<GitData> commits = csvReaderService.readCsvFile(1,path);
        assertThat(commits.size(), greaterThan(0));
    }
    @Test
    public void testReadCsvFile_CatchesError() throws Exception {
        CSVReaderService csvReaderService = new CSVReaderService();
        List<GitData> commits = csvReaderService.readCsvFile(0,path);
        assertEquals(commits.size(), 0);
    }
}


