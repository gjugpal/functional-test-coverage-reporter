package com.geek.elasticsearch.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class Endpoint {

    private long timestamp;
    private String method;
    private String path;
}
