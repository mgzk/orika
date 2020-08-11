package com.example.orika.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Source {
    private String firstName;

    private String secondName;

    private SourceAddress address;
}
