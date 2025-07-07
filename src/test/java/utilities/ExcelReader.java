package utilities;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;


public class ExcelReader {
    public static String getCellData(String filePath, String sheetName, int row, int col) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row rowData = sheet.getRow(row);
            Cell cell = rowData.getCell(col);
            return cell.getStringCellValue();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}


