package org.acme.runtime;

public record ValidationError(
        String condDtlTypId,
        String errorCode,
        String errorMessage,
        String ruleName,
        Severity severity
) {}
