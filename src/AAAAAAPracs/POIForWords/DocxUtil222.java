package AAAAAAPracs.POIForWords;


import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DocxUtil222 {

    public static void writeTblWithImageToDocx_2() throws IOException {
        BufferedReader in = null;
        XWPFDocument temp = null;
        BufferedOutputStream out = null;
        File tempDoc = new File("C:\\ZZBK\\t1.docx");
        tempDoc.createNewFile();
        try {
            //in = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\test\\2.doc"), "ISO8859_1"));
            temp = new XWPFDocument(new BufferedInputStream(new FileInputStream(tempDoc)));

            out = new BufferedOutputStream(new FileOutputStream("C:\\ZZBK\\t2.docx"));
            XWPFParagraph p = temp.getParagraphArray(0);
            p.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run = p.insertNewRun(0);

            //表格生成 x行6列.
            int rows = 6;
            int cols = 6;
            XmlCursor cursor = p.getCTP().newCursor();
            XWPFTable tableOne = temp.insertNewTbl(cursor);

            //样式控制
            CTTbl ttbl = tableOne.getCTTbl();
            CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
            CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
            CTJc cTJc = tblPr.addNewJc();
            cTJc.setVal(STJc.Enum.forString("center"));//表格居中
            tblWidth.setW(new BigInteger("8000"));//每个表格宽度
            tblWidth.setType(STTblWidth.DXA);

            //表格创建
            XWPFTableRow tableRowTitle = tableOne.getRow(0);
            tableRowTitle.setHeight(380);

            tableRowTitle.getCell(0).setText("标题");
            tableRowTitle.addNewTableCell().setText("内容");
            tableRowTitle.addNewTableCell().setText("姓名");
            tableRowTitle.addNewTableCell().setText("日期");
            tableRowTitle.addNewTableCell().setText("备注");
            tableRowTitle.addNewTableCell().setText("备注2");
            for (int i = 1; i < rows; i++) {
                XWPFTableRow createRow = tableOne.createRow();
                for (int j = 0; j < cols; j++) {
                    createRow.getCell(j).setText("我是第" + i + "行,第" + (j + 1) + "列");
                }
            }
            mergeCellsHorizontal(tableOne, 0, 0, 1);//WPS不支持跨列
            mergeCellsVertically(tableOne, 1, 1, 2);
            run.addBreak();
            temp.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("写入完成。。。。。。。。。。。。。。");
    }

    // word跨列合并单元格
    public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    // word跨行并单元格
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    public static InputStream replaceTextToIps(InputStream inputStream, Map<String, Object> contentMap) {
        XWPFDocument document = replaceTextToXWPF(inputStream, contentMap);
        return XWPFDocumentToIps(document);
    }

    public static XWPFDocument replaceTextToXWPF(InputStream inputStream, Map<String, Object> contentMap) {
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //处理段落
        List<XWPFParagraph> paragraphList = document.getParagraphs();
        processParagraphs(paragraphList, contentMap);

        //处理表格
        Iterator<XWPFTable> it = document.getTablesIterator();
        while (it.hasNext()) {
            XWPFTable table = it.next();
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                    processParagraphs(paragraphListTable, contentMap);
                }
            }
        }
        return document;
    }

    public static InputStream XWPFDocumentToIps(XWPFDocument document) {
        InputStream ips = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.write(byteArrayOutputStream);
            ips = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ips;
    }

    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = "${" + entry.getKey() + "}";
                            if (text.contains(key)) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }

    public static void exportBg() throws FileNotFoundException {

        String srcPath = "C:\\ZZBK\\t1.docx";
        String targetPath = "C:\\ZZBK\\t2.docx";
        String key = "${marks}";// 在文档中需要替换插入表格的位置
        XWPFDocument doc;
        FileOutputStream out = new FileOutputStream(targetPath);

        try {
            doc = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            if (paragraphList != null && paragraphList.size() > 0) {
                //遍历段落信息
                for (XWPFParagraph paragraph : paragraphList) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (int i = 0; i < runs.size(); i++) {
                        String text = runs.get(i).getText(0).trim();
                        if (text != null) {
                            if (text.indexOf(key) >= 0) {
                                runs.get(i).setText(text.replace(key, ""), 0);

                                XmlCursor cursor = paragraph.getCTP().newCursor();
                                // 在指定游标位置插入表格
                                XWPFTable table = doc.insertNewTbl(cursor);

                                CTTblPr tablePr = table.getCTTbl().getTblPr();
                                CTTblWidth width = tablePr.addNewTblW();
                                width.setW(BigInteger.valueOf(8500));
                                inserInfo(table, null);
                                break;
                            }
                        }
                    }
                }
            }

            doc.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {

        }
    }


    private static void inserInfo(XWPFTable table, List list) {
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = null;
        for (int col = 1; col < 6; col++) {//默认会创建一列，即从第2列开始
            // 第一行创建了多少列，后续增加的行自动增加列
            CTTcPr cPr = row.createCell().getCTTc().addNewTcPr();
            CTTblWidth width = cPr.addNewTcW();
            if (col == 1 || col == 2 || col == 4) {
                width.setW(BigInteger.valueOf(2000));
            }
        }
        row.getCell(0).setText("指标");
        row.getCell(1).setText("指标说明");
        row.getCell(2).setText("公式");
        row.getCell(3).setText("参考值");
        row.getCell(4).setText("说明");
        row.getCell(5).setText("计算值");
        for (int i = 0; i < 4; i++) {
            row = table.createRow();
            row.getCell(0).setText("cell 01");
            cell = row.getCell(1);
            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
            cell.setText("cell 02");
            cell = row.getCell(2);
            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
            cell.setText("cell 03");
//            if (item.getCkz() != null && !item.getCkz().contains("$")) {
            row.getCell(3).setText("cell 04");
//            }
            cell = row.getCell(4);
            cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2000));
            cell.setText("cell 01");
            row.getCell(5).setText("cell 05");
        }
    }


    public static void main(String[] args) throws IOException {

        System.out.println("extremely good".indexOf("etc"));
        exportBg();
    }


}
