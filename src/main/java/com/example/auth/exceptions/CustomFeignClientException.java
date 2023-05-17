package com.example.auth.exceptions;

import feign.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomFeignClientException extends Exception {

    private String message;

    private transient Response response;

}
