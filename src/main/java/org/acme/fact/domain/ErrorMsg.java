package org.acme.fact.domain;

public record ErrorMsg(
        String condConsId,
        String msgId,
        String msgCtt,
        String msgInfo
) {}
