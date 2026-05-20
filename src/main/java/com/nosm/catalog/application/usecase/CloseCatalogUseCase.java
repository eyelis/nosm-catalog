package com.nosm.catalog.application.usecase;

import com.nosm.catalog.domain.model.Catalog;
import com.nosm.catalog.domain.port.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Close Catalog
 * Closes a validated catalog.
 */
@Service
@RequiredArgsConstructor
public class CloseCatalogUseCase {
    private final CatalogRepository catalogRepository;

    @Transactional
    public Catalog execute(String catalogId) {
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new IllegalArgumentException("Catalog not found: " + catalogId));
        
        catalog.close();
        return catalogRepository.save(catalog);
    }
}