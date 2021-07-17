package com.au.mergedcatalog.fileprocessor;


import com.au.mergedcatalog.entities.ProductDto;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CatalogFileProcessor{

    private CatalogFileProcessor() {
    }

    public static List csvToBeanConverter(Object objectType ,String inputFile) {
        List<Object> inputList = new ArrayList<>();
        try {
        if (inputFile == null) {
            throw new IOException("No file uploaded!");
        }
            ClassLoader classLoader = CatalogFileProcessor.class.getClassLoader();
        var streamReader = new InputStreamReader(classLoader.getResourceAsStream(inputFile));
         inputList = new CsvToBeanBuilder(streamReader)
                .withIgnoreLeadingWhiteSpace(true)
                .withType(objectType.getClass())
               // .withFieldAsNull(CSVReaderNullFieldIndicator.valueOf("sourceCatalog"))
                .build()
                .parse();
    }catch(IOException e){             
        log.error("Failed to read inputfile", e.getCause());
    }
     return inputList;
    }

    public static void beanToCsvConverter(List<ProductDto> productDto, File fileName){
        try {
            Writer writer = new FileWriter(fileName);
            HeaderColumnNameMappingStrategy<ProductDto> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProductDto.class);
            log.info("Writing final catalog to file {}", fileName.toString());
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(strategy)
                    .withSeparator(',')
                    .build();
            beanToCsv.write(productDto);
            writer.close();
            log.info("Finished writing final catalog to file {}", fileName.toString());
            System.out.println("Processed trips file printed");
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

//
//try (InputStream inputStream = getClass().getResourceAsStream("/input.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//        String contents = reader.lines()
//        .collect(Collectors.joining(System.lineSeparator()));
//        }