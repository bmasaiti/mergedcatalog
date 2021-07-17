package com.au.mergedcatalog.service;

import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import com.au.mergedcatalog.fileprocessor.CatalogFileProcessor;
import com.au.mergedcatalog.fileprocessor.CatalogHelper;
import com.au.mergedcatalog.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;


@RequiredArgsConstructor
@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogHelper catalogHelper;
    private final MapStructMapper mapper;
    @Value("${csvfilebase.output}")
    String basepath;

    @Override
    public List buildFinalCatalog() {
         File outputFilePath = new File(basepath+"buildFinalCatalog.csv") ;
        List<Product> enrichedCatalogA = catalogHelper.enrichProduct(Company.A);
        List<Product> enrichedCatalogB = catalogHelper.enrichProduct(Company.B);

        SortedSet<Product> finalCat  = new TreeSet<>(Comparator.comparing(Product::getBarcode));
        finalCat.addAll(enrichedCatalogA);
        finalCat.addAll(enrichedCatalogB);
           List<Product> finalProductCatalog = new ArrayList<>(finalCat);
           List<ProductDto>  productDtoList  = new ArrayList<>();

        for (var item:
                finalProductCatalog) {
            var prodDto = mapper.productToProductDto(item);
            productDtoList.add(prodDto) ;

        }

        CatalogFileProcessor.beanToCsvConverter(productDtoList,outputFilePath);
         log.info(productDtoList.toString());
        return productDtoList;
    }


}
