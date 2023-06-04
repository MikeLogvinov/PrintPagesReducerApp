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
class NumParserTest {
    private String pageNumsOriginReq, pageNumsOriginReqChars;
    private Set<Integer> originalPages, pageNumbersBelongsTrueList;
    private StringBuilder reducedPages;
    private final Set<Integer> emptySet = new HashSet<>();

    @BeforeEach
    public void initTests() {
        Integer[] pageNums = new Integer[]{1, 2, 3, 4, 8, 12, 11, 24, 3544, 11, 1, 1};
        pageNumsOriginReq = "1, 2, 3, 4, 8, 12, 11, 24, 3544, 11, 1, 1, 0";
        pageNumsOriginReqChars = "1, 2a, 3, b4, 8, 12, 11, 24, c3544, 11, 1, 1, 0, d";
        originalPages = Stream.of(pageNums).collect(Collectors.toSet());
        reducedPages = new StringBuilder().append("1-4,8,11-12,24,3544");
        pageNumbersBelongsTrueList =  Stream.of(1, 3, 8, 24, 11, 12).collect(Collectors.toSet());
    }

    @Test
    @DisplayName("Test sortedUniquePrintPagesSet procedure, must return unique sorted list without zero")
    void test_getSortedUniquePrintPagesSet() {
        validateOriginalPagesList(pageNumsOriginReq);
        assertEquals(originalPages, getSortedUniquePrintPagesSet());
    }

    @Test
    @DisplayName("Test validateOriginalPagesList procedure, must return true if contains chars, false if not")
    void test_validateOriginalPagesList() {
        assertTrue(validateOriginalPagesList(pageNumsOriginReqChars));
        assertFalse(validateOriginalPagesList(pageNumsOriginReq));
    }

    @Test
    @DisplayName("Test printPageReducer procedure, must correctly reduced page numbers")
    void test_printPageReducer() {
        assertEquals(reducedPages.toString(), printPageReducer(originalPages).toString());
    }

    @Test
    @DisplayName("Test validateOriginalPagesList procedure if request is null, must return true")
    void test_validateOriginalPagesList_returns_notValidIfNull() {
        assertTrue(validateOriginalPagesList(null));
    }

    @Test
    @DisplayName("Test getSortedUniquePrintPagesSet, will return empty Set type object if request is null, empty, single string, single mixed string, single zero, not Integer value")
    void test_getSortedUniquePrintPagesSet_nullPointerException() {
        validateOriginalPagesList(null);
        assertEquals(emptySet, getSortedUniquePrintPagesSet());
        validateOriginalPagesList(StringUtils.EMPTY);
        assertEquals(emptySet, getSortedUniquePrintPagesSet());
        validateOriginalPagesList("0");
        assertEquals(emptySet, getSortedUniquePrintPagesSet());
        validateOriginalPagesList(RandomStringUtils.random(9, true, false));
        assertEquals(emptySet, getSortedUniquePrintPagesSet());
        validateOriginalPagesList(RandomStringUtils.random(9, true, false));
        assertEquals(emptySet, getSortedUniquePrintPagesSet());
        validateOriginalPagesList(RandomStringUtils.random(10, false, true));
        assertEquals(emptySet, getSortedUniquePrintPagesSet());
    }

    @Test
    @DisplayName("Test getSortedUniquePrintPagesSet and printPageReducer will return correct page list")
    void test_printPageReducer_combined_with_getSortedUniquePrintPagesSet() {
        validateOriginalPagesList(pageNumsOriginReq);
        assertEquals(reducedPages.toString(), printPageReducer(getSortedUniquePrintPagesSet()).toString());
    }

    @Test
    @DisplayName("Test splitPagesCorrectListWrongList procedure, must correctly create  reduced page numbers")
    void test_splitPagesCorrectListWrongList() {
        splitPagesCorrectListWrongList(pageNumsOriginReqChars);
        assertEquals(pageNumbersBelongsTrueList,getSortedUniquePrintPagesSet());
    }
}
