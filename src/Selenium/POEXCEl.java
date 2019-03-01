package Selenium;

import org.apache.poi.ss.usermodel.*;

import java.io.File;

/**
 * @author bockey
 */
public class POEXCEl {

    public int getRowslenth(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        int firstRowNum = sheet.getFirstRowNum();
        return lastRowNum - firstRowNum;
    }

    public Sheet getSheetsFromPoi(File file) throws Exception {
        Workbook wb = WorkbookFactory.create(file);
        return wb.getSheetAt(0);
    }


    public String readRow(Sheet sheet, int colIndex, int rowIndex) {
        Row row = sheet.getRow(colIndex);
        Cell cell = row.getCell(rowIndex);
        if (cell.getCellType().toString().equals("NUMERIC")) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return cell.getStringCellValue();
    }


}
