package com.au.mergedcatalog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Product {
    @CsvBindByName
    private String sku;
    @CsvBindByName
    private String description ;
    @CsvBindByName
    private String source;
    private String supplierId;
    private String barcode;
}
