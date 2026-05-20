package com.nosm.catalog.domain.model;

/**
 * Enumeration of catalog types.
 */
public enum CatalogType {
    CASH("Trésorerie"),
    CREDIT("Crédit"),
    INVESTMENT("Investissement"),
    CUSTODY("Garde-titres");

    private final String label;

    CatalogType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}