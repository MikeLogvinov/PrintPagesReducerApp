package com.task.printpagesreducerapp.controllers;

import com.task.printpagesreducerapp.dto.PrintPagesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Reduce Controller", description = "Page numbers reducer API")
@RequestMapping("/api/v1")
@Slf4j
public class ReduceController {
    private static final String INTERNAL_SERVER_ERROR = "There is an issue.";
    private static final String BAD_REQUEST_DESCRIPTION = "There is a BAD REQUEST: ";
    private static final String REQUEST_DESCRIPTION = "There is a print reduce pages REQUEST: ";

    @Operation(
            summary = "Getting reduced form for arbitrary set of page numbers.",
            description = "The response is JSON object, two string type parameters 'original' and 'reduced'. 'original' contains original request, 'reduced' contains result of changed/reduced pages list for printing.", method = "reducedPageNumbers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PrintPagesResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)})
    @GetMapping(value = "/reducedPageNumbers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reducedPageNumbers(@RequestParam("rawPageNumbers") String rawPageNum) {
        try {
            if (validateOriginalPagesList(rawPageNum)) {
                log.warn(BAD_REQUEST_DESCRIPTION + rawPageNum);
                return new ResponseEntity<>(getWrongPageListNumbers(), HttpStatus.BAD_REQUEST);
            }
            else {
                Set<Integer> convertedIdsList = getSortedUniquePrintPagesSet();
                final PrintPagesResponseDto respReducedPageNum =
                        PrintPagesResponseDto.builder().
                                original(rawPageNum).
                                reduced(printPageReducer(convertedIdsList).toString())
                                .build();
                log.info(REQUEST_DESCRIPTION + convertedIdsList);
                return new ResponseEntity<>(respReducedPageNum, HttpStatus.OK);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
