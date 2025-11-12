package com.smartretails.backend.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesReportDto {

    private String from;
    private String to;
    private long transactions;
    private BigDecimal grossTotal;
    private BigDecimal netTotal;
    private BigDecimal taxTotal;
    private BigDecimal discountTotal;
}
