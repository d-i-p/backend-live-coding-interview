package com.de.hiking.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionResponse {

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

}
