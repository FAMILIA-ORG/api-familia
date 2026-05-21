package com.example.familia.exception;

import java.util.List;

public class BadRequestException extends RuntimeException {
    private final List<String> missingFields;

    public BadRequestException(String message, List<String> missingFields) {
        super(message);
        this.missingFields = missingFields;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }
}
