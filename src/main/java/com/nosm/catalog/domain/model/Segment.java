package com.nosm.catalog.domain.model;

import lombok.*;

/**
 * Value Object: Segment
 * Represents a customer segment in the catalog scope.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "segmentCode")
@ToString
public class Segment {
    private String segmentCode;
    private String segmentName;
    private boolean exceptionalList;
}