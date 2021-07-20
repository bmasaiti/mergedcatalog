package com.au.mergedcatalog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto {
    private static final long serialVersionUID = 1L;
    @CsvBindByName
    private String sku;
    @CsvBindByName
    private String description ;
    @CsvBindByName
    private String source;

}
