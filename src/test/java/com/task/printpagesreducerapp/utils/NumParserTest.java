package com.task.printpagesreducerapp.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.task.printpagesreducerapp.utils.NumParser.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test NumParser class")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NumParserTest {
    private String pageNumsOriginReq, pageNumsOriginReqChars;
    private Set<Integer> originalPages;
    private StringBuilder reducedPages;
    private final Set<Integer> emptySet = new HashSet<>();

    @BeforeEach
    public void initTests() {
        Integer[] pageNums = new Integer[]{1, 2, 3, 4, 8, 12, 11, 24, 3544, 11, 1, 1};
        pageNumsOriginReq = "1, 2, 3, 4, 8, 12, 11, 24, 3544, 11, 1, 1, 0";
        pageNumsOriginReqChars = "1, 2a, 3, b4, 8, 12, 11, 24, c3544, 11, 1, 1, 0, d";
        originalPages = Stream.of(pageNums).collect(Collectors.toSet());
        reducedPages = new StringBuilder().append("1-4,8,11-12,24,3544");
    }

    @Test
    @Order(1)
    @DisplayName("Test sortedUniquePrintPagesSet procedure, must return unique sorted list without zero")
    void test_getSortedUniquePrintPagesSet() {
        assertEquals(originalPages, getSortedUniquePrintPagesSet(pageNumsOriginReq));
    }

    @Test
    @Order(2)
    @DisplayName("Test validateOriginalPagesList procedure, must return true if contains chars, false if not")
    void test_validateOriginalPagesList() {
        assertTrue(validateOriginalPagesList(pageNumsOriginReqChars));
        assertFalse(validateOriginalPagesList(pageNumsOriginReq));
    }

    @Test
    @Order(3)
    @DisplayName("Test printPageReducer procedure, must correctly reduced page numbers")
    void test_printPageReducer() {
        assertEquals(reducedPages.toString(), printPageReducer(originalPages).toString());
    }

    @Test
    @Order(4)
    @DisplayName("Test validateOriginalPagesList procedure if request is null, must return true")
    void test_validateOriginalPagesList_returns_notValidIfNull() {
        assertTrue(validateOriginalPagesList(null));
    }

    @Test
    @Order(5)
    @DisplayName("Test getSortedUniquePrintPagesSet, will return empty Set type object if request is null, empty, single string, single mixed string, single zero")
    void test_getSortedUniquePrintPagesSet_nullPointerException() {
        assertEquals(emptySet, getSortedUniquePrintPagesSet(null));
        assertEquals(emptySet, getSortedUniquePrintPagesSet(StringUtils.EMPTY));
        assertEquals(emptySet, getSortedUniquePrintPagesSet("0"));
        assertEquals(emptySet, getSortedUniquePrintPagesSet(RandomStringUtils.random(9, true, false)));
        assertEquals(emptySet, getSortedUniquePrintPagesSet(RandomStringUtils.random(9, true, true)));
    }

    @Test
    @Order(6)
    @DisplayName("Test printPageReducer combined with getSortedUniquePrintPagesSet, will return correct reduced print page list")
    void test_printPageReducer_combined_with_getSortedUniquePrintPagesSet() {
        assertEquals(reducedPages.toString(), printPageReducer(getSortedUniquePrintPagesSet(pageNumsOriginReq)).toString());
    }
}
