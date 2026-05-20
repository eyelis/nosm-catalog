package com.nosm.catalog.domain.model;

import lombok.*;
import java.math.BigDecimal;

/**
 * Entity: Product
 * Represents a product or service configured in a catalog.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uniqueProductId")
@ToString
public class Product {
    private String uniqueProductId;
    private String productVersionId;
    private String family;
    private String subFamily;
    private String productName;
    private String billingCode;
    private String scope;
    private String billingMode;
    private String calculationMode;
    private String priceUnit;
    private String priceType;
    private BigDecimal price;
    private String currency;
    private String accountingCode;
    private String afpCode;
    private String crtCode;
    private String gppCode;
    private boolean repairCorporateFlag;
    private boolean repairFiCommercialPayment;
    private boolean repairFiFinancialPayment;
    private boolean correspondentFlag;
    private boolean changeFlag;
}