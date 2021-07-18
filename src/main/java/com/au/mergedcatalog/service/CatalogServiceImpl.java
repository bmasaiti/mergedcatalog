package com.au.mergedcatalog.service;

import com.au.mergedcatalog.entities.Company;
import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import com.au.mergedcatalog.fileprocessor.CatalogFileProcessor;
import com.au.mergedcatalog.fileprocessor.CatalogHelper;
import com.au.mergedcatalog.mappers.MapStructMapper;
import com.au.mergedcatalog.repository.CombinedCatalogRepository;
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
    private final CombinedCatalogRepository combinedCatalogRepository;
    @Value("${csvfilebase.output}")
    String basepath;

    @Override
    public List buildFinalCatalog() {
         String outputFilePath = basepath+"/FinalCatalog.csv" ;
        List<Product> enrichedCatalogA = catalogHelper.enrichProduct(Company.A);
        List<Product> enrichedCatalogB = catalogHelper.enrichProduct(Company.B);

        SortedSet<Product> finalCat  = new TreeSet<>(Comparator.comparing(Product::getBarcode));
        finalCat.addAll(enrichedCatalogA);
        finalCat.addAll(enrichedCatalogB);
           List<Product> finalProductCatalog = new ArrayList<>(finalCat);
           List<ProductDto>  productDtoList  = new ArrayList<>();
           writeFinalCatalogToDb(finalProductCatalog);

        for (var item:
                finalProductCatalog) {
            var prodDto = mapper.productToProductDto(item);
            productDtoList.add(prodDto) ;

        }

        CatalogFileProcessor.writeCombinedCatalogToCsVFile(productDtoList,outputFilePath);
         log.info(productDtoList.toString());
        return productDtoList;
    }

    private void writeFinalCatalogToDb(List<Product> finalProductCatalog) {
        combinedCatalogRepository.saveAll(finalProductCatalog);
    }


}
