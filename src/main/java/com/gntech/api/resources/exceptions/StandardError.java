package com.gntech.api.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardError {

    private LocalDateTime timestamp;
    private Integer status;
    private String path;
    private String message;
}
