package com.nosm.catalog.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for Catalog Aggregate Root
 */
@DisplayName("Catalog Entity Tests")
class CatalogTest {

    private Catalog catalog;

    @BeforeEach
    void setUp() {
        catalog = Catalog.createNew("Test Catalog", CatalogType.CASH, "EUR", "USER001");
    }

    @Nested
    @DisplayName("Creation Tests")
    class CreationTests {
        @Test
        @DisplayName("Should create new catalog with DRAFT status")
        void shouldCreateNewCatalogInDraft() {
            assertThat(catalog.getStatus()).isEqualTo(CatalogStatus.DRAFT);
            assertThat(catalog.getCatalogVersion()).isEqualTo("9.1");
            assertThat(catalog.getCatalogUniqueId()).isNotNull();
            assertThat(catalog.getCreatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Should generate unique catalog ID")
        void shouldGenerateUniqueCatalogId() {
            Catalog catalog2 = Catalog.createNew("Test Catalog 2", CatalogType.CREDIT, "EUR", "USER002");
            assertThat(catalog.getCatalogUniqueId()).isNotEqualTo(catalog2.getCatalogUniqueId());
        }
    }

    @Nested
    @DisplayName("Draft Modification Tests")
    class DraftModificationTests {
        @Test
        @DisplayName("Should allow modification when in DRAFT status")
        void shouldAllowModificationInDraft() {
            LocalDate activationDate = LocalDate.of(2026, 5, 19);
            String description = "Updated description";

            catalog.updateDraft(activationDate, description, "USER001");

            assertThat(catalog.getActivationDate()).isEqualTo(activationDate);
            assertThat(catalog.getDescription()).isEqualTo(description);
            assertThat(catalog.getLastModifiedBy()).isEqualTo("USER001");
        }

        @Test
        @DisplayName("Should reject modification when not in DRAFT")
        void shouldRejectModificationWhenNotDraft() {
            catalog.addProduct(Product.builder().uniqueProductId("P001").build());
            catalog.submit();

            assertThatThrownBy(() -> catalog.updateDraft(LocalDate.now(), "Description", "USER"))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("DRAFT");
        }
    }

    @Nested
    @DisplayName("Submission Tests")
    class SubmissionTests {
        @Test
        @DisplayName("Should submit catalog with products")
        void shouldSubmitCatalogWithProducts() {
            catalog.addProduct(Product.builder().uniqueProductId("P001").build());
            catalog.submit();

            assertThat(catalog.getStatus()).isEqualTo(CatalogStatus.SUBMITTED);
            assertThat(catalog.getSubmittedAt()).isNotNull();
        }

        @Test
        @DisplayName("Should reject submission without products")
        void shouldRejectSubmissionWithoutProducts() {
            assertThatThrownBy(() -> catalog.submit())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("au moins 1 produit");
        }

        @Test
        @DisplayName("Should reject resubmission")
        void shouldRejectResubmission() {
            catalog.addProduct(Product.builder().uniqueProductId("P001").build());
            catalog.submit();

            assertThatThrownBy(() -> catalog.submit())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("DRAFT");
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {
        @Test
        @DisplayName("Should validate submitted catalog")
        void shouldValidateCatalog() {
            catalog.addProduct(Product.builder().uniqueProductId("P001").build());
            catalog.submit();
            catalog.validate();

            assertThat(catalog.getStatus()).isEqualTo(CatalogStatus.VALIDATED);
            assertThat(catalog.getValidatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Should reject validation of non-submitted catalog")
        void shouldRejectValidationOfNonSubmitted() {
            assertThatThrownBy(() -> catalog.validate())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("SUBMITTED");
        }
    }

    @Nested
    @DisplayName("Closure Tests")
    class ClosureTests {
        @Test
        @DisplayName("Should close validated catalog")
        void shouldCloseCatalog() {
            catalog.addProduct(Product.builder().uniqueProductId("P001").build());
            catalog.submit();
            catalog.validate();
            catalog.close();

            assertThat(catalog.getStatus()).isEqualTo(CatalogStatus.CLOSED);
            assertThat(catalog.getClosedAt()).isNotNull();
        }

        @Test
        @DisplayName("Should reject closure of non-validated catalog")
        void shouldRejectClosureOfNonValidated() {
            assertThatThrownBy(() -> catalog.close())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("VALIDATED");
        }
    }

    @Nested
    @DisplayName("Product Management Tests")
    class ProductManagementTests {
        @Test
        @DisplayName("Should add product to DRAFT catalog")
        void shouldAddProductToDraft() {
            Product product = Product.builder().uniqueProductId("P001").build();
            catalog.addProduct(product);

            assertThat(catalog.getProductCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should reject product addition when not DRAFT")
        void shouldRejectProductAdditionWhenNotDraft() {
            catalog.addProduct(Product.builder().uniqueProductId("P001").build());
            catalog.submit();

            assertThatThrownBy(() -> catalog.addProduct(Product.builder().uniqueProductId("P002").build()))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("DRAFT");
        }
    }
}
