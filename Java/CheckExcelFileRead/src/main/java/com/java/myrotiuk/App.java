package com.java.myrotiuk;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Hello world!
 *
 */
//http://stackoverflow.com/questions/14740727/upload-excel-file-into-database-using-apache-poi-and-spring-framework
//http://docs.spring.io/spring-framework/docs/2.0.8/api/org/springframework/web/multipart/commons/CommonsMultipartFile.html
public class App 
{
    public static void main( String[] args )
    {
    	List<User> users = new ArrayList<>();
    	 try
         {
    		 File fileToParse = new File("src/test.xlsx");

             InputStream file = new FileInputStream(fileToParse);
             String nameOfFile = fileToParse.getName();
             System.out.println(nameOfFile);
             Iterator<Row> rowIterator = null;
             if(nameOfFile.matches("\\w{1,}.xls")){
             //Create Workbook instance holding reference to .xlsx file
             
            	 HSSFWorkbook workbook = new HSSFWorkbook(file);
            	 //Get first/desired sheet from the workbook
             
            	 HSSFSheet sheet = workbook.getSheetAt(0);
            	 rowIterator = sheet.iterator();
             
             }else if(nameOfFile.matches("\\w{1,}.xlsx")){
            	XSSFWorkbook workbook = new XSSFWorkbook(file);
            	XSSFSheet sheet = workbook.getSheetAt(0); 
            	rowIterator = sheet.iterator();
             }
             //Iterate through each rows one by one
             
             while (rowIterator.hasNext()) 
             {
                 Row row = rowIterator.next();
                 //For each row, iterate through all the columns
                 Iterator<Cell> cellIterator = row.cellIterator();
                  
                 while (cellIterator.hasNext()) 
                 {
                     Cell cell = cellIterator.next();
                     //Check the cell type and format accordingly
                     switch (cell.getCellType()) 
                     {
                         case Cell.CELL_TYPE_NUMERIC:
                             System.out.print(cell.getNumericCellValue() + "t");
                             break;
                         case Cell.CELL_TYPE_STRING:
                             //System.out.print(cell.getStringCellValue() + "t");
                             String userName = cell.getStringCellValue();
                             String[] splitedUserName = userName.trim().split("\\s");
                             if(splitedUserName.length > 1 && splitedUserName.length < 3){
                            	 String firstName = splitedUserName[0];
                            	 String lastName = splitedUserName[1];
                            	 User user = new User(userName);
                                 user.setEmail(firstName+"_"+lastName+"@epam.com");
                                 users.add(user);
                             }else if(splitedUserName.length == 3){
                            	 String firstName = splitedUserName[0];
                            	 String lastName = splitedUserName[1];
                            	 String lastName2 = splitedUserName[2];
                            	 User user = new User(userName);
                                 user.setEmail(firstName+"_"+lastName+"_"+lastName2+"@epam.com");
                                 users.add(user);
                             }
                             break;
                     }
                 }
                 System.out.println("");
             }
             file.close();
            // fileToParse.delete();
         } 
         catch (Exception e) 
         {
             e.printStackTrace();
         }
    	 
    	 System.out.println(users);
    }
}
