package com.e11.retriever;

import com.e11.retriever.gitData.GitData;
import com.e11.retriever.gitData.GitDataStats;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GitDataRetrieverTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String id;
    private static List<String> repoNames;

    @Test
    @Order(1)
    public void testGetAllGitData(){
        ResponseEntity<GitDataStats> resp = testRestTemplate.exchange("/gitData", HttpMethod.GET, null,
                new ParameterizedTypeReference<GitDataStats>() {});

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        GitDataStats gitData = resp.getBody();
        assert gitData != null;
        assertThat(gitData.getGitData().size(),greaterThan(0));
    }

    @Test
    @Order(2)
    public void testGetGitData(){
        id = "1";
        ResponseEntity<GitData> responseEntity = testRestTemplate.getForEntity("/gitData/" + id, GitData.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        GitData ps = responseEntity.getBody();
        assert ps != null;
        assertEquals(id,ps.getId());
    }

    @Test
    @Order(3)
    public void testGetGitData_NotFound(){
        id = "-1";
        ResponseEntity<GitData> responseEntity = testRestTemplate.getForEntity("/gitData/" + id, GitData.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @Order(4)
    public void testGetAllInPeriod() {
        String fromDate = "2023-07-12";
        String toDate = "2023-07-13";

        ResponseEntity<GitDataStats> resp = testRestTemplate.exchange("/gitData/withDates?from=" + fromDate + "&to=" + toDate,
                HttpMethod.GET, null, new ParameterizedTypeReference<GitDataStats>() {});

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        GitDataStats gitData = resp.getBody();
        assert gitData != null;
        assertThat(gitData.getGitData().size(), greaterThan(0));

        // Add additional assertions to verify the response data as needed
    }

    @Test
    @Order(5)
    public void testGetAllRepoNames(){
        ResponseEntity<List<String>> resp = testRestTemplate.exchange("/gitData/repoNames", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<String>>() {});

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        repoNames = resp.getBody();
        assert repoNames != null;
        assertThat(repoNames.size(),greaterThan(0));
    }

    @Test
    @Order(6)
    public void testResultsByRepoName(){
        ResponseEntity<GitDataStats> resp = testRestTemplate.exchange("/gitData/byRepo/" + repoNames.get(0), HttpMethod.GET, null,
                new ParameterizedTypeReference<GitDataStats>() {});

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        GitDataStats results = resp.getBody();
        assert results != null;
        assertThat(results.getGitData().size(),greaterThan(0));
        assertEquals(results.getGitData().get(0).getRepoName(),repoNames.get(0));
    }

    @Test
    @Order(7)
    public void testResultsByRepoName_NotFound(){
        ResponseEntity<GitDataStats> resp = testRestTemplate.exchange("/gitData/byRepo/madeUpFakeRepo", HttpMethod.GET, null,
                new ParameterizedTypeReference<GitDataStats>() {});

        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }

    @Test
    @Order(8)
    public void testGetAllInPeriodByRepo() {
        String fromDate = "2021-07-03";
        String toDate = "2021-09-03";

        ResponseEntity<GitDataStats> resp = testRestTemplate.exchange("/gitData/byRepo/"+repoNames.get(0)+"/withDates?from=" + fromDate + "&to=" + toDate,
                HttpMethod.GET, null, new ParameterizedTypeReference<GitDataStats>() {});

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        GitDataStats gitData = resp.getBody();
        assert gitData != null;
        assertThat(gitData.getGitData().size(), greaterThan(0));

        // Add additional assertions to verify the response data as needed
    }
}
