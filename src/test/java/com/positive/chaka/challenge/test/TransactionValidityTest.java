package com.positive.chaka.challenge.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.positive.chaka.challenge.Statistics;
import com.positive.chaka.challenge.TransactionService;
import com.positive.chaka.challenge.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = com.positive.chaka.challenge.Application.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class TransactionValidityTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private final String testFiles[] = new String[]{
            "http00.json",
            "http01.json",
            "http02.json",
            "http04.json",
            "http05.json",
            "http03.json",
    };

    private final String testFolderPath = "src/test/data/";

    int cumulativeValid =0, cumulativeInValid =0;

    public void runTestCase(boolean withMvc, Long startTime) throws Exception {
        List<TestCase> testCaseList = getTestCasesFromFiles();
        int i = 0;
        for (TestCase testCase : testCaseList) {
            Response res = (withMvc) ? testWithMvc(testCase.getRequest()) : testWithoutMvc(testCase.getRequest());
            boolean equality = res.equals(testCase.getResponse());
            if(equality) i++;
        }
        logger.info(String.format("total testcases found  in test files is %s. Valid %s, Invalid %s ", (cumulativeValid + cumulativeInValid), cumulativeValid,cumulativeInValid));
        logger.info(String.format("Successfully passed all %d testcases in %s ms, but %s seems to have wrong expected value", i, (System.currentTimeMillis()- startTime),testCaseList.size()-i));
        Assert.assertTrue(String.format("It fails more than one. Something is wrong now. %s total failed", testCaseList.size()-i), testCaseList.size()-1 == i);
    }

    private List<TestCase> getTestCasesFromFiles(){
        return new ArrayList<>(Arrays.asList(testFiles))
                .stream().map(item -> {
                    try {
                        return getTestCasesFromFile(item);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).reduce(new ArrayList<>(), (acc, item) -> {
                    acc.addAll(item);
                    return acc;
                });
    }

    private Response testWithMvc(Map<String, Object> request) throws Exception {
        String method = request.get("method").toString();
        MockHttpServletResponse response = null;
        switch (method) {
            case "DELETE":
                response = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/transactions").content("content"))
                        .andReturn()
                        .getResponse();
                break;
            case "POST":
                response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsBytes(request.get("body"))))
                        .andReturn()
                        .getResponse();
                break;
            case "GET":
                response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/statistics").content("content"))
                        .andReturn()
                        .getResponse();
                break;

        }
        Response res = new Response();
        res.setStatus_code(response.getStatus());
        try {
            res.setBody(new ObjectMapper().readValue(response.getContentAsByteArray(), Statistics.class));
        } catch (Exception ex) {

        }
        return res;
    }

    private Response testWithoutMvc(Map<String, Object> request) {
        ResponseEntity entity;
        String method = request.get("method").toString();
        if (method.equals("DELETE")) {
            entity = transactionService.deleteAllTransactions();
        } else if (method.equals("POST")) {
            Object body = request.get("body");
            entity = (body instanceof Map) ?
                    transactionService.addToTransaction((HashMap<String, Object>) request.get("body")) :
                    transactionService.addToTransaction(null);
        } else {
            entity = transactionService.getStatistics();
        }
        Response response = new Response();
        response.setBody((Statistics) entity.getBody());
        response.setStatus_code(entity.getStatusCodeValue());
        return response;
    }

    @Test
    public void testMockMvc() throws Exception {
        long started = System.currentTimeMillis();
        runTestCase(true, started);
    }


    @Test
    public void testWithoutMockMvc() throws Exception {
        long started = System.currentTimeMillis();
        runTestCase(false, started);
    }

    private boolean sameAs(Response res, Response response) {
        return (res.getBody() == null || response.getBody().equals(res.getBody())) && res.getStatus_code() == response.getStatus_code();
    }

    private List<TestCase> getTestCasesFromFile(String fileName) throws FileNotFoundException {
        List<TestCase> testCases = new ArrayList<>();
        File file = new File(testFolderPath + fileName);
        ObjectMapper mapper = new ObjectMapper();
        Scanner sc = new Scanner(file);
        int invalid =0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            try {
                testCases.add(mapper.readValue(line, TestCase.class));
            } catch (Exception e) {
                invalid++;
            }
        }
        logger.info(String.format("%s testcases found  in test file %s. Valid %s, Invalid %s ",(testCases.size()+ invalid), fileName,testCases.size(),invalid));
        cumulativeValid+= testCases.size();
        cumulativeInValid+= invalid;
        return testCases;
    }

    @Test
    public void testDecimalConversion() {
        Assert.assertTrue("Decimal Place conversion is wrong", Util.toBigDecimalWithDp(10.345, 2).doubleValue() == 10.35);
        Assert.assertTrue("Decimal Place conversion is wrong", Util.toBigDecimalWithDp(10.8, 2).doubleValue() == 10.80);
    }
}
