package com.task.printpagesreducerapp.controllers;

import com.task.printpagesreducerapp.dto.PrintPagesResponseDto;
import com.task.printpagesreducerapp.utils.NumParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ReduceController {
    private static final String INTERNAL_SERVER_ERROR = "There is an issue: ";
    private static final String BAD_REQUEST = "Only whole numbers separated by commas are accepted.";
    private static final String BAD_REQUEST_DESCRIPTION = "There is BAD REQUEST: ";
    private static final String REQUEST_DESCRIPTION = "There is print reduce pages REQUEST: ";

    @GetMapping(value = "/reducedPageNumbers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reducedPageNumbers(@RequestParam("rawPageNumbers") String rawPageNumbers) {
        try {
            String pattern = "\\d{1,9}"; // only digits values, each has to contain less than 10 symbols
            Set<String> checkRawPageNumbers = Stream.of(rawPageNumbers.split(","))
                    .map(String::trim)
                    .filter(s -> !s.matches(pattern))
                    .collect(Collectors.toSet());
            if (checkRawPageNumbers.size() > 0) {
                log.warn(BAD_REQUEST_DESCRIPTION + rawPageNumbers);
                return new ResponseEntity<>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
            }

            Set<Integer> convertedIdsList = Stream.of(rawPageNumbers.split(","))
                    .map(String::trim)
                    .filter(s -> s.matches(pattern))
                    .map(Integer::parseInt)
                    .filter(number -> number > 0)
                    .collect(Collectors.toCollection(TreeSet::new));

            PrintPagesResponseDto responseReducedPageNumbers = PrintPagesResponseDto.builder().original(rawPageNumbers).reduced(NumParser.printPageReducer(convertedIdsList)).build();
            log.info(REQUEST_DESCRIPTION + convertedIdsList);
            return new ResponseEntity<>(responseReducedPageNumbers, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
