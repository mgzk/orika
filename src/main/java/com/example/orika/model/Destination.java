package com.example.orika.model;

import lombok.Data;

@Data
public class Destination {
    private String firstName;

    private String lastName;

    private DestinationAddress address;
}
