package AAAAAAPracs.POIForWords;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DocxUtilWithPics {

    public static InputStream replaceTextToIps(InputStream inputStream, Map<String, String> contentMap) {
        XWPFDocument document = replaceTextToXWPF(inputStream, contentMap);
        return XWPFDocumentToIps(document);
    }

    public static XWPFDocument replaceTextToXWPF(InputStream inputStream, Map<String, String> contentMap) {
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

    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, String> param) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Map.Entry<String, String> entry : param.entrySet()) {
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

    public static void processTableValues(XWPFTable table, Map<String, String> data) {
        int rcount = table.getNumberOfRows();
        for (int i = 0; i < rcount; i++) {
            XWPFTableRow row = table.getRow(i);
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //表格中处理段落（回车）
                List<XWPFParagraph> cellParList = cell.getParagraphs();
                for (int p = 0; cellParList != null && p < cellParList.size(); p++) { //每个格子循环
                    List<XWPFRun> runs = cellParList.get(p).getRuns(); //每个格子的内容都要单独处理
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if (text != null) {
                            boolean isSetText = false;
                            for (Map.Entry<String, String> entry : data.entrySet()) {
                                String key = "${" + entry.getKey() + "}";
                                if (text.contains(key)) {
                                    isSetText = true;
                                    Object value = entry.getValue();
                                    text = text.replace(key, value != null ? value.toString() : "");
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

    }

    public static void ReadwriteSummaryDoc(String path, OutputStream out, List<ArchivedInfo> list, Map<String, String> m) throws IOException {
        XWPFDocument docs = getSummaryDocNoWrite(path, out, list, m);
        docs.write(out);
        docs.close();
    }

    public static XWPFDocument getSummaryDocNoWrite(String path, OutputStream out, List<ArchivedInfo> list, Map<String, String> m) throws IOException {
        File tempDoc = new File(path);
        XWPFDocument docs = new XWPFDocument(new BufferedInputStream(new FileInputStream(tempDoc)));
        List<XWPFParagraph> paragraphList = docs.getParagraphs();
        processParagraphs(paragraphList, m);
        docs = readgenerateTables(docs, list, m);
        return docs;
    }

    public static byte[] getSummaryDocBytes(String path, List<ArchivedInfo> list, Map<String, String> m) throws IOException {
        File tempDoc = new File(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFDocument docs = new XWPFDocument(new BufferedInputStream(new FileInputStream(tempDoc)));
        List<XWPFParagraph> paragraphList = docs.getParagraphs();
        processParagraphs(paragraphList, m);
        docs = readgenerateTables(docs, list, m);
        docs.write(out);
        docs.close();
        byte[] bytes = out.toByteArray();
        out.close();
        return bytes;
    }

    public static byte[] ReadwriteApprovalDoc(String path, Map<String, String> m) throws IOException {
        File tempDoc = new File(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFDocument docs = new XWPFDocument(new BufferedInputStream(new FileInputStream(tempDoc)));
        List<XWPFParagraph> paragraphList = docs.getParagraphs();
        processParagraphs(paragraphList, m);
        Iterator<XWPFTable> tablesIterator = docs.getTablesIterator();
        if (tablesIterator.hasNext()) {
            XWPFTable table = tablesIterator.next();
            processTableValues(table, m);
        }

        docs.write(out);
        docs.close();
        byte[] bytes = out.toByteArray();
        out.close();
        return bytes;
    }

    public static XWPFDocument readgenerateTables(XWPFDocument doc, List<ArchivedInfo> list, Map<String, String> m) throws IOException {
        Iterator<XWPFTable> tablesIterator = doc.getTablesIterator();
        XWPFTable table = tablesIterator.next();
        processTableValues(table, m);
        inserInfo(table, list);
        return doc;
    }

    private static void inserInfo(XWPFTable table, List<ArchivedInfo> list) {

        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = null;

        row.getCell(0).setText("序号");
        row.getCell(1).setText("工程变更项目");
        row.getCell(2).setText("变更单编号");
        row.getCell(3).setText("批准日期");
        row.getCell(4).setText("施工单位报价(元)");
        row.getCell(5).setText("设计单位报价(元)");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int i = 1;

        int totalBuildPrice = 0;
        int totalDesignPrice = 0;
        for (ArchivedInfo a : list) {

            totalBuildPrice += Integer.parseInt(STR.isBlank(a.getPriceByBuid()) ? "0" : a.getPriceByBuid());
            totalDesignPrice += Integer.parseInt(STR.isBlank(a.getPriceByDesign()) ? "0" : a.getPriceByDesign());

            row = table.insertNewTableRow(i);//为表格添加行
            row.createCell().setText("" + i);
            row.createCell().setText(a.getProcessInfo());
            row.createCell().setText(a.getArchivedId());
            row.createCell().setText(sdf.format(a.getProcessDate()));
            row.createCell().setText(a.getPriceByBuid());
            row.createCell().setText(a.getPriceByDesign());

            i++;
        }

        row = table.insertNewTableRow(i);//为表格添加行
        row.createCell().setText("" + i);
        row.createCell().setText("合 计 金 额");
        row.createCell().setText("");
        row.createCell().setText("");
        row.createCell().setText(totalBuildPrice + "");
        row.createCell().setText(totalDesignPrice + "");

    }

    public static void addStampImage(List<XWPFParagraph> paragraphList, Map<String, byte[]> pics) {
        try {
            if (paragraphList != null && paragraphList.size() > 0) {
                for (XWPFParagraph paragraph : paragraphList) {
                    for (XWPFRun run : paragraph.getRuns()) {//遍历每一个单元格
                        for (Map.Entry<String, byte[]> stringEntry : pics.entrySet()) {
                            if (run.getText(0) != null && run.getText(0).contains("${" + stringEntry.getKey() + "}")) {//如果遇到"&章"则进行替换
                                try {
                                    insertCellStamp(run, "${" + stringEntry.getKey() + "}", stringEntry.getValue());//给带有要盖章字样的单元格 加上章的图片
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addStampImage(XWPFTable table, Map<String, byte[]> m) {
        try {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {//遍历每一个单元格
                    for (Map.Entry<String, byte[]> stringEntry : m.entrySet()) {
                        if (cell.getText().contains("${" + stringEntry.getKey() + "}")) {//如果遇到"&章"则进行替换
                            try {
                                insertCellStamp(cell, "${" + stringEntry.getKey() + "}", stringEntry.getValue());//给带有要盖章字样的单元格 加上章的图片
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertCellStamp(XWPFTableCell cell, String key, byte[] picbytes) throws
            InvalidFormatException, IOException {//给带有要盖章字样的单元格 加上章的图片
        for (XWPFParagraph para : cell.getParagraphs()) {
            for (XWPFRun run : para.getRuns()) {
                String text = run.getText(0);
                String imgFile = "img";
                ByteArrayInputStream is = new ByteArrayInputStream(picbytes);
                run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(50), Units.toEMU(20)); // 100x100 pixels
                is.close();
                text = text.replace(key, "");
                run.setText(text, 0);
            }
            //插入图片
        }
    }

    private static void insertCellStamp(XWPFRun run, String key, byte[] picbytes) throws IOException, InvalidFormatException {
        String text = run.getText(0);
        String imgFile = "img";
        ByteArrayInputStream is = new ByteArrayInputStream(picbytes);
        run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(50), Units.toEMU(20)); // 100x100 pixels
        is.close();
        text = text.replace(key, "");
        run.setText(text, 0);
    }


    public static void main(String[] args) throws IOException, InvalidFormatException {
        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph title = doc.createParagraph();
        XWPFRun run = title.createRun();
        run.setText("Fig.1 A Natural Scene");
        run.setBold(true);
        title.setAlignment(ParagraphAlignment.CENTER);

        String imgFile = "encabezado.jpg";
        FileInputStream is = new FileInputStream(imgFile);
        run.addBreak();
        run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
        is.close();

        FileOutputStream fos = new FileOutputStream("test4.docx");
        doc.write(fos);
        fos.close();
    }
}
