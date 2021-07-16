package com.au.mergedcatalog.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Builder
public class EnrichedProduct {
    private String sku;
    private String description ;
    private String sourceCatalog ;
    private String supplierId;
    private String barcode;
}
