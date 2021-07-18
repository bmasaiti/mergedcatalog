package com.au.mergedcatalog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class Product {

    @CsvBindByName
    private String sku;
    @CsvBindByName
    private String description ;
    @CsvBindByName
    private String source;
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;
    private String supplierId;
    private String barcode;
   
}
