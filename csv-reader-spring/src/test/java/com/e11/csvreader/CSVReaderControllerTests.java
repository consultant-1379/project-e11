package com.e11.csvreader;

import com.e11.csvreader.controllers.CSVReaderController;
import com.e11.csvreader.gitData.GitData;
import com.e11.csvreader.gitData.GitDataRepo;
import com.e11.csvreader.service.CSVReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CSVReaderControllerTests {

    @Mock
    private GitDataRepo gitDataRepo;

    @Mock
    private CSVReaderService csvReaderService;

    private CSVReaderController csvReaderController;

    private List<GitData> someMockedDataList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        csvReaderController = new CSVReaderController();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");

        GitData commit = new GitData(
                "0"
                ,"the-repo-name"
                , LocalDateTime.parse("2021-09-13 21:31:46+05:30",formatter),
                0
                ,0
                ,0
                ,0
                ,0
                ,0,
                0,
                0
                ,"name1");
        GitData commit2 = new GitData(
                "1"
                ,"the-repo-name"
                , LocalDateTime.parse("2021-10-13 21:31:46+05:30",formatter),
                0
                ,0
                ,0
                ,0
                ,0
                ,0,
                0,
                0
                ,"name2");
        someMockedDataList = new ArrayList<>();
        someMockedDataList.add(commit);
        someMockedDataList.add(commit2);
        csvReaderController.gitDataRepo = gitDataRepo;
        csvReaderController.csvReaderService = csvReaderService;
    }

    @Test
    public void testRun() {
        // Mock behavior of CSVReaderService
        when(csvReaderService.readCsvFile(anyInt(),anyString())).thenReturn(someMockedDataList);

        // Call the run method
        csvReaderController.run();

        // Verify that methods were called as expected
        verify(csvReaderService, times(5)).readCsvFile(anyInt(),anyString());
        verify(gitDataRepo, times(someMockedDataList.size() * 5)).save(any());
    }
}
