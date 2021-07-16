package com.au.mergedcatalog.fileprocessor;

import com.au.mergedcatalog.entities.Barcode;
import com.au.mergedcatalog.entities.FinalCatalog;
import com.au.mergedcatalog.entities.OldCatalog;
import com.au.mergedcatalog.entities.Supplier;
import com.au.mergedcatalog.service.Company;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.loader.plan.build.internal.LoadGraphLoadPlanBuildingStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CatalogHelper {
    private CatalogHelper(){}
    @SneakyThrows
    public  List getSuppliers(){
        return CatalogFileProcessor.csvToBeanConverter( new Supplier() , "");
    }

    public  List getBarcodes() throws IOException {
        return  CatalogFileProcessor.csvToBeanConverter(new Barcode(),"") ;
    }

    public  List getOldCatalogA() throws IOException {
        return CatalogFileProcessor.csvToBeanConverter(new OldCatalog(),"");

    }

    public  List getOldCatalogB() throws IOException {
        return CatalogFileProcessor.csvToBeanConverter(new OldCatalog(),"");

    }

    @SneakyThrows
    public  void createFinalCatalog(List<FinalCatalog> finalCatalogList) {
        CatalogFileProcessor.beanToCsvConverter(finalCatalogList,new File("finalCatalogList.csv"));
    }

    public List getOldCatalogWithSource(String inputFile, Company company){
        List<OldCatalog>catalog = null;
        try{
            List<OldCatalog> rawCatalog = CatalogFileProcessor.csvToBeanConverter(new OldCatalog(),"") ;

            for (var item:
                    rawCatalog) {
                item.setSourceCatalog(company.toString());
                catalog.add(item);
        }

        } catch(IOException e){
         log.error("Failed to retrieve old catalog",e.getCause());
        }
        return catalog;
    }

    public List enrichProduct(String inputFile, Company company) {
        List<OldCatalog> oldCatalog = getOldCatalogWithSource(inputFile, company);
        List<Barcode> barcodelist = new ArrayList<>();
        List<OldCatalog> enrichedProducts = new ArrayList<>();
        oldCatalog.stream().forEach(
                product -> {
                    var barcode = barcodelist.stream().filter(x -> x.getSku().equalsIgnoreCase(product.getSku())).findFirst();
                    product.setBarcode(barcode.get().getBarcode());
                    product.setSupplierId(barcode.get().getSupplierId()) ;
                    enrichedProducts.add(product);
                }
        );

        return enrichedProducts;
    }
}
