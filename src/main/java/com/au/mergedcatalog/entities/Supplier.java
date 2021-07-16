package com.au.mergedcatalog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Supplier {
    @CsvBindByName
    private String id;
    @CsvBindByName
    private String name;
}
