package com.example.auth.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class InternalErrorException extends Exception {
    @NonNull private String message;

    private String codeError;

}