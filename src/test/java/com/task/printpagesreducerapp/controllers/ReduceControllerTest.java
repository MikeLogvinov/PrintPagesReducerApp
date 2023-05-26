package com.task.printpagesreducerapp.controllers;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.task.printpagesreducerapp.utils.NumParser.getSortedUniquePrintPagesSet;
import static com.task.printpagesreducerapp.utils.NumParser.printPageReducer;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReduceController.class)
@DisplayName("Test ReduceController class")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReduceControllerTest {
    @Autowired
    private MockMvc mvc;
    private String pageNumsOriginReq;
    private String pageNumsOriginReqChars;
    private final static String URL_PATH = "/api/v1/reducedPageNumbers";

    @BeforeEach
    public void initTests() {
        pageNumsOriginReq = "1, 2, 3, 4, 8, 12, 11, 24, 3544, 11, 1, 1, 0";
        pageNumsOriginReqChars = "1, 2a, 3, b4, 8, 12, 11, 24, c3544, 11, 1, 1, 0, d";
    }

    @Test
    @Order(1)
    @DisplayName("Test reducedPageNumbersApi 200, must return correct reduced print page list")
    public void test_reducedPageNumbersApi_200() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", pageNumsOriginReq)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.original").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reduced").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reduced").value(printPageReducer(getSortedUniquePrintPagesSet(pageNumsOriginReq)).toString()));
    }

    @Test
    @Order(2)
    @DisplayName("Test reducedPageNumbersApi 400, must return 400 Response in case of wrong requested page list with chars")
    public void test_reducedPageNumbersApi_400() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", pageNumsOriginReqChars)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.original").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reduced").doesNotExist());
    }

    @Test
    @Order(3)
    @DisplayName("Test reducedPageNumbersApi 400, must return Bad Request in case of empty request")
    public void test_reducedPageNumbersApi_400_with_EmptyRequest() throws Exception
    {        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", StringUtils.EMPTY)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @DisplayName("Test reducedPageNumbersApi 400, must return Bad Request in case of big value")
    public void test_reducedPageNumbersApi_400_big_integer_value() throws Exception
    {        mvc.perform(MockMvcRequestBuilders
                        .get(URL_PATH)
                        .param("rawPageNumbers", "9999999999")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
