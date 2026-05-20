package com.nosm.catalog.application.usecase;

import com.nosm.catalog.domain.model.Catalog;
import com.nosm.catalog.domain.port.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

/**
 * Use Case: Update Catalog
 * Updates a catalog in DRAFT status.
 */
@Service
@RequiredArgsConstructor
public class UpdateCatalogUseCase {
    private final CatalogRepository catalogRepository;

    @Transactional
    public Catalog execute(String catalogId, LocalDate activationDate, String description, String userId) {
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new IllegalArgumentException("Catalog not found: " + catalogId));
        
        catalog.updateDraft(activationDate, description, userId);
        return catalogRepository.save(catalog);
    }
}