package com.au.mergedcatalog;

import com.au.mergedcatalog.entities.Barcode;
import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;


public class TestDataSupport {

    public static Barcode getBarcodes() {
        Barcode barcode = new Barcode();
        barcode.setBarcode("z2783613083817");
        barcode.setSku("647-vyk-317");
        barcode.setSupplierId("00001");
        return barcode;
    }

    public static  Product getProduct(){
        Product product = new Product();
        product.setBarcode("z2783613083817");
        product.setDescription("some product");
        product.setSku("647-vyk-317");
        product.setSource("A");

      return  product;
    }

    public static ProductDto getProductDto(){
        ProductDto dto = new ProductDto();
        dto.setDescription("some product");
        dto.setSku("647-vyk-317");
        dto.setSource("A");
        return dto;
    }
}
