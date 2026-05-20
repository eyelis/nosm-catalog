-- SQL Server 2022 Schema for NOSM Catalog

CREATE TABLE catalogs (
    catalog_unique_id NVARCHAR(36) PRIMARY KEY,
    catalog_number NVARCHAR(50) NOT NULL,
    catalog_version NVARCHAR(20) NOT NULL,
    type NVARCHAR(20) NOT NULL,
    name NVARCHAR(255) NOT NULL,
    currency NVARCHAR(3) NOT NULL,
    status NVARCHAR(20) NOT NULL,
    created_at DATETIME2 NOT NULL,
    created_by NVARCHAR(50) NOT NULL,
    last_modified_at DATETIME2 NOT NULL,
    last_modified_by NVARCHAR(50) NOT NULL,
    submitted_at DATETIME2,
    validated_at DATETIME2,
    closed_at DATETIME2,
    activation_date DATE,
    description NVARCHAR(MAX),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

CREATE TABLE products (
    unique_product_id NVARCHAR(50) PRIMARY KEY,
    product_version_id NVARCHAR(50) NOT NULL,
    catalog_unique_id NVARCHAR(36) NOT NULL,
    family NVARCHAR(100) NOT NULL,
    sub_family NVARCHAR(100) NOT NULL,
    product_name NVARCHAR(255) NOT NULL,
    billing_code NVARCHAR(50) NOT NULL,
    scope NVARCHAR(MAX),
    billing_mode NVARCHAR(50),
    calculation_mode NVARCHAR(50),
    price_unit NVARCHAR(50),
    price_type NVARCHAR(50),
    price DECIMAL(19,4),
    currency NVARCHAR(3),
    accounting_code NVARCHAR(50),
    afp_code NVARCHAR(50),
    crt_code NVARCHAR(50),
    gpp_code NVARCHAR(50),
    repair_corporate_flag BIT,
    repair_fi_commercial_payment BIT,
    repair_fi_financial_payment BIT,
    correspondent_flag BIT,
    change_flag BIT,
    FOREIGN KEY (catalog_unique_id) REFERENCES catalogs(catalog_unique_id)
);

CREATE TABLE entities (
    entity_code NVARCHAR(50) PRIMARY KEY,
    entity_name NVARCHAR(255) NOT NULL,
    catalog_unique_id NVARCHAR(36) NOT NULL,
    FOREIGN KEY (catalog_unique_id) REFERENCES catalogs(catalog_unique_id)
);

CREATE TABLE segments (
    segment_code NVARCHAR(50) PRIMARY KEY,
    segment_name NVARCHAR(255) NOT NULL,
    catalog_unique_id NVARCHAR(36) NOT NULL,
    exceptional_list BIT NOT NULL,
    FOREIGN KEY (catalog_unique_id) REFERENCES catalogs(catalog_unique_id)
);