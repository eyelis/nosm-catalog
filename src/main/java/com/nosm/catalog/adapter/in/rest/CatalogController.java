package com.nosm.catalog.adapter.in.rest;

import com.nosm.catalog.application.usecase.*;
import com.nosm.catalog.domain.model.Catalog;
import com.nosm.catalog.domain.model.CatalogType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

/**
 * REST Controller for Catalog Management
 */
@RestController
@RequestMapping("/api/catalogs")
@RequiredArgsConstructor
public class CatalogController {

    private final CreateCatalogUseCase createCatalogUseCase;
    private final UpdateCatalogUseCase updateCatalogUseCase;
    private final SubmitCatalogUseCase submitCatalogUseCase;
    private final ValidateCatalogUseCase validateCatalogUseCase;
    private final CloseCatalogUseCase closeCatalogUseCase;

    @PostMapping
    public ResponseEntity<Catalog> createCatalog(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam String currency,
            @RequestHeader(value = "X-User-Id", defaultValue = "SYSTEM") String userId) {
        Catalog catalog = createCatalogUseCase.execute(name, CatalogType.valueOf(type.toUpperCase()), currency, userId);
        return ResponseEntity.ok(catalog);
    }

    @PutMapping("/{catalogId}")
    public ResponseEntity<Catalog> updateCatalog(
            @PathVariable String catalogId,
            @RequestParam LocalDate activationDate,
            @RequestParam String description,
            @RequestHeader(value = "X-User-Id", defaultValue = "SYSTEM") String userId) {
        Catalog catalog = updateCatalogUseCase.execute(catalogId, activationDate, description, userId);
        return ResponseEntity.ok(catalog);
    }

    @PostMapping("/{catalogId}/submit")
    public ResponseEntity<Catalog> submitCatalog(@PathVariable String catalogId) {
        Catalog catalog = submitCatalogUseCase.execute(catalogId);
        return ResponseEntity.ok(catalog);
    }

    @PostMapping("/{catalogId}/validate")
    public ResponseEntity<Catalog> validateCatalog(@PathVariable String catalogId) {
        Catalog catalog = validateCatalogUseCase.execute(catalogId);
        return ResponseEntity.ok(catalog);
    }

    @PostMapping("/{catalogId}/close")
    public ResponseEntity<Catalog> closeCatalog(@PathVariable String catalogId) {
        Catalog catalog = closeCatalogUseCase.execute(catalogId);
        return ResponseEntity.ok(catalog);
    }
}