package com.example.auth.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListSimpleResponse<T> {

    private Integer code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> value;
}
