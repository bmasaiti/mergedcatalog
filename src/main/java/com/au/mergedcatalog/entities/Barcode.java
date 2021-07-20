package com.au.mergedcatalog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Barcode {
    @CsvBindByName
    private String supplierId;
    @CsvBindByName
    private String sku;
    @CsvBindByName
    private String barcode;

}
