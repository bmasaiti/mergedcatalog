#Trip Fare Calculator

##  How to run
   Setup java 11 and gradle 6.8.x or 7.x 
   1. Add absolute basepath to the input and output folders with csv files
   2.  Run ./gradlew clean build
   3.  Run ./gradlew bootRun
   4. Navigate to the location of the output file and to verify results written the specified output file.
   5. Navigate to h2db console to verify results written to the product table
   
   ## Assumptions
   1. This console application is built to work as a once off task of merging catalogs
   2. Once the catalog files are merged they need to be written to the db
   3. It is assumed the catalog files will be named as given in the instructions .   

   
   
   ## Potential improvements
   1. package the application in docker 
   2. Improve db access to allow normal reads and writes
   3. Increase test coveragefrom 84% to more than 90%
    
