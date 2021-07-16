package com.au.mergedcatalog.fileprocessor;


import com.au.mergedcatalog.entities.FinalCatalog;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CatalogFileProcessor{
    private static final Logger LOG = LoggerFactory.getLogger(CatalogFileProcessor.class);

    private CatalogFileProcessor() {
    }

    public static List csvToBeanConverter(Object objectType ,String inputFile) throws IOException {
        if (inputFile == null) {
            throw new IOException("No file uploaded!");
        }
        var streamReader = new InputStreamReader(inputFile, StandardCharsets.UTF_8);

        return new CsvToBeanBuilder(streamReader)
                .withIgnoreLeadingWhiteSpace(true)
                .withType(objectType.getClass())
                .withFieldAsNull(CSVReaderNullFieldIndicator.valueOf("sourceCatalog"))
                .build()
                .parse();

    }

    public static void beanToCsvConverter(List<FinalCatalog> finalCatalog, File fileName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Writer writer = new FileWriter(fileName);
        HeaderColumnNameMappingStrategy<FinalCatalog> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(FinalCatalog.class);
        LOG.info("Writing processed trips to file {}",fileName.toString());
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withMappingStrategy(strategy)
                .withSeparator(',')
                .build();
        beanToCsv.write(finalCatalog);
        writer.close();
       LOG.info("Finished writing processed trips to file {}",fileName.toString());
        System.out.println("Processed trips file printed");
        return;
    }

}
