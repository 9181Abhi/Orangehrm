package utility;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class Dataprovider {

	 @DataProvider(name = "LoginData")
	    public String[][] getData() throws IOException   {

	        String path = ".\\testdata\\Data.xlsx"; // taking xl file from testData folder

	        ExcelUtility xlutil = new ExcelUtility(path); // creating an object for ExcelUtility

	        int totalrows = xlutil.getRowCount("Sheet1");
	        int totalcols = xlutil.getCellCount("Sheet1", 1);

	        String logindata[][] = new String[totalrows][totalcols]; // created for 2D array to store data

	        for (int i = 1; i <= totalrows; i++) { // read the data from xl storing in 2D array
	            for (int j = 0; j < totalcols; j++) {
	                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j); // i=1,0
	            }
	        }
	        return logindata; // returning 2D array
	    }
		
}
