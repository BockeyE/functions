package ExcelExprac;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * @author bockey
 */
public class ExcelRW {


    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public File f = new File("tem.xlsx");
    public File f2 = new File("tem1.xlsx");
    public File f3 = new File("tem1.xlsx");


    private int getRowslenth(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        int firstRowNum = sheet.getFirstRowNum();
        return lastRowNum - firstRowNum;
    }

    private Sheet getSheetsFromPoi(File file) throws Exception {
        Workbook wb = WorkbookFactory.create(file);
        return wb.getSheetAt(0);
    }


    private ArrayList readRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        ArrayList tem = new ArrayList();
//        Iterator<Cell> cellIterator = row.cellIterator();
        for (Cell cell : row) {
            if (cell.getCellType().toString().equals("NUMERIC")) {
                tem.add(cell.getNumericCellValue() + "");
                continue;
            }
            tem.add(cell.getStringCellValue());
        }
        return tem;
    }

    public HashSet readExcel(File file) throws Exception {
        Sheet sheetsFromPoi = getSheetsFromPoi(file);
        int rowslenth = getRowslenth(sheetsFromPoi);
        System.out.println(rowslenth);
        HashSet res = new HashSet();
        for (int i = 1; i <= rowslenth; i++) {
            ArrayList arrayList = readRow(sheetsFromPoi, i);
            res.add(arrayList);
        }
        return res;
    }


    /**
     * excel 写出流，必须有一个源文件excel，读取成workbook对象，
     * 修改sheet对象后，输出到一个新的文件对象中，可以创建一个临时file
     *
     * @param
     * @throws Exception
     */
    public FileOutputStream writeAssetExcel(Object fields, List data, FileOutputStream fos) throws Exception {
        Workbook wb = WorkbookFactory.create(true);
        Sheet sheet = wb.createSheet();
        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
        String[] a = {"通过审核", "未通过审核"};
        Row row0 = sheet.createRow(0);
        setRow(row0, fields);
        int size = data.size();
        DataValidationConstraint explicitListConstraint = dataValidationHelper.createExplicitListConstraint(a);
        DataValidation validation = dataValidationHelper.createValidation(explicitListConstraint, new CellRangeAddressList(1, size, 10, 10));
        validation.setShowErrorBox(true);
// 设置提示框
        validation.createPromptBox("提示", "请正确选择状态!");
        validation.setShowPromptBox(true);
        sheet.addValidationData(validation);

        Row row = null;
        for (int i = 0; i < size; i++) {
            row = sheet.createRow(i + 1);
            setRow(row, data.get(i));
        }
        wb.write(fos);
        return fos;
    }


    private void setRow(Row row0, Object fields) {
        row0.createCell(0).setCellValue(fields.toString());

    }


}


