package com.task.printpagesreducerapp.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class NumParser {
    private static final String SPLITTER = ",";
    private static final String PATTERN = "\\d{1,9}"; // only integer values, each has to contain less than 10 symbols
    private static Map<Boolean, List<String>> splitPagesCorrectListWrongList = new HashMap<>();
    public static StringBuilder printPageReducer(final Set<Integer> requestPages) {
        Integer[] pagesArray = new Integer[ requestPages.size() ];
        pagesArray = requestPages.toArray(pagesArray);
        StringBuilder pagesForPrint = new StringBuilder();
        int j = 0;
        Arrays.sort(pagesArray);

        for (int i = 0; i < pagesArray.length; i++) {
            if ( i != pagesArray.length - 1 && pagesArray[i] == (pagesArray[i + 1] - 1)) { continue; }
            else {
                int pagesNumber = i + 1 - j;
                if (pagesNumber > 1) {
                    pagesForPrint.append(pagesArray[j]).append("-").append(pagesArray[i]);
                }
                else {
                    pagesForPrint.append(pagesArray[i]);
                }
                if (i != pagesArray.length - 1) { pagesForPrint.append(","); }
                j = i + 1;
            }
        }
        return pagesForPrint;
    }
    public static boolean validateOriginalPagesList(final String originalPagesList) {
        splitPagesCorrectListWrongList(originalPagesList);
        return  StringUtils.isBlank(originalPagesList) ||
                StringUtils.endsWith(originalPagesList, SPLITTER) ||
                "0".equals(originalPagesList) ||
                splitPagesCorrectListWrongList.isEmpty() || !splitPagesCorrectListWrongList.get(false).isEmpty();
    }
    public static Set<Integer> getSortedUniquePrintPagesSet() {
        Set<Integer> response = new HashSet<>();
        if (!splitPagesCorrectListWrongList.isEmpty())
            response = splitPagesCorrectListWrongList.get(true)
                    .stream()
                    .map(Integer::parseInt)
                    .filter(number -> number > 0)
                    .collect(Collectors.toSet());
        return response;
    }
    private static void splitPagesCorrectListWrongList (final String originalPagesList) {
        if (!StringUtils.isBlank(originalPagesList))
        {
            splitPagesCorrectListWrongList =
                    Stream.of(originalPagesList.split(SPLITTER))
                            .map(String::trim)
                            .collect(Collectors.partitioningBy(nun -> nun.matches(PATTERN)));
        }
    }

}
