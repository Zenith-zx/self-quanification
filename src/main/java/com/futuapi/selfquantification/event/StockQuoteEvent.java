package com.futuapi.selfquantification.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 周欣
 * @date 2025/4/1 16:47
 */
@Data
@AllArgsConstructor
public class StockQuoteEvent {
    private String stockData;
}
