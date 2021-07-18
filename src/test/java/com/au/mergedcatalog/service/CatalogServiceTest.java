package com.au.mergedcatalog.service;

import com.au.mergedcatalog.TestDataSupport;
import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import com.au.mergedcatalog.fileprocessor.CatalogHelper;
import com.au.mergedcatalog.mappers.MapStructMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {
    @Mock
    CatalogHelper catalogHelper;
    @Mock
    MapStructMapper mapper;

    @InjectMocks
    CatalogService catalogService = new CatalogServiceImpl(catalogHelper, mapper);

    @Test
    public void shouldBuildFinalCatalog() {
        List<Product> productList = new ArrayList<>();
        productList.add(TestDataSupport.getProduct());
        when(catalogHelper.enrichProduct(any())).thenReturn(productList);
        //when(mapper.productToProductDto(rr.get(0))).thenReturn(dto);
        var catalog = catalogService.buildFinalCatalog();
        assertNotNull(catalog);
        assertEquals("soem value ", catalog.get(0).getSku());
        assertEquals("soem value ", catalog.get(0).getDescription());
        assertEquals("soem value ", catalog.get(0).getSource());
    }

}
