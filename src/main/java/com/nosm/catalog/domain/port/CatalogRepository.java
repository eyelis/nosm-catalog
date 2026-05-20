package com.nosm.catalog.domain.port;

import com.nosm.catalog.domain.model.Catalog;
import java.util.Optional;
import java.util.List;

/**
 * Output Port: CatalogRepository
 * Interface for catalog persistence operations following hexagonal architecture.
 */
public interface CatalogRepository {
    Catalog save(Catalog catalog);
    Optional<Catalog> findById(String catalogUniqueId);
    List<Catalog> findAll();
    void delete(String catalogUniqueId);
}