package com.task.printpagesreducerapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrintPagesResponseDto {
    String original;
    StringBuffer reduced;
}
