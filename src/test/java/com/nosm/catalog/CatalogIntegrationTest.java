package com.nosm.catalog;

import com.nosm.catalog.adapter.out.persistence.CatalogRepositoryImpl;
import com.nosm.catalog.application.usecase.*;
import com.nosm.catalog.domain.model.Catalog;
import com.nosm.catalog.domain.model.CatalogType;
import com.nosm.catalog.domain.model.Product;
import com.nosm.catalog.domain.port.CatalogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests with Testcontainers and SQL Server 2022
 */
@SpringBootTest
@Testcontainers
class CatalogIntegrationTest {

    @Container
    static MSSQLServerContainer<?> mssql = new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2022-latest")
            .withPassword("YourPassword1234!")
            .withExposedPorts(1433);

    @DynamicPropertySource
    static void registerProperties() {
        // Configure connection to Testcontainers
    }

    @Autowired
    private CreateCatalogUseCase createCatalogUseCase;

    @Autowired
    private UpdateCatalogUseCase updateCatalogUseCase;

    @Autowired
    private SubmitCatalogUseCase submitCatalogUseCase;

    @Autowired
    private ValidateCatalogUseCase validateCatalogUseCase;

    @Autowired
    private CloseCatalogUseCase closeCatalogUseCase;

    @Test
    void testCompleteCatalogLifecycle() {
        // Create
        Catalog catalog = createCatalogUseCase.execute(
                "Threshold Based volume/amount",
                CatalogType.CASH,
                "EUR",
                "UTXXXX"
        );
        assertThat(catalog.getCatalogUniqueId()).isNotNull();
        assertThat(catalog.getStatus()).hasToString("DRAFT");

        // Update
        Catalog updated = updateCatalogUseCase.execute(
                catalog.getCatalogUniqueId(),
                LocalDate.of(2026, 5, 19),
                "Mise à jour des seuils",
                "UTXXXX"
        );
        assertThat(updated.getDescription()).isEqualTo("Mise à jour des seuils");

        // Submit
        Catalog withProduct = updated.toBuilder()
                .products(java.util.Set.of(Product.builder().uniqueProductId("P001").build()))
                .build();
        Catalog submitted = submitCatalogUseCase.execute(withProduct.getCatalogUniqueId());
        assertThat(submitted.getStatus()).hasToString("SUBMITTED");
    }
}
