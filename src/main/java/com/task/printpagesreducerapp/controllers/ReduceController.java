package com.task.printpagesreducerapp.controllers;

import com.task.printpagesreducerapp.dto.PrintPagesResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.task.printpagesreducerapp.utils.NumParser.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ReduceController {
    private static final String INTERNAL_SERVER_ERROR = "There is an issue: ";
    private static final String BAD_REQUEST = "Only integer numbers separated by commas are accepted.";
    private static final String BAD_REQUEST_DESCRIPTION = "There is BAD REQUEST: ";
    private static final String REQUEST_DESCRIPTION = "There is print reduce pages REQUEST: ";

    @GetMapping(value = "/reducedPageNumbers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reducedPageNumbers(@RequestParam("rawPageNumbers") String rawPageNum) {
        try {
            if (validateOriginalPagesList(rawPageNum)) {
                log.warn(BAD_REQUEST_DESCRIPTION + rawPageNum);
                return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
            }
            else {
                Set<Integer> convertedIdsList = getSortedUniquePrintPagesSet(rawPageNum);
                final PrintPagesResponseDto respReducedPageNum =
                        PrintPagesResponseDto.builder().
                                original(rawPageNum).
                                reduced(printPageReducer(convertedIdsList))
                                .build();
                log.info(REQUEST_DESCRIPTION + convertedIdsList);
                return new ResponseEntity<>(respReducedPageNum, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
