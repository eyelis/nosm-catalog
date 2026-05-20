package com.nosm.catalog.domain.model;

/**
 * Enumeration of catalog statuses in the lifecycle management.
 */
public enum CatalogStatus {
    DRAFT("Draft - Catalogue en création"),
    SUBMITTED("Submitted - Catalogue soumis pour validation"),
    VALIDATED("Validated - Catalogue approuvé"),
    CLOSED("Closed - Catalogue clôturé");

    private final String description;

    CatalogStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}