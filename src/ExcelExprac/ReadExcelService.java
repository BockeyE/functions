package ExcelExprac;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author bockey
 */
@Service
public class ReadExcelService {

    private int getRowslenth(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        int firstRowNum = sheet.getFirstRowNum();
        return lastRowNum - firstRowNum;
    }

    private Sheet getSheetByFile(File file) throws Exception {
        Workbook wb = WorkbookFactory.create(file);
        return wb.getSheetAt(0);
    }

    private ArrayList readRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        ArrayList<String> tem = new ArrayList<>();
        for (Cell cell : row) {
            if (cell.getCellType().toString().equals("NUMERIC")) {
                tem.add(((Double) cell.getNumericCellValue()).intValue() + "");
                continue;
            }
            tem.add(cell.getStringCellValue());
        }
        return tem;
    }

    public HashSet readExcel(File file) throws Exception {
        Sheet sheetsFromPoi = getSheetByFile(file);
        int rowslenth = getRowslenth(sheetsFromPoi);
        System.out.println(rowslenth);
        HashSet res = new HashSet();
        for (int i = 1; i <= rowslenth; i++) {
            ArrayList arrayList = readRow(sheetsFromPoi, i);
            res.add(arrayList);
        }
        return res;
    }

//
//    /*
//     * excel 写出流，读取成workbook对象，
//     * 修改sheet对象后，输出到一个新的文件对象中，可以创建一个临时file
//     */
//    public void getExcelStream(AcmsProjFields fields, List<AcmsAsset> data, FileOutputStream fos, Integer target) throws Exception {
//        Workbook wb = WorkbookFactory.create(true);
//        Sheet sheet = wb.createSheet();
//        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
//        int size = data.size();
//        if (NULL.not(target) && size > 0) {  //添加验证列表
//            String[] a = AssetStatusUtil.getStrsByCode(target);
//            DataValidationConstraint explicitListConstraint = dataValidationHelper.createExplicitListConstraint(a);
//            //添加验证范围
//            DataValidation validation = dataValidationHelper.createValidation(explicitListConstraint, new CellRangeAddressList(1, size, 10, 10));
//            validation.setShowErrorBox(true);
//            // 设置提示框
//            validation.createPromptBox("提示", "请正确选择状态!");
//            validation.setShowPromptBox(true);
//            sheet.addValidationData(validation);
//        }
//        Row row0 = sheet.createRow(0);
//        setRow0(row0, fields);
//        Row row = null;
//        for (int i = 0; i < size; i++) {
//            row = sheet.createRow(i + 1);
//            setRowi(row, data.get(i));
//        }
//        wb.write(fos);
//    }
//
//    private void setRow0(Row row0, AcmsProjFields fields) {
//        row0.createCell(0).setCellValue("资产序号");
//        row0.createCell(1).setCellValue(fields.getBuyer());
//        row0.createCell(2).setCellValue(fields.getSeller());
//        row0.createCell(3).setCellValue(fields.getAmount());
//        row0.createCell(4).setCellValue(fields.getBillNumber());
//        row0.createCell(5).setCellValue(fields.getDueDate());
//        row0.createCell(6).setCellValue(fields.getInvoiceNumber());
//        row0.createCell(7).setCellValue(fields.getInvoiceAmount());
//        row0.createCell(8).setCellValue(fields.getContractNumber());
//        row0.createCell(9).setCellValue(fields.getPerformFilename());
//        row0.createCell(10).setCellValue(fields.getAssetFilename());
//        row0.createCell(11).setCellValue("资产状态");
//    }
//
//    private void setRowi(Row row, AcmsAsset asset) {
//        row.createCell(0).setCellValue(asset.getAssetId().toString());
//        row.createCell(1).setCellValue(asset.getBuyer());
//        row.createCell(2).setCellValue(asset.getSeller());
//        row.createCell(3).setCellValue(asset.getAmount());
//        row.createCell(4).setCellValue(asset.getBillNumber());
//        row.createCell(5).setCellValue(asset.getDueDate());
//        row.createCell(6).setCellValue(asset.getInvoiceNumber());
//        row.createCell(7).setCellValue(asset.getInvoiceAmount());
//        row.createCell(8).setCellValue(asset.getContractNumber());
//        row.createCell(9).setCellValue(asset.getPerformFilename());
//        row.createCell(10).setCellValue(asset.getAssetFilename());
//        row.createCell(11).setCellValue(AcmsAsset.AssetStatus.getDescByCode(asset.getAssetStatus()));
//    }
//
//
//    public HashMap<Integer, Integer> readForStatus(MultipartFile file) throws Exception {
//        File f = File.createTempFile(UUID.randomUUID().toString(), ".xlsx");
//        file.transferTo(f);
//        HashMap<Integer, Integer> d = readForStatus(f);
//        f.delete();
//        return d;
//    }
//
//    /*
//     * 只读取excel中的 资产id和修改后的状态str，转换为int，集成到map中
//     */
//    public HashMap<Integer, Integer> readForStatus(File file) throws Exception {
//        Sheet sheet = getSheetByFile(file);
//        int rowslenth = getRowslenth(sheet);
//        System.out.println("lenth");
//        System.out.println(rowslenth);
//        HashMap<Integer, Integer> m = new HashMap<>();
//        Row row = null;
//        for (int i = 1; i <= rowslenth; i++) {
//            row = sheet.getRow(i);
//            String s0 = "";
//            System.out.println(row.getLastCellNum());
//            if (row.getCell(0).getCellType().toString().equals("NUMERIC")) {
//                s0 = row.getCell(0).getNumericCellValue() + "";
//            } else {
//                s0 = row.getCell(0).getStringCellValue();
//            }
//            System.out.println(s0);
//            String stringCellValue1 = row.getCell(1).getStringCellValue();
//            System.out.println(stringCellValue1);
//            System.out.println(i);
//            Integer aimcode = AcmsAsset.AssetStatus.getIntByDesc(stringCellValue1);
//            m.put(Integer.parseInt(s0), aimcode);
//        }
//        return m;
//    }
//
//
//    public ArrayList<AcmsAsset> readForNew(MultipartFile file) throws Exception {
//        File f = File.createTempFile(UUID.randomUUID().toString(), ".xlsx");
//        file.transferTo(f);
//        ArrayList<AcmsAsset> assets = readForNew(f);
//        f.delete();
//        System.out.println(f.exists());
//        return assets;
//    }
//
//    /*
//     * 读取新上传的资产提交列表，
//     */
//    public ArrayList<AcmsAsset> readForNew(File file) throws Exception {
//        Sheet sheet = getSheetByFile(file);
//        int rowslenth = getRowslenth(sheet);
//        System.out.println("lenth");
//        System.out.println(rowslenth);
//        ArrayList<AcmsAsset> ar = new ArrayList<>();
//        Row row = null;
//        AcmsAsset acmsAsset = null;
//        for (int i = 1; i <= rowslenth; i++) {
//            try {
//                row = sheet.getRow(i);
//                acmsAsset = readRowForNewAsset(row);
//                ar.add(acmsAsset);
//            } catch (Exception e) {
//                System.out.println(e);
//                System.out.println("catches");
//            }
//        }
//        return ar;
//    }
//
//    /*
//    读取row信息。封装到asset类中，其中状态统一设置为1，初始提交\待筛选状态
//     */
//    private AcmsAsset readRowForNewAsset(Row row) throws ParseException {
//        AcmsAsset a = new AcmsAsset();
//        String c1 = readCellForType(row.getCell(1)).trim();
//        String c2 = readCellForType(row.getCell(2)).trim();
//        String c3 = readCellForType(row.getCell(3)).trim();
//        String c4 = readCellForType(row.getCell(4)).trim();
//        String c5 = readCellForType(row.getCell(5)).trim();
//        String c6 = readCellForType(row.getCell(6)).trim();
//        String c7 = readCellForType(row.getCell(7)).trim();
//        String c8 = readCellForType(row.getCell(8)).trim();
//        a.setBuyer(c1);
//        a.setSeller(c2);
//        a.setAmount(c3);
//        a.setBillNumber(c4);
//        a.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse(c5));
//        a.setInvoiceNumber(c6);
//        a.setInvoiceAmount(c7);
//        a.setContractNumber(c8);
//        a.setAssetStatus(1);
//        return a;
//    }
//
//
//    private String readCellForType(Cell cell) {
//        if (cell.getCellType().equals(CellType.NUMERIC)) {
//            return cell.getNumericCellValue() + "";
//        }
//        return cell.getStringCellValue();
//    }
//
//
//    public ArrayList<AcmsAsset> readForAttach(MultipartFile file, Integer proj) {
//        File f = null;
//        ArrayList<AcmsAsset> assets = null;
//        try {
//            f = File.createTempFile(UUID.randomUUID().toString(), ".xlsx");
//            file.transferTo(f);
//            assets = readForAttach(f, proj);
//            f.delete();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return assets;
//    }
//
//    public ArrayList<AcmsAsset> readForAttach(File file, Integer proj) throws Exception {
//        Sheet sheet = getSheetByFile(file);
//        int rowslenth = getRowslenth(sheet);
//        HashMap<Integer, Integer> m = new HashMap<>();
//        Row row = null;
//        HashSet attachepools = new HashSet();
//        ArrayList<AcmsAsset> assets = new ArrayList<>();
//        for (int i = 1; i <= rowslenth; i++) {
//            row = sheet.getRow(i);
//            Double assetid = row.getCell(0).getNumericCellValue();
////            String billNumber = row.getCell(1).getStringCellValue().trim();
//            String performFileName = row.getCell(2).getStringCellValue().trim();
//            String assetFilename = row.getCell(3).getStringCellValue().trim();
////            String[] daoOperate = {assetid, performFileName, assetFilename};
////            boolean add1 = attachepools.add(performFileName);
////            boolean add2 = attachepools.add(assetFilename);
////            if (!add1 || !add2) {
////                failed_ids.add(assetid);
////            }
//            AcmsAsset a = new AcmsAsset();
////            a.setAssetId(Integer.parseInt(assetid));
//            a.setAssetId(assetid.intValue());
////            a.setBillNumber(billNumber);
//            a.setPerformFilename(performFileName);
//            a.setAssetFilename(assetFilename);
//            assets.add(a);
//        }
//        return assets;
//    }


}
