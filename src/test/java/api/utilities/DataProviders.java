package api.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//All Data
	@DataProvider(name = "Data")
	String[][] getAllData() throws IOException
	{
		String Path = System.getProperty("user.dir")+"//testData//MOCK_DATA (1).xlsx";
		ExcelUtility xl = new ExcelUtility(Path);
		
		int rowCount = xl.getRowCount("Worksheet");
		int cellCount = xl.getCellCount("Worksheet", 1);
		
		String allData[][] =  new String[rowCount][cellCount];
		
		for(int i =1; i<= rowCount; i++)
		{
			for(int j =0; j<cellCount; j++)
			{
				 allData[i-1][j] = xl.getCellData("Worksheet", i, j);
			}
		}
		return allData;
	}
	
	//Username
	@DataProvider(name = "DataUsername")
	String[] getUsername() throws IOException
	{
		String Path = System.getProperty("user.dir")+"//testData//MOCK_DATA (1).xlsx";
		ExcelUtility xl = new ExcelUtility(Path);
		
		int rowCount = xl.getRowCount("Worksheet");
		
		
		String usernameData[] =  new String[rowCount];
		
		for(int i =1; i<= rowCount; i++)
		{
			
			usernameData[i-1] = xl.getCellData("Worksheet", i, 1);
			
		}
		return usernameData;
	}
}
