package com.example.onlineshopping.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    private String massage;
    @JsonFormat(pattern = ResponseConstants.RESPONSE_DATE_FORMAT)
    private LocalDateTime dateTime;
    private int status;
}
