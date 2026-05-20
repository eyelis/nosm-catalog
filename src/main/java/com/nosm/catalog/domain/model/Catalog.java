package com.nosm.catalog.domain.model;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Aggregate Root: Catalog
 * Manages the complete lifecycle of a catalog in the NOSM system.
 */
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "catalogUniqueId")
@ToString
public class Catalog {
    private String catalogUniqueId;
    private String catalogNumber;
    private String catalogVersion;
    private CatalogType type;
    private String name;
    private String currency;
    private CatalogStatus status;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;
    private LocalDateTime submittedAt;
    private LocalDateTime validatedAt;
    private LocalDateTime closedAt;
    private LocalDate activationDate;
    private String description;
    
    @Builder.Default
    private Set<Product> products = new HashSet<>();
    
    @Builder.Default
    private Set<Entity> entities = new HashSet<>();
    
    @Builder.Default
    private Set<Segment> segments = new HashSet<>();

    /**
     * Factory method to create a new catalog in DRAFT status
     */
    public static Catalog createNew(String name, CatalogType type, String currency, String userId) {
        return Catalog.builder()
                .catalogUniqueId(UUID.randomUUID().toString())
                .catalogNumber("CAT-" + System.currentTimeMillis())
                .catalogVersion("9.1")
                .type(type)
                .name(name)
                .currency(currency)
                .status(CatalogStatus.DRAFT)
                .createdAt(LocalDateTime.now())
                .createdBy(userId)
                .lastModifiedAt(LocalDateTime.now())
                .lastModifiedBy(userId)
                .products(new HashSet<>())
                .entities(new HashSet<>())
                .segments(new HashSet<>())
                .build();
    }

    /**
     * Update catalog with new information when status is DRAFT
     */
    public void updateDraft(LocalDate activationDate, String description, String userId) {
        if (status != CatalogStatus.DRAFT) {
            throw new IllegalStateException("Le catalogue ne peut être modifié qu'au statut DRAFT.");
        }
        this.activationDate = activationDate;
        this.description = description;
        this.lastModifiedAt = LocalDateTime.now();
        this.lastModifiedBy = userId;
    }

    /**
     * Submit catalog for validation
     */
    public void submit() {
        if (status != CatalogStatus.DRAFT) {
            throw new IllegalStateException("Seul un catalogue DRAFT peut être soumis.");
        }
        if (products.isEmpty()) {
            throw new IllegalStateException("Le catalogue doit contenir au moins 1 produit configuré.");
        }
        this.status = CatalogStatus.SUBMITTED;
        this.submittedAt = LocalDateTime.now();
    }

    /**
     * Validate submitted catalog
     */
    public void validate() {
        if (status != CatalogStatus.SUBMITTED) {
            throw new IllegalStateException("Seul un catalogue SUBMITTED peut être validé.");
        }
        this.status = CatalogStatus.VALIDATED;
        this.validatedAt = LocalDateTime.now();
    }

    /**
     * Close validated catalog
     */
    public void close() {
        if (status != CatalogStatus.VALIDATED) {
            throw new IllegalStateException("Seul un catalogue VALIDATED peut être clôturé.");
        }
        this.status = CatalogStatus.CLOSED;
        this.closedAt = LocalDateTime.now();
    }

    /**
     * Add product to catalog
     */
    public void addProduct(Product product) {
        if (status != CatalogStatus.DRAFT) {
            throw new IllegalStateException("Les produits ne peuvent être ajoutés qu'en mode DRAFT.");
        }
        this.products.add(product);
    }

    /**
     * Add entity to catalog scope
     */
    public void addEntity(Entity entity) {
        if (status != CatalogStatus.DRAFT) {
            throw new IllegalStateException("Les entités ne peuvent être ajoutées qu'en mode DRAFT.");
        }
        this.entities.add(entity);
    }

    /**
     * Add segment to catalog scope
     */
    public void addSegment(Segment segment) {
        if (status != CatalogStatus.DRAFT) {
            throw new IllegalStateException("Les segments ne peuvent être ajoutés qu'en mode DRAFT.");
        }
        this.segments.add(segment);
    }

    public int getProductCount() {
        return products.size();
    }

    public int getEntityCount() {
        return entities.size();
    }

    public int getSegmentCount() {
        return segments.size();
    }
}