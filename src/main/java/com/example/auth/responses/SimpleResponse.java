package com.example.auth.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleResponse<T> {

    private Integer code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String path;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T value;
}
