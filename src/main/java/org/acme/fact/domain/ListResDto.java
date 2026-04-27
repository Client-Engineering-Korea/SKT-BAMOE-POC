package org.acme.fact.domain;

import java.util.List;

/**
 * 목록과 개수를 함께 반환하는 공통 객체
 */
public record ListResDto<T>(
        long totalCount,
        List<T> items
) {}
