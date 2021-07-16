package com.au.mergedcatalog.service;

import com.au.mergedcatalog.entities.OldCatalog;
import com.au.mergedcatalog.fileprocessor.CatalogHelper;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class CatalogServiceImpl {

    private final CatalogHelper catalogHelper;

    public List buildFinalCatalog() {
        List<OldCatalog> enrichedCatalogA = catalogHelper.enrichProduct("", Company.A);
        List<OldCatalog> enrichedCatalogB = catalogHelper.enrichProduct("", Company.B);

        return Stream.concat(enrichedCatalogA.stream(), enrichedCatalogB.stream())
                .map(x -> x.getBarcode())
                .distinct()
                .collect(Collectors.toList());
    }
}
