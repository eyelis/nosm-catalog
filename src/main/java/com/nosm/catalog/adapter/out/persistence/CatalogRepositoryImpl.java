package com.nosm.catalog.adapter.out.persistence;

import com.nosm.catalog.domain.model.Catalog;
import com.nosm.catalog.domain.model.CatalogStatus;
import com.nosm.catalog.domain.model.CatalogType;
import com.nosm.catalog.domain.port.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;

/**
 * Repository Adapter for Catalog Persistence
 * Implements the CatalogRepository interface using JDBC and SQL Server.
 */
@Repository
@RequiredArgsConstructor
public class CatalogRepositoryImpl implements CatalogRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Catalog save(Catalog catalog) {
        String sql = """
            IF EXISTS (SELECT 1 FROM catalogs WHERE catalog_unique_id = ?)
            BEGIN
                UPDATE catalogs 
                SET catalog_number = ?, catalog_version = ?, type = ?, name = ?, currency = ?, 
                    status = ?, last_modified_at = GETDATE(), last_modified_by = ?,
                    submitted_at = ?, validated_at = ?, closed_at = ?, 
                    activation_date = ?, description = ?
                WHERE catalog_unique_id = ?
            END
            ELSE
            BEGIN
                INSERT INTO catalogs (catalog_unique_id, catalog_number, catalog_version, type, 
                                    name, currency, status, created_at, created_by, 
                                    last_modified_at, last_modified_by, activation_date, description)
                VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), ?, GETDATE(), ?, ?, ?)
            END
            """;
        
        Object[] params = {
            catalog.getCatalogUniqueId(),
            catalog.getCatalogNumber(),
            catalog.getCatalogVersion(),
            catalog.getType().name(),
            catalog.getName(),
            catalog.getCurrency(),
            catalog.getStatus().name(),
            catalog.getLastModifiedBy(),
            catalog.getSubmittedAt() != null ? Timestamp.valueOf(catalog.getSubmittedAt()) : null,
            catalog.getValidatedAt() != null ? Timestamp.valueOf(catalog.getValidatedAt()) : null,
            catalog.getClosedAt() != null ? Timestamp.valueOf(catalog.getClosedAt()) : null,
            catalog.getActivationDate(),
            catalog.getDescription(),
            catalog.getCatalogUniqueId(),
            catalog.getCatalogUniqueId(),
            catalog.getCatalogNumber(),
            catalog.getCatalogVersion(),
            catalog.getType().name(),
            catalog.getName(),
            catalog.getCurrency(),
            catalog.getStatus().name(),
            catalog.getCreatedBy(),
            catalog.getLastModifiedBy(),
            catalog.getActivationDate(),
            catalog.getDescription()
        };
        
        jdbcTemplate.update(sql, params);
        return catalog;
    }

    @Override
    public Optional<Catalog> findById(String catalogUniqueId) {
        String sql = "SELECT * FROM catalogs WHERE catalog_unique_id = ?";
        try {
            Catalog catalog = jdbcTemplate.queryForObject(sql, 
                (rs, rowNum) -> Catalog.builder()
                    .catalogUniqueId(rs.getString("catalog_unique_id"))
                    .catalogNumber(rs.getString("catalog_number"))
                    .catalogVersion(rs.getString("catalog_version"))
                    .type(CatalogType.valueOf(rs.getString("type")))
                    .name(rs.getString("name"))
                    .currency(rs.getString("currency"))
                    .status(CatalogStatus.valueOf(rs.getString("status")))
                    .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                    .createdBy(rs.getString("created_by"))
                    .lastModifiedAt(rs.getTimestamp("last_modified_at") != null ? rs.getTimestamp("last_modified_at").toLocalDateTime() : null)
                    .lastModifiedBy(rs.getString("last_modified_by"))
                    .submittedAt(rs.getTimestamp("submitted_at") != null ? rs.getTimestamp("submitted_at").toLocalDateTime() : null)
                    .validatedAt(rs.getTimestamp("validated_at") != null ? rs.getTimestamp("validated_at").toLocalDateTime() : null)
                    .closedAt(rs.getTimestamp("closed_at") != null ? rs.getTimestamp("closed_at").toLocalDateTime() : null)
                    .activationDate(rs.getDate("activation_date") != null ? rs.getDate("activation_date").toLocalDate() : null)
                    .description(rs.getString("description"))
                    .build(),
                catalogUniqueId);
            return Optional.of(catalog);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Catalog> findAll() {
        String sql = "SELECT * FROM catalogs ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            Catalog.builder()
                .catalogUniqueId(rs.getString("catalog_unique_id"))
                .catalogNumber(rs.getString("catalog_number"))
                .catalogVersion(rs.getString("catalog_version"))
                .type(CatalogType.valueOf(rs.getString("type")))
                .name(rs.getString("name"))
                .currency(rs.getString("currency"))
                .status(CatalogStatus.valueOf(rs.getString("status")))
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .createdBy(rs.getString("created_by"))
                .build()
        );
    }

    @Override
    public void delete(String catalogUniqueId) {
        String sql = "DELETE FROM catalogs WHERE catalog_unique_id = ?";
        jdbcTemplate.update(sql, catalogUniqueId);
    }
}