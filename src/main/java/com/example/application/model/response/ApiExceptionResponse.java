package com.example.application.model.response;

import java.util.List;

public record ApiExceptionResponse
        (
                List<String> messages
        ) {
        
}
