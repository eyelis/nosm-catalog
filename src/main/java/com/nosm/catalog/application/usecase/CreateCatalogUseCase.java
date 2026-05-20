package com.nosm.catalog.application.usecase;

import com.nosm.catalog.domain.model.Catalog;
import com.nosm.catalog.domain.model.CatalogType;
import com.nosm.catalog.domain.port.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Create Catalog
 * Creates a new catalog in DRAFT status.
 */
@Service
@RequiredArgsConstructor
public class CreateCatalogUseCase {
    private final CatalogRepository catalogRepository;

    @Transactional
    public Catalog execute(String name, CatalogType type, String currency, String userId) {
        Catalog catalog = Catalog.createNew(name, type, currency, userId);
        return catalogRepository.save(catalog);
    }
}