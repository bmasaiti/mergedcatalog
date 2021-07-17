package com.au.mergedcatalog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private static final long serialVersionUID = 1L;
    @CsvBindByName
    private String sku;
    @CsvBindByName
    private String description ;
    @CsvBindByName
    private String source;

}
