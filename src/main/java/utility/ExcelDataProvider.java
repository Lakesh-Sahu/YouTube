package utility;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "excelData")
    public static Object[][] excelData() {
        String fileLocation = System.getProperty("user.dir") + "/src/test/resources/data.xlsx";
        return ExcelReaderUtil.readExcelData(fileLocation);
    }
}