package com.nosm.catalog.application.usecase;

import com.nosm.catalog.domain.model.Catalog;
import com.nosm.catalog.domain.port.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Submit Catalog
 * Submits a catalog for validation.
 */
@Service
@RequiredArgsConstructor
public class SubmitCatalogUseCase {
    private final CatalogRepository catalogRepository;

    @Transactional
    public Catalog execute(String catalogId) {
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new IllegalArgumentException("Catalog not found: " + catalogId));
        
        catalog.submit();
        return catalogRepository.save(catalog);
    }
}