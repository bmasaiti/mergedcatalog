package com.au.mergedcatalog.fileprocessor;

import com.au.mergedcatalog.entities.Barcode;
import com.au.mergedcatalog.entities.ProductDto;
import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.Supplier;
import com.au.mergedcatalog.service.Company;
import lombok.SneakyThrows;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CatalogHelper {

    @Value("${csvfilebase.input}")
    String basepath;
    private CatalogHelper() {
    }

    @SneakyThrows
    public List getSuppliers() {
        return CatalogFileProcessor.csvToBeanConverter(new Supplier(), "");
    }

    public List getBarcodes(Company company) {
        List<Barcode> barcodes = new ArrayList<>();
        String inputFile = basepath +"/barcodes" + company+".csv";
        barcodes = CatalogFileProcessor.csvToBeanConverter(new Barcode(), inputFile);
        return barcodes;
    }

    @SneakyThrows
    public void createFinalCatalog(List<ProductDto> finalCatalogList) {
        CatalogFileProcessor.beanToCsvConverter(finalCatalogList, new File("finalCatalogList.csv"));
    }

    public List getOldCatalogWithSource(Company company) {
        String inputFile = basepath + "/catalog" + company + ".csv";
        List<Product> catalog = new ArrayList<>();
        List<Product> rawCatalog = CatalogFileProcessor.csvToBeanConverter(new Product(), inputFile);

        for (var item :
                rawCatalog) {
            item.setSource(company.toString());
            catalog.add(item);
        }

        return catalog;
    }

    public List enrichProduct(Company company) {

        List<Product> oldCatalog = getOldCatalogWithSource(company);
        List<Barcode> barcodelist = getBarcodes(company);
        List<Product> enrichedProducts = new ArrayList<>();
        oldCatalog.stream().forEach(
                product -> {
                    var barcode = barcodelist.stream().filter(x -> x.getSku().equalsIgnoreCase(product.getSku())).findFirst();
                    product.setBarcode(barcode.get().getBarcode());
                    product.setSupplierId(barcode.get().getSupplierId());
                    enrichedProducts.add(product);
                }
        );

        return enrichedProducts;
    }
}
