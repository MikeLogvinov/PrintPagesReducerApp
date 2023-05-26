package com.task.printpagesreducerapp.utils;

import java.util.Arrays;
import java.util.Set;
public class NumParser {
    public static StringBuffer printPageReducer(final Set<Integer> requestPages) {
        Integer[] pagesArray = new Integer[ requestPages.size() ];
        pagesArray = requestPages.toArray(pagesArray);
        StringBuffer pagesForPrint = new StringBuffer();
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
}
