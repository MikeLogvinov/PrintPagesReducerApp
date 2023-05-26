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
        final int pagesArrayLengthWithoutDuplicates = removeDuplicates(pagesArray);
        final Integer[] pagesArrayWithoutDuplicates = Arrays.copyOfRange(pagesArray, 0, pagesArrayLengthWithoutDuplicates);

        for (int i = 0; i < pagesArrayWithoutDuplicates.length; i++) {

            if ( i != pagesArrayWithoutDuplicates.length - 1 && pagesArrayWithoutDuplicates[i] == (pagesArrayWithoutDuplicates[i + 1] - 1)) {
                continue;
            }
            else {
                int pagesNumber = i + 1 - j;
                if (pagesNumber > 1) {
                    pagesForPrint.append(pagesArrayWithoutDuplicates[j]).append("-").append(pagesArrayWithoutDuplicates[i]);
                }
                else {
                    pagesForPrint.append(pagesArrayWithoutDuplicates[i]);
                }
                if (i != pagesArrayWithoutDuplicates.length - 1) { pagesForPrint.append(","); }
                j = i + 1;
            }
        }
        return pagesForPrint;
    }

    public static int removeDuplicates(final Integer[] nums) {
        int i = 0;
        for (int n : nums) {
            if (i == 0 || n != nums[i - 1]) {
                nums[i++] = n;
            }
        }
        return i;
    }
}
