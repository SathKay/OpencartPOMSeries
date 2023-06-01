package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	private static String REGISTER_PAGE_TEST_FILE = "./src/test/resources/testdata/OpenCartTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {
		
		Object data[][] = null; //method variable should be initialized with its default value always, if in case the 
								//for loop doesn't it should return null atleast
		
		try {
			FileInputStream input = new FileInputStream(REGISTER_PAGE_TEST_FILE);
			book = WorkbookFactory.create(input);  // This create a copy of the excel data in java memory  
			sheet = book.getSheet(sheetName);
			
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
			for(int i =0;i<sheet.getLastRowNum();i++) {
				for(int j=0; j<sheet.getRow(0).getLastCellNum();j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
		
	}

}
