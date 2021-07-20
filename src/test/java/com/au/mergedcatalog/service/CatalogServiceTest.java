package com.au.mergedcatalog.service;

import com.au.mergedcatalog.TestDataSupport;
import com.au.mergedcatalog.entities.Company;
import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import com.au.mergedcatalog.fileprocessor.CatalogFileProcessor;
import com.au.mergedcatalog.fileprocessor.CatalogHelper;
import com.au.mergedcatalog.mappers.MapStructMapper;
import com.au.mergedcatalog.repository.CombinedCatalogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {
    @Mock
    CatalogHelper catalogHelper;
    @Mock
    MapStructMapper mapper;
    @Mock
    CatalogServiceUtils mockCatalogUtils;

    @InjectMocks
    CatalogServiceImpl catalogService;
    String combinedCatalogPath = "/Users/oathkeeper/DEV/mergedcatalog/src/test/resources/inputcsv/combinedcatalog.csv";
    @Test
    public void shouldBuildFinalCatalog() {
         List<Product> productList = new ArrayList<>();
        productList.add(TestDataSupport.getProduct());
        var dto = TestDataSupport.getProductDto();
        List<ProductDto> dtoList = new ArrayList<>();
        dtoList.add(dto);
        when(catalogHelper.enrichProduct(Company.A)).thenReturn(productList);
        when(mapper.productToProductDto(productList.get(0))).thenReturn(dto);
        List<ProductDto> catalog = catalogService.buildFinalCatalog();
        assertNotNull(catalog);
        var proddto = catalog.get(0);
        assertEquals("some product", proddto.getDescription());
        assertEquals("A", proddto.getSource());
        assertEquals("647-vyk-317", proddto.getSku());
        
    }

}
