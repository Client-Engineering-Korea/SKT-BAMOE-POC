package org.acme.runtime;

public record ActionItem(
        String kind,
        String targetProdId,
        String message,
        String reasonRuleId
) {}
