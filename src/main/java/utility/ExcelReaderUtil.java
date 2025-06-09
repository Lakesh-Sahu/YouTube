package utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderUtil {

    public static Object[][] readSingleColumnExcelData(String fileName, String sheetName) {
        try {
            InputStream file = new DataInputStream(new FileInputStream(fileName));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName); // read the given sheet

            List<Object[]> records = new ArrayList<>();
            int rowNum = sheet.getFirstRowNum() + 1; // Skip the header row
            int totalRows = findLastNonBlankRow(sheet);

            for (int i = rowNum; i <= totalRows; i++) {
                Row row = sheet.getRow(i);
                List<Object> columns = new ArrayList<>();

                Cell cell = row.getCell(0);
                columns.add(getCellValue(cell));

                records.add(columns.toArray());
            }

            workbook.close();
            return records.toArray(new Object[0][]);
        } catch (Exception e) {
            return null;
        }
    }

    // Find the last non-blank row in a sheet
    public static int findLastNonBlankRow(Sheet sheet) {
        if (sheet != null) {
            for (int i = sheet.getLastRowNum(); i >= sheet.getFirstRowNum(); i--) {
                Row row = sheet.getRow(i);
                if (!isRowBlank(row)) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Find the last non-blank column in a given row
    public static int findLastNonBlankColumn(Row row) {
        int lastNonBlankColumnNum = -1;
        if (row != null) {
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    lastNonBlankColumnNum = j;
                }
            }
        }
        return lastNonBlankColumnNum;
    }

    // Helper method to determine if a row is blank
    private static boolean isRowBlank(Row row) {
        if (row == null || row.getFirstCellNum() < 0 || row.getLastCellNum() < 0) {
            return true;
        }

        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private static Object getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> cell.getNumericCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            default -> null;
        };
    }
}