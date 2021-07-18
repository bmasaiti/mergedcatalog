package com.au.mergedcatalog.fileprocessor;

import com.au.mergedcatalog.TestDataSupport;
import com.au.mergedcatalog.entities.Barcode;
import com.au.mergedcatalog.entities.Company;
import com.au.mergedcatalog.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatalogHelperTest {

    CatalogHelper catalogHelper;

    @Test
    public void shouldReturnEnrichedProducts() {
        List<Product> products = new ArrayList<>();
        products.add(TestDataSupport.getProduct());
        List<Barcode> barcodelist = new ArrayList<>();
        barcodelist.add(TestDataSupport.getBarcodes());

        when(CatalogFileProcessor.getListOfObjectsFromCsvFile(Product.class,"")).thenReturn(products);
        when(CatalogFileProcessor.getListOfObjectsFromCsvFile(Barcode.class, "")).thenReturn(barcodelist);

        List<Product> enrichedProducts = catalogHelper.enrichProduct(Company.A);

        assertNotNull(enrichedProducts);
        assertEquals("z2783613083817", enrichedProducts.get(0).getBarcode());
        assertEquals("A", enrichedProducts.get(0).getSource());
        assertEquals("647-vyk-317", enrichedProducts.get(0).getSku());
        assertEquals("00001", enrichedProducts.get(0).getSupplierId());

    }

}
