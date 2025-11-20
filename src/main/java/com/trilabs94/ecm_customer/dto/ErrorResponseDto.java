package com.trilabs94.ecm_customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
    private String uri;
    private String errorMessage;
    private HttpStatus errorCode;
    private LocalDateTime errorTimeStamp;
}
