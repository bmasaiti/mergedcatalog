package com.au.mergedcatalog.fileprocessor;


import com.au.mergedcatalog.entities.ProductDto;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class CatalogFileProcessor{


    public  List getListOfObjectsFromCsvFile(Object objectType , String inputFile) {
        List<Object> inputList = new ArrayList<>();
        try {
        if (inputFile == null) {
            throw new IOException("No file uploaded!");
        }
            try (var streamReader = new InputStreamReader(new FileInputStream(inputFile))) {
                inputList = new CsvToBeanBuilder(streamReader)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withType(objectType.getClass())
                        .build()
                        .parse();
            }
        }catch(IOException e){
        log.error("Failed to read inputfile", e.getCause());
        System.exit(-1);
    }
     return inputList;
    }

    public  void writeCombinedCatalogToCsVFile(List<ProductDto> productDto, String fileName){
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(fileName));
            HeaderColumnNameMappingStrategy<ProductDto> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProductDto.class);
            log.info("Writing final catalog to file {}", fileName);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(strategy)
                    .withSeparator(',')
                    .build();
            beanToCsv.write(productDto);
            writer.close();
            log.info("Finished writing final catalog to file {}", fileName);
            return;
        }catch (FileNotFoundException e){
            log.error("Output file not found",e.getCause());
        }  catch (IOException ex){
            log.error(ex.getMessage());
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

}