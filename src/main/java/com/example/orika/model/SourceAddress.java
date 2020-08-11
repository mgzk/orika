package com.example.orika.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SourceAddress {
    private String firstAddress;

    private String secondAddress;
}
