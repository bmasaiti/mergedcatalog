package com.au.mergedcatalog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.ExampleMatcher;


@Getter
@Setter
public class OldCatalog {
    @CsvBindByName
    private String sku;
    @CsvBindByName
    private String description ;
    @CsvBindByName
    private String sourceCatalog ;
    private String supplierId;
    private String barcode;
}
