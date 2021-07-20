package com.au.mergedcatalog.service;

import com.au.mergedcatalog.entities.Company;
import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import com.au.mergedcatalog.fileprocessor.CatalogHelper;
import com.au.mergedcatalog.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@RequiredArgsConstructor
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogHelper catalogHelper;
    private final MapStructMapper mapper;
    private final CatalogServiceUtils catalogServiceUtils;
    @Value("${csvfilebase.output}")
    String basepath;
    List<ProductDto> productDtoList = new ArrayList<>();
    @Override
    public List buildFinalCatalog() {
        String outputFilePath = basepath + "/FinalCatalog.csv";
        List<Product> enrichedCatalogA = catalogHelper.enrichProduct(Company.A);
        List<Product> enrichedCatalogB = catalogHelper.enrichProduct(Company.B);
        SortedSet<Product> finalCat = new TreeSet<>(Comparator.comparing(Product::getBarcode));
        finalCat.addAll(enrichedCatalogA);
        finalCat.addAll(enrichedCatalogB);
        List<Product> finalProductCatalog = new ArrayList<>(finalCat);
        generateProductDtoforCsvFile(finalProductCatalog, productDtoList);
        catalogServiceUtils.writeCatalogToCsvFile(outputFilePath, productDtoList);
        catalogServiceUtils.writeFinalCatalogToDb(finalProductCatalog);
        return productDtoList;
    }

    private void generateProductDtoforCsvFile(List<Product> finalProductCatalog, List<ProductDto> productDtoList) {
        for (var item :
                finalProductCatalog) {
            var prodDto = mapper.productToProductDto(item);
            productDtoList.add(prodDto);

        }
    }


}
