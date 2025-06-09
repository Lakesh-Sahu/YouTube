package utility;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "itemToSearchForTotalViewCount", parallel = true)
    public static Object[][] excelData() {
        String fileLocation = System.getProperty("user.dir") + "/src/test/resources/data.xlsx";
        return ExcelReaderUtil.readSingleColumnExcelData(fileLocation, "Sheet1");
    }
}