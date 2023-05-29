package com.task.printpagesreducerapp.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class PrintPagesResponseDto {
    private String original;
    private String reduced;
}
