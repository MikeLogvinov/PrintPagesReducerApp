package com.task.printpagesreducerapp.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReduceController.class)
@DisplayName("Test ReduceController class")
class ReduceControllerTest {
    @Autowired
    private MockMvc mvc;
    private String pageNumsOriginReq, reducedPages, pageNumsOriginReqWithChars, pageNumsOriginReqWithZeros;
    private final static String URL_PATH = "/api/v1/reducedPageNumbers";

    @BeforeEach
    public void initTests() {
        pageNumsOriginReq = "1, 2, 3, 4, 8, 12, 11, 24, 3544, 11, 1, 1";
        pageNumsOriginReqWithChars = "1, 2a, 3, b4, 8, 12, 11, 24, c3544, 11, 1, 1, 0, d";
        pageNumsOriginReqWithZeros = "0, 00, 000, 01, 05";
        reducedPages = "1-4,8,11-12,24,3544";
    }

    @Test
    @DisplayName("Test reducedPageNumbersApi 200, must return correct reduced print page list")
    void test_reducedPageNumbersApi_200() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", pageNumsOriginReq)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.original").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reduced").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.original").value(pageNumsOriginReq))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reduced").value(reducedPages));
    }

    @Test
    @DisplayName("Test reducedPageNumbersApi 400, must return 400 Response in case of wrong requested page list with chars")
    void test_reducedPageNumbersApi_400() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", pageNumsOriginReqWithChars)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.original").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reduced").doesNotExist());
    }

    @Test
    @DisplayName("Test reducedPageNumbersApi 400, must return Bad Request in case of empty, zero, single string request")
    void test_reducedPageNumbersApi_400_with_EmptyRequest() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", StringUtils.EMPTY)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", RandomStringUtils.random(9, true, false))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test reducedPageNumbersApi 400, must return Bad Request in case of big value")
    void test_reducedPageNumbersApi_400_big_integer_value() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", RandomStringUtils.random(10, false, true))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test reducedPageNumbersApi 400, must return 400 Response in case of wrong requested page list with single zeros or starts with zero numbers, etc")
    void test_reducedPageNumbersApi_400_contains_zeros() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", pageNumsOriginReqWithZeros)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
