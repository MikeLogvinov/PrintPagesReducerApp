package com.task.printpagesreducerapp.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.task.printpagesreducerapp.utils.NumParser.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test NumParser class")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NumParserTest {
    private String pageNumsOriginReq;
    private String pageNumsOgnReqChars;
    private Set<Integer> originalPages;
    private StringBuffer reducedPages;
    private final TreeSet<Integer> treeSet = new TreeSet<>();

    @BeforeEach
    public void initTests() {
        Integer[] pageNums = new Integer[]{1, 2, 3, 4, 8, 12, 11, 24, 3544, 11, 1, 1};
        pageNumsOriginReq = "1, 2, 3, 4, 8, 12, 11, 24, 3544, 11, 1, 1, 0";
        pageNumsOgnReqChars = "1, 2a, 3, b4, 8, 12, 11, 24, c3544, 11, 1, 1, 0, d";
        originalPages = Stream.of(pageNums).collect(Collectors.toCollection(TreeSet::new));
        reducedPages = new StringBuffer().append("1-4,8,11-12,24,3544");
    }

    @Test
    @Order(1)
    @DisplayName("Test sortedUniquePrintPagesSet procedure, must return unique sorted list without zero")
    public void test_getSortedUniquePrintPagesSet() {
        assertEquals(originalPages, getSortedUniquePrintPagesSet(pageNumsOriginReq));
    }

    @Test
    @Order(2)
    @DisplayName("Test validateOriginalPagesList procedure, must return true if contains chars, false if not")
    public void test_validateOriginalPagesList() {
        assertTrue(validateOriginalPagesList(pageNumsOgnReqChars));
        assertFalse(validateOriginalPagesList(pageNumsOriginReq));
    }

    @Test
    @Order(3)
    @DisplayName("Test printPageReducer procedure, must correctly reduced page numbers")
    public void test_printPageReducer() {
        assertEquals(reducedPages.toString(), printPageReducer(originalPages).toString());
    }

    @Test
    @Order(4)
    @DisplayName("Test validateOriginalPagesList procedure if request is null, must return true")
    public void test_validateOriginalPagesList_returns_notValidIfNull() {;
        assertTrue(validateOriginalPagesList(null));
    }

    @Test
    @Order(5)
    @DisplayName("Test getSortedUniquePrintPagesSet, will return empty TreeSet if request is null")
    public void test_getSortedUniquePrintPagesSet_nullPointerException() {
        assertEquals(treeSet, getSortedUniquePrintPagesSet(null));
    }

    @Test
    @Order(6)
    @DisplayName("Test printPageReducer combined with getSortedUniquePrintPagesSet, will return correct reduced print page list")
    public void test_printPageReducer_combined_with_getSortedUniquePrintPagesSet() {
        assertEquals(reducedPages.toString(), printPageReducer(getSortedUniquePrintPagesSet(pageNumsOriginReq)).toString());
    }

}
