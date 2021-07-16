package com.au.mergedcatalog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalCatalog {
    @CsvBindByName
    private String sku;
    @CsvBindByName
    private String sourceCatalog;
    @CsvBindByName
    private String description ;
}
