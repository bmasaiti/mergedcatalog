package com.au.mergedcatalog.fileprocessor;

import com.au.mergedcatalog.TestDataSupport;
import com.au.mergedcatalog.entities.Barcode;
import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CatalogFileProcessorTest {


    String catalogAPath = "/Users/oathkeeper/DEV/mergedcatalog/src/test/resources/inputcsv/catalogA.csv";
    String barcodeAPath  = "/Users/oathkeeper/DEV/mergedcatalog/src/test/resources/inputcsv/barcodesA.csv" ;
    String combinedCatalogPath = "/Users/oathkeeper/DEV/mergedcatalog/src/test/resources/inputcsv/combinedcatalog.csv";

    CatalogFileProcessor catalogFileProcessor = new CatalogFileProcessor();
    

    @Test
    public void shouldProduceListProductsFromCatalogCsvFile(){

        List<Product> productList = catalogFileProcessor.getListOfObjectsFromCsvFile(new Product(),catalogAPath);
        Product product = productList.get(0);
        assertNotNull(product);
        assertEquals("647-vyk-317", product.getSku());
        assertEquals("Walkers Special Old Whiskey", product.getDescription());

    }

    @Test
    public void shouldProduceListBarcodesFromBarcodeCsvFile(){

        List<Barcode> barcodeList = catalogFileProcessor.getListOfObjectsFromCsvFile(new Barcode(),barcodeAPath);
        Barcode barcode = barcodeList.get(0);
        assertNotNull(barcode);
        assertEquals("00001", barcode.getSupplierId());
        assertEquals("647-vyk-317", barcode.getSku());
        assertEquals("z2783613083817", barcode.getBarcode());

    }
    @Test
    public void shouldWriteProductDtoToCombinedCatalogFile() throws IOException {
        List<ProductDto> combinedCatalog = new ArrayList<>();
        combinedCatalog.add(TestDataSupport.getProductDto());

        catalogFileProcessor.writeCombinedCatalogToCsVFile(combinedCatalog,combinedCatalogPath);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(combinedCatalogPath), "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        var record = csvRecords.iterator().next();

        assertEquals("some product", record.get("description"));
        assertEquals("647-vyk-317", record.get("sku"));
        assertEquals("A", record.get("source"));


    }
}
