package com.au.mergedcatalog.fileprocessor;

import java.util.List;

public interface CatalogProcessor<T> {
      List<T> csvToBeanConverter(T np, String inputFile);

}
