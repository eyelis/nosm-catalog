package com.nosm.catalog.domain.model;

import lombok.*;

/**
 * Value Object: Entity
 * Represents a legal entity in the catalog scope.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "entityCode")
@ToString
public class Entity {
    private String entityCode;
    private String entityName;
}