package com.task.printpagesreducerapp.controllers;

import com.task.printpagesreducerapp.utils.NumParser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ReduceController {

    @GetMapping("/reducedPageNumbers")
    public ResponseEntity<?> reducedPageNumbers(@RequestParam(value="rawPageNumbers") String rawPageNumbers) {

        Set<Integer> convertedIdsList = Stream.of(rawPageNumbers.split(","))
                .map(String::trim)
                .filter(s -> s.matches("\\d{1,9}")) // only digits values, each has to contain less than 10 symbols
                .map(Integer::parseInt)
                .filter(number -> number > 0)
                .collect(Collectors.toCollection(TreeSet::new));

        return new ResponseEntity<>(convertedIdsList, HttpStatus.OK);
    }
}
